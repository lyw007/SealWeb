package com.pp100.seal.model.sealutil;

import java.io.File;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.timevale.esign.sdk.tech.bean.PosBean;
import com.timevale.esign.sdk.tech.bean.SignBean;
import com.timevale.esign.sdk.tech.bean.result.Result;
import com.timevale.esign.sdk.tech.bean.result.SignDataResult;
import com.timevale.esign.sdk.tech.bean.result.VerifyPdfResult;
import com.timevale.esign.sdk.tech.bean.seal.OrganizeTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.PersonTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.SealColor;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import com.timevale.esign.sdk.tech.service.SignService;
import com.timevale.esign.sdk.tech.service.factory.SignServiceFactory;

@Service
public class EsignService {
    private SignService signServiceImpl = SignServiceFactory.instance();

    private static Logger logger = Logger.getLogger(EsignService.class);

    /**
     * 验证PDF
     * 
     * @param signedFile(PDF保存的相对路径)
     * @return
     */
    public SignStatus verifyPdf(String signedFile, String sealFileUrl) {
        signedFile = sealFileUrl + signedFile; // 本地绝对路径
        VerifyPdfResult r = signServiceImpl.localVerifyPdf(signedFile);
        // 验证失败
        if (0 != r.getErrCode()) {
            logger.error("verifyPdf-验证失败！" + signedFile);
            return SignStatus.VERIFY_FAILED;
        }
        // 对所有签名进行验证
        for (SignBean sign : r.getSignatures()) {
            if (!sign.getSignature().isValidate()) {
                logger.error("verifyPdf-验证失败！" + signedFile);
                return SignStatus.VERIFY_FAILED;
            }
        }
        return SignStatus.VERIFY_OK;
    }

    /**
     * 手绘签署
     * 
     * @param devId
     *            开发者模式
     * @param userId
     *            用户编号
     * @param sealData
     *            手绘签印章(前台传入)
     * @param fileUrl
     *            文件保存地址
     * @param keyWord
     *            关键字
     * @param sealTempUrl
     *            在电子签章过程中，临时文件存放地址
     */
    public void signDraw(String devId, String userId, String sealData, String fileUrl, String keyWord, SealColor color,
            String sealTempUrl) throws Exception {
        sealData = sealData.substring(sealData.indexOf(',') + 1);
        // 完整印章数据
        String seal = (new EsignSealService()).createSeal(devId, userId, sealData, color);
        if (null == seal) { // 签署失败
            throw new Exception("手绘签署-失败" + userId);
        }
        this.sign(devId, userId, sealData, fileUrl, keyWord, sealTempUrl);
    } 

    /**
     * 开发者账号所在平台自己签注
     * 
     * @param req
     * @param resp
     * @param id
     * @param seal (印章数据)
     * @param sealFileUrl E签宝保存地址 return 返回文件保存地址
     */
    private String sign2PlatForm(String devId, String fileUrl, String keyWord, String sealFileUrl) {
        // 关键字定位
        PosBean pos = new PosBean();
        pos.setPosType(1);
        pos.setKey(keyWord);
        // 清理
        clear(sealFileUrl);
        // 调整目录
        fileUrl = sealFileUrl + fileUrl;
        StringBuilder full = new StringBuilder();
        full.append(sealFileUrl);

        StringBuilder relative = new StringBuilder();
        relative.append("signed_" + UUID.randomUUID().toString() + ".pdf ");

        Result r = signServiceImpl.localSignPDF(devId, fileUrl, full.append(relative).toString(), pos, 0,
                SignType.Key, "");
        if (0 != r.getErrCode()) {
            return "";
        }

        return relative.toString();
    }

    /**
     * 使用电子签章模板
     * 
     * @param devId
     *            开发者模式
     * @param userId
     *            用户编号
     * @param personTemplateType
     *            模板参数 （前台传入）
     * @param organizeTemplateType
     *            模板参数 （前台传入）
     * @param userType
     *            用户类型
     * @param fileUrl
     *            文件保存地址
     * @param keyWord
     *            关键字
     * @param uText
     *            (用于企业印章)
     * @Param dText (用于企业印章) return 返回文件保存地址
     */
    public String signModel(String devId, String userId, PersonTemplateType personTemplateType,
            OrganizeTemplateType organizeTemplateType, ESealUserType userType, String fileUrl, String keyWord,
            SealColor color, String uText, String dText, String sealTempUrl) throws Exception {
        // 完整印章数据
        String seal = (new EsignSealService()).createSeal(devId, userId, userType, personTemplateType,
                organizeTemplateType, color, uText, dText);
        return this.sign(devId, userId, seal, fileUrl, keyWord, sealTempUrl);
    }

    /**
     * 关键字签章-需要设置多页。（坐标定位盖章，需要设置单页）
     * 
     * @param req
     * @param resp
     * @param id
     * @param seal
     *            (印章数据) return 返回文件保存地址(如果出现异常，就返回传入的路径，保证后面的程序能够正常进行)
     */
    private String sign(String devId, String userId, String sealData, String fileName, String keyWord,
            String sealTempUrl)  throws Exception{
        String sealFileUrl = "";
        try {
            this.isExsitFile2CreateFileUrl(sealTempUrl);

            // 关键字定位
            PosBean pos = new PosBean();
            pos.setPosType(1);
            pos.setKey(keyWord);

            // 待签署合同-地址
            String tmpFileUrl = sealTempUrl + fileName;
            // 签署完成-合同保存地址
            sealFileUrl = "signed_" + UUID.randomUUID().toString() + ".pdf ";

            Result r = signServiceImpl.localSignPDF(devId, userId, sealData, tmpFileUrl, sealTempUrl + sealFileUrl, pos,
                    SignType.Key, null);
            if (0 != r.getErrCode()) {
                throw new Exception("替换关键字： " + keyWord + "。\t" + r.getMsg());
            }
            clear(tmpFileUrl); // 清空临时文件
        } catch (Exception e) {
            throw new Exception("sign=签章错误" + e.getMessage());
        }
        return sealFileUrl;
    }

    // 判断并文件是否存在，不存在创建文件夹
    private boolean isExsitFile2CreateFileUrl(String fileUrl) {
        boolean flag = false;
        File root = new File(fileUrl);
        if (!root.exists()) {
            flag = root.mkdirs();
        }
        return flag;
    }

    // 删除临时文件
    public void clear(String path) {
        File root = new File(path);
        if (root.exists()) {
            root.delete();
        }
    }

    /**
     * 签署文本文件
     * 
     * @param signOperate
     * @param text
     * @param devId
     * @param userId
     */
    public void preSignText(boolean signOperate, String text, String devId, String userId) {
        String signature = "";
        if (signOperate) {
            signature = signText(devId, text);
        } else {
            verifyText(text, signature);
        }
    }

    /**
     * 签文本
     * 
     * @param text
     * @param dmId
     * @param devId
     * @param userid
     */
    private String signText(String devId, String text) {
        SignDataResult r = signServiceImpl.localSignData(devId, text);
        if (0 != r.getErrCode()) {// 签署失败
            return "";
        }
        return r.getSignResult();
    }

    /**
     * 验证文本
     * 
     * @param req
     * @param resp
     */
    private SignStatus verifyText(String text, String signature) {
        Result r = signServiceImpl.verifySignResult(text, signature);
        if (0 != r.getErrCode()) { // 验证失败
            return SignStatus.VERIFY_FAILED;
        }
        return SignStatus.VERIFY_OK;
    }
}
