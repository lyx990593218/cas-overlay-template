package club.laiyouxu.captcha.easy.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: easyCaptcha
 * @Author: Kurt Xu
 * @Date: 2021/12/22 17:09
 * @Version: 1.0
 */
public interface EasyCaptchaService {

    /**
     * 生成验证码
     *
     * @param key      验证码 uuid
     * @param response HttpServletResponse
     * @throws IOException 异常
     */
    void create(String key, HttpServletResponse response) throws IOException;

    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     * @return 是否成功
     */
    Boolean check(String key, String value);
}
