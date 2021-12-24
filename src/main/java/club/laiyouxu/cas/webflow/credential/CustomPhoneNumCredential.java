package club.laiyouxu.cas.webflow.credential;

import lombok.Generated;
import org.apereo.cas.authentication.Credential;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/23 14:03
 * @Version: 1.0
 */
public class CustomPhoneNumCredential implements Credential {

    private String phone;

    private String validCode;

    @Generated
    public CustomPhoneNumCredential(final String phone, final String validCode) {
        this.phone = phone;
        this.validCode = validCode;
    }

    @Override
    public String getId() {
        return this.phone;
    }

    @Generated
    CustomPhoneNumCredential() {
    }

    @Generated
    public String getPhone() {
        return phone;
    }

    @Generated
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Generated
    public String getValidCode() {
        return validCode;
    }

    @Generated
    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
