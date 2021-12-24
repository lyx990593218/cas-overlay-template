package club.laiyouxu.captcha.easy.service.impl;

import club.laiyouxu.captcha.easy.properties.EasyCaptchaProperties;
import club.laiyouxu.captcha.easy.service.EasyCaptchaService;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: easyCaptcha实现
 * @Author: Kurt Xu
 * @Date: 2021/12/22 17:11
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class EasyCaptchaServiceImpl implements EasyCaptchaService {

    private final EasyCaptchaProperties captchaProperties;

    private final Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        Captcha captcha = createCaptcha();
        map.put(key, StringUtils.lowerCase(captcha.text()));
        setHeader(response);
        captcha.out(response.getOutputStream());
    }

    @Override
    public Boolean check(String key, String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        String code = map.get(key);
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        if (!StringUtils.equalsIgnoreCase(value, code)) {
            return false;
        }
        map.remove(key);
        return true;
    }

    private Captcha createCaptcha() {
        Captcha captcha;
        EasyCaptchaProperties.CaptchaType type = captchaProperties.getType();
        switch (type) {
            case GIF:
                captcha = new GifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLen());
                break;
            case SPEC:
                captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLen());
                break;
            case CHINESE:
                captcha = new ChineseCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLen());
                break;
            case CHINESE_GIF:
                captcha = new ChineseGifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLen());
                break;
            case ARITHMETIC:
            default:
                captcha = new ArithmeticCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLen());
                break;
        }
        captcha.setCharType(captchaProperties.getCharType());

        return captcha;
    }

    private void setHeader(HttpServletResponse response) {
        response.setContentType(captchaProperties.getType().getContentType());
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
