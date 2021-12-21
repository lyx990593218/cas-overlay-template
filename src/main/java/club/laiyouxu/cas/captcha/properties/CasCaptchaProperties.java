package club.laiyouxu.cas.captcha.properties;

import lombok.Getter;
import lombok.Setter;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * @author laiyouxu
 */
@ConfigurationProperties(value = CasCaptchaProperties.PREFIX, ignoreUnknownFields = false)
@Getter
@Setter
public class CasCaptchaProperties implements Serializable {
    public static final String PREFIX = "scp.cas.captcha";
    private static final long serialVersionUID = -8620267783596072683L;

    private CasConfigurationProperties casProperties;

    public CasCaptchaProperties(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    /**
     * Google reCAPTCHA settings.
     */
    @NestedConfigurationProperty
    private CaptchaProperties captcha = new CaptchaProperties();

}
