package com.pp100.seal.model.service.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pp100.model.domain.JsonMapper;
import com.pp100.model.domain.JsonMapper.JsonException;
import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.model.dao.ContractApplicationDao;
import com.pp100.seal.model.dao.ContractDao;
import com.pp100.seal.model.dao.ContractTemplateDao;
import com.pp100.seal.model.domain.entity.ContractApplication;
import com.pp100.seal.model.domain.entity.ContractTemplate;
import com.pp100.seal.model.sealutil.ESealUserType;
import com.pp100.seal.model.sealutil.ESignInit;
import com.pp100.seal.model.sealutil.EsignService;
import com.pp100.seal.model.service.sign.ESealAccountManagerService;
import com.pp100.seal.utils.DateUtil;
import com.pp100.seal.utils.HtmlGenerator;
import com.pp100.seal.utils.PdfGenerator;
import com.timevale.esign.sdk.tech.bean.seal.OrganizeTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.PersonTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.SealColor;

@Service
public class TemplateManagerService {
    private static Logger logger = Logger.getLogger(TemplateManagerService.class);

    @Value("#{applicationProperties['eseal.file.url']}")
    private String SEAL_FILE_URL = ""; // 文件保存地址

    @Value("#{applicationProperties['file.service.url']}")
    private String FILE_SERVICE_URL = ""; // 文件服务器

    @Value("#{applicationProperties['file.service.internal']}")
    private String FILE_SERVICE_INTERNAL = "";

    @Value("#{applicationProperties['file.service.app']}")
    private String FILE_SERVICE_APP = "";

    private EsignService esignService;
    private ContractTemplateDao contractTemplateDao;
    private ContractApplicationDao contractApplicationDao;
    private ESealAccountManagerService eSealAccountManagerService;
    private ParameterManagerService parameterManagerService;

    @Autowired
    public TemplateManagerService(EsignService esignService, ContractTemplateDao contractTemplateDao,
            ContractApplicationDao contractApplicationDao, ESealAccountManagerService eSealAccountManagerService,
            ParameterManagerService parameterManagerService) {
        this.esignService = esignService;
        this.contractTemplateDao = contractTemplateDao;
        this.contractApplicationDao = contractApplicationDao;
        this.eSealAccountManagerService = eSealAccountManagerService;
        this.parameterManagerService = parameterManagerService;
    }

    /**
     * 获取模板信息
     * 
     * @param templateFtl
     * @return
     */
    public ContractTemplate getContractTemplateByTemplateName(String templateName) {
        return contractTemplateDao.getContractTemplateByTemplateName(templateName);
    }

    /**
     * 获取模板对应的业务信息
     * 
     * @param application
     * @return
     */
    public ContractApplication getContractApplictionByApplictionName(String applicationName) {
        return contractApplicationDao.getContractApplictionByApplictionName(applicationName);
    }

    /**
     * 获取所有的业务模板信息
     * 
     * @return
     */
    public List<ContractTemplate> getAllContractTemplates() {
        return contractTemplateDao.getAllList();
    }

    /**
     * 获取所有系统拥有的模板信息
     * 
     * @return
     */
    public List<ContractApplication> getAllContractApplications() {
        return contractApplicationDao.getAllList();
    }

    /**
     * 执行生成电子签章所需的PDF模板
     * 
     * @param templateFtlName
     * @param contractPersonss
     * @param contractEnterprise
     * @param contractEnterprises
     * @param contractContent
     * @return
     */
    public String excuteContractBuildReturnFileId(long contractId, String templateContract, List contractLists,
            Map<String, Object> contractContents, List<ApiEnterprise> apiEnterprises, List<ApiPerson> apiPerson)
                    throws Exception {

        String openId = ESignInit.getOpenId(); // 获取开发者ID
        // 生成PDF 文件
        File file = this.getAgreementFile(templateContract, contractLists, contractContents, SEAL_FILE_URL);
        String tmpContractFileUrl = file.getName();

        // 遍历所有的信息
        // (1)进行个人盖章
        for (int i = 0; apiPerson != null && i < apiPerson.size(); i++) {
            ApiPerson apiBasePerson = apiPerson.get(i);
            String personId = StringUtils.trimToEmpty(eSealAccountManagerService.getPersonId(apiBasePerson).getEsignId());
            tmpContractFileUrl = esignService.signModel(openId, personId, PersonTemplateType.HWLS, null,
                    ESealUserType.USER_TYPE_PERSON, tmpContractFileUrl, apiBasePerson.getIdNumber() + "~",
                    SealColor.RED, null, null, SEAL_FILE_URL);
        }

        // (2)进行企业盖章
        apiEnterprises = isPlatformSeal(contractContents) ? parameterManagerService.getAllApiEnterprises(apiEnterprises) : apiEnterprises;
        for (int i = 0; apiEnterprises != null && i < apiEnterprises.size(); i++) {
            ApiEnterprise apiEnterprise = apiEnterprises.get(i);
            String orgId = StringUtils.trimToEmpty(eSealAccountManagerService.getEnterpriseId(apiEnterprise).getEsignId());
            tmpContractFileUrl = esignService.signModel(openId, orgId, null, OrganizeTemplateType.STAR,
                    ESealUserType.USER_TYPE_COMPANY, tmpContractFileUrl, apiEnterprise.getKeyword(), SealColor.RED,
                    apiEnterprise.getPlusDate(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), SEAL_FILE_URL);
        }
        return this.upload2FileService(tmpContractFileUrl);
    }

    // 判断合同是否需要进行平台盖章
    public boolean isPlatformSeal(Map<String, Object> contractContents) {
        if (contractContents.get("isPlatformSeal") == null) {
            return true;
        }
        return null == contractContents || StringUtils.isBlank(contractContents.get("isPlatformSeal").toString())
                || Boolean.valueOf(contractContents.get("isPlatformSeal").toString());
    }
    
    /**
     * 生成PDF文件(默认放到系统临时文件夹中)
     * 
     * @param templateFtl
     * @param contractEntitys
     *            合同盖章实体集合
     * @param contractSubjects
     *            合同内容主体集合
     * @return
     */
    public File getAgreementFile(String templateContract, List<Map<String, Object>> contractList,
            Map<String, Object> contractContents) throws Exception {
        return this.getAgreementFile(templateContract, contractList, contractContents, null);
    }

    /**
     * 生成PDF文件
     * 
     * @param templateFtl
     * @param contractEntitys  合同盖章实体集合
     * @param contractSubjects 合同内容主体集合
     * @param fileFolder 文件保存地址 (如果为空，表示放到系统临时文件夹中， 如果不为空，表示放到指定文件夹中)
     * @return
     */
    public File getAgreementFile(String templateContract, List<Map<String, Object>> contractList,
            Map<String, Object> contractContents, String fileFolder) throws Exception {
        return this.getAgreementFile(null, templateContract, contractList, contractContents, fileFolder);
    }

    /**
     * 生成PDF文件
     * 
     * @param agreementName 协议名称（设置文件的名称） 
     * @param templateFtl
     * @param contractEntitys 合同盖章实体集合
     * @param contractSubjects 合同内容主体集合
     * @param fileFolder 文件保存地址 (如果为空，表示放到系统临时文件夹中， 如果不为空，表示放到指定文件夹中)
     * @return
     */
    public File getAgreementFile(String agreementName, String templateContract, List<Map<String, Object>> contractList,
            Map<String, Object> contractContents, String fileFolder) throws Exception {
        File tempFile;
        String prefix = StringUtils.trimToEmpty(agreementName) + DateUtil.simple(new Date()) + "_" + UUID.randomUUID().toString();
        try {
            Map<String, Object> variables = new HashMap(); // 参数
            variables.put("investlist", contractList);
            variables.put("map", contractContents);
            if (fileFolder != null) {
                tempFile = new File(fileFolder + prefix + ".pdf");
            } else {
                tempFile = File.createTempFile(prefix, ".pdf");
            }
            String waterImage = contractContents != null ? String.valueOf(contractContents.get("waterImage")) : null;
            PdfGenerator.generateNoVerify2ESign(HtmlGenerator.generate(templateContract, variables), tempFile, waterImage);
            return tempFile;
        } catch (Exception e) {
            throw new Exception("创建借款协议失败！ " + e.getMessage());
        }
    }
    
    // 删除临时文件
    public void clearTempSealContractFile() {
        File file = new File(SEAL_FILE_URL);
        String[] childFilePaths = file.list();
        if (childFilePaths.length <= 100) {
            return;
        }
        for (String childFilePath : childFilePaths) {
            File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);
            if(childFile.isFile()){
                childFile.delete();
            }
        }
    }

    /**
     * 上传文件到文件服务器
     * 
     * @param url
     * @return
     */
    private String upload2FileService(String url) throws Exception {
        String uploadResult = "";
        try {
            uploadResult = Request.Post(FILE_SERVICE_URL)
                    .body(MultipartEntityBuilder.create()
                            .addBinaryBody("file", fileToByte(SEAL_FILE_URL + url), ContentType.APPLICATION_OCTET_STREAM, "file")
                            .addTextBody("internal", FILE_SERVICE_INTERNAL).addTextBody("app", FILE_SERVICE_APP)
                            .build())
                    .execute().returnContent().asString();
            if (null == uploadResult || "".equals(uploadResult)) {
                return "";
            }
            JsonMapper jsonMapper = new JsonMapper();
            HashMap map = jsonMapper.fromJson(uploadResult, new TypeReference<HashMap>() {
            });

            if (!"OK".equals(map.get("code"))) {
                return "";
            }
            map = jsonMapper.fromJson(jsonMapper.toJson(map.get("data")), new TypeReference<HashMap>() {
            });
            return map.get("id").toString();
        } catch (JsonException e) {
            throw new Exception("文件服务器返回的参数异常：" + e.getMessage() + uploadResult);
        } catch (Exception e) {
            throw new Exception("上传文件服务器失败 地址：" + url + uploadResult + e.getMessage());
        }
    }
    
    public static byte[] fileToByte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
