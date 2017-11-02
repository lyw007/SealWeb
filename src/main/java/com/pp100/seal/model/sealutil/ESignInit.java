package com.pp100.seal.model.sealutil;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.timevale.esign.sdk.tech.bean.result.LoginResult;
import com.timevale.esign.sdk.tech.bean.result.Result;
import com.timevale.esign.sdk.tech.service.factory.EsignsdkServiceFactory;

public class ESignInit {
    private static String openId = null; // 开发者账号
    private static boolean eSignConnectStatus = true; // 开发者账号失效状态(true：失效
                                                      // false：有效)
    private static Date createTime = null; // 创建时间
    @Autowired
    public ESignInit() {
        InitESignOpenId();
    }

    /**
     * 同步初始化E-签宝开发者账号，防止开发者账号不统一。
     */
    public static synchronized void InitESignOpenId() {
        if (eSignConnectStatus) {
            Result result = EsignsdkServiceFactory.instance().init(ESignSetting.getInstance().getProjectId(), ESignSetting.getInstance().getProjectSecret());
            if (0 != result.getErrCode()) {
                return;
            }
            LoginResult loginResult = EsignsdkServiceFactory.instance().login();
            if (0 != loginResult.getErrCode()) {
                return;
            }
            openId = loginResult.getAccountId(); // 设置开发者账号
            createTime = new Date(); // 设置开发者账号创建时间
            eSignConnectStatus = false; // 判断是否需要初始化状态
        }
    }

    /**
     * 获取开发者编号,获取的时候，要判断开发者账号是否存在或者失效。失效的话，重新连接。
     * 
     * @return
     */
    public static String getOpenId() {
        if (eSignConnectStatus) {
            InitESignOpenId();
        }
        return openId;
    }

    /**
     * 开发者账号失效判断(E-签宝 返回的失效状态编号 5004和5005)
     */
    public static void checkLose2OpenId(int errorCode) {
        if (errorCode == 5004 || errorCode == 5005) {
            eSignConnectStatus = true;
        }
    }

    /**
     * 判断时间是否快失效
     * 
     * @return
     */
    public static Long checkCreateTime() {
        return ((new Date()).getTime() - createTime.getTime()) / (1000 * 60);
    }
}
