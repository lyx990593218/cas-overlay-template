package club.laiyouxu.cas.custom.properties.captcha;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//@RequiresModule(name = "cas-server-support-captcha")
@Getter
@Setter
public class RecaptchaProperties implements Serializable {

    private static final long serialVersionUID = -7955074129123813915L;

    /**
     * Whether google reCAPTCHA should be enabled.
     */
    private boolean enabled = true;

    /**
     * The google reCAPTCHA endpoint for verification of tokens and input.
     */
    private String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";

    /**
     * Whether google reCAPTCHA invisible should be enabled.
     */
    private boolean invisible;
}
