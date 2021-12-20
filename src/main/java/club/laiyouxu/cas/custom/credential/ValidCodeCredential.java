package club.laiyouxu.cas.custom.credential;

import org.apereo.cas.authentication.UsernamePasswordCredential;

/**
 * @Description: 验证码登录
 * @Author: Kurt Xu
 * @Date: 2021/12/17 14:54
 * @Version: 1.0
 */
public class ValidCodeCredential extends UsernamePasswordCredential {

    public ValidCodeCredential() {
    }

    public ValidCodeCredential(String username, String password, String validCode, String deviceId) {
        super(username, password);
        this.validCode = validCode;
        this.deviceId = deviceId;
    }

    private String validCode;

    private String deviceId;

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
