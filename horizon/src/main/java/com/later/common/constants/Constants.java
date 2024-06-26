package com.later.common.constants;

import lombok.Getter;

public class Constants {

    public static final String Header_Key_Application_Name = "Application-Name";
    public static final String Header_Application_Name_None = "Application_Name_None";
    public static final String Header_Trace_Id = "Trace-Id";
    public static final String Header_Trace_Id_None = "Trace_Id_None";
    public static final String Header_Key_Access_Token = "Access-Token";
    public static final String Header_Key_Refresh_Token = "Refresh-Token";
    public static final String Header_Key_Rsa_Public_Key = "Rsa-Public-Key";

    public static final String Default_Parameter_Name_Csrf = "_csrf";
    public static final String Default_Header_Name_Csrf = "X-CSRF-TOKEN";

    public static final String Form_Parameter_Name_Username = "username";
    public static final String Form_Parameter_Name_Password = "password";
    public static final String Form_Parameter_Name_Code = "code";

    public static final String Session_Captcha = "SessionCaptcha:";
    public static final String Session_Spring_Security_Saved_Request = "SPRING_SECURITY_SAVED_REQUEST";
    public static final String Session_Spring_Security_Context = "SPRING_SECURITY_CONTEXT";

    public static final String Env_Cfg_PlaintextDecrypt = "Cfg.PlaintextDecrypt";
    public static final String Env_RSA_PasswordSeed = "RSA.PasswordSeed";

    public static final String Suppress_Warnings_Deprecation = "deprecation";

    public static final String Default_Authentication_Username = "super";
    public static final String Default_Authentication_Password = "123456";
    public static final String Default_Spring_Profiles_Active = "native";


    @Getter
    public enum BizStatus implements IEnum<String> {

        Request_Header_PublicKey_Non_Exist("Request_Header_PublicKey_Non_Exist", "请求头公钥不存在"),
        Request_Obtain_Fail("Request_Obtain_Fail", "Request获取失败"),
        Response_Obtain_Fail("Response_Obtain_Fail", "Response获取失败"),
        Session_Obtain_Fail("Session_Obtain_Fail", "Session获取失败"),
        ServletRequestAttributes_Obtain_Fail("ServletRequestAttributes_Obtain_Fail", "ServletRequestAttributes获取失败"),
        Rsa_PublicKey_Expire("Rsa_PublicKey_Expire", "公钥失效"),
        Rsa_SecretKey_Initialize_Failed("Rsa_PublicKey_Expire", "密钥初始化失败"),
        Rsa_PublicKey_Encrypt_Failed("Rsa_PublicKey_Expire", "公钥加密失败"),
        Rsa_SecretKey_Decrypt_Failed("Rsa_PublicKey_Expire", "私钥解密失败"),
        Authentication_User_Non_Exist("Authentication_User_Non_Exist", "用户不存在"),
        Captcha_Non_Match("Session_Captcha_Non_Match", "会话验证码不匹配"),
        Jpa_Query_Service_Failed("Jpa_Query_Service_Failed", "查询服务失败"),
        Cfg_Decrypt_Obtain_Fail("Cfg_Decrypt_Obtain_Fail", "配置解密密码获取失败"),

        Sso_Captcha_Not_Exists("Sso_Captcha_Not_Exists", "Sso验证码不存在"),
        Sso_User_Not_Exists("Sso_User_Not_Exists", "Sso用户不存在"),
        Sso_User_Exists("Sso_User_Exists", "Sso用户已存在"),
        Sso_User_Password_Incorrect("Sso_User_Password_Incorrect", "Sso用户密码不正确"),

        Sso_Client_Details_Not_Exists("Sso_Client_Details_Not_Found", "Sso客户端详情不存在"),
        Sso_User_Details_Not_Exists("Sso_User_Details_Not_Found", "Sso用户详情不存在"),

        User_Exists("User_Exists", "用户已存在"),
        User_Not_Exists("User_Not_Exists", "用户不存在"),
        Role_Exists("Role_Exists", "角色已存在"),
        Role_Not_Exists("Role_Not_Exists", "角色不存在"),
        Menu_Exists("Menu_Exists", "菜单已存在"),
        Comic_Picture_Not_Exists("Comic_Picture_Not_Exists", "漫画章节图片不存在"),

        User_Not_Relevancy_Role("User_Not_Relevancy_Role", "用户未关联角色"),

        ;

        private final String code;

        private final String message;

        BizStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    public enum BizResponseState implements IEnum<String> {

        Biz_Ok_Response("Biz_Ok_Response", "业务响应成功"),
        Biz_Failed_Response("Biz_Failed_Response", "业务响应失败"),

        ;

        private final String code;

        private final String message;

        BizResponseState(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    public enum DataState implements IEnum<String>, IEnumStatus<Integer> {

        Deleted(0, "Deleted", "已删除"),
        Saved(1, "Saved", "已保存"),

        Disabled(2, "Disabled", "已禁用"),
        Enabled(3, "Enabled", "已启用"),

        Locked(4, "Locked", "已锁定"),
        Unlocked(5, "Unlocked", "未锁定"),
        ;

        private final Integer state;

        private final String code;

        private final String message;

        DataState(Integer state, String code, String message) {
            this.state = state;
            this.code = code;
            this.message = message;
        }

    }

}
