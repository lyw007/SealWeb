package com.pp100.seal.model.sealutil;

import org.springframework.stereotype.Service;

import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.seal.OrganizeTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.PersonTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.SealColor;
import com.timevale.esign.sdk.tech.service.SealService;
import com.timevale.esign.sdk.tech.service.factory.SealServiceFactory;

/**
 * 印章管理、印章模板管理 (印章颜色都是红色)
 * 
 * @author Administrator
 *
 */
@Service
public class EsignSealService {
    private SealService sealServiceImpl = SealServiceFactory.instance();
 
    /** 
     * 创建完整印章数据
     * 
     * @param devId 开发者账号
     * @param userId 用户唯一标识
     * @param userType 账号类型-企业或个人
     * @param uText 企业印章使用(上行文)
     * @param dText 企业印章使用(下行文)
     * @param type
     * @return
     */
    public String createSeal(String devId, String userId, ESealUserType userType, PersonTemplateType personTemplateType,
            OrganizeTemplateType organizeTemplateType, SealColor color, String uText, String dText) throws Exception {
        if (userType.equals(ESealUserType.USER_TYPE_PERSON)) {// 个人
            return createSealPersonal(devId, userId, personTemplateType, color);
        } else {// 企业
            return createSealOrganize(devId, userId, organizeTemplateType, color, uText, dText);
        }
    }

    /**
     * 根据手绘数据创建完整印章数据
     * 
     * @param devId
     * @param userId
     * @param sealData
     * @return
     */
    public String createSeal(String devId, String userId, String sealData, SealColor color)  throws Exception{
        AddSealResult r = sealServiceImpl.addFileSeal(devId, userId, sealData, color);
        if (0 != r.getErrCode()) {
            throw new Exception("根据手绘数据创建完整印章数据-失败" + userId);
        }
        return r.getSealData();
    }

    /**
     * 创建个人电子签章
     * 
     * @param devId
     * @param userId
     * @param personTemplateType 个人印章模板
     * @return
     */
    private String createSealPersonal(String devId, String userId, PersonTemplateType personTemplateType,
            SealColor color) throws Exception {
        // 印章模板调整，请到projectConstants中进行修改
        AddSealResult r = sealServiceImpl.addTemplateSeal(devId, userId, personTemplateType, color);
        if (0 != r.getErrCode()) {
            throw new Exception("创建个人电子签章-失败" + userId);
        }
        return r.getSealData();
    }

    /**
     * 创建企业电子签章
     * 
     * @param devId
     * @param userId
     * @param organizeTemplateType (企业印章模板)
     * @param color(颜色)
     * @param uText(上行文)
     * @param dText(下行文)
     * @return
     */
    private String createSealOrganize(String devId, String userId, OrganizeTemplateType organizeTemplateType, SealColor color, String uText,
            String dText) throws Exception {
        AddSealResult r = sealServiceImpl.addTemplateSeal(devId, userId, organizeTemplateType, color, uText, dText);
        if (0 != r.getErrCode()) {
            throw new Exception("创建企业电子签章-失败" + userId);
        }
        return r.getSealData();
    }
}
