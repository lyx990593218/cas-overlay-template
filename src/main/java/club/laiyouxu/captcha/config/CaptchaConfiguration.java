package club.laiyouxu.captcha.config;

import club.laiyouxu.captcha.controller.CaptchaController;
import club.laiyouxu.captcha.easy.properties.EasyCaptchaProperties;
import club.laiyouxu.captcha.easy.service.EasyCaptchaService;
import club.laiyouxu.captcha.easy.service.impl.EasyCaptchaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 控制类配置
 * @Author: Kurt Xu
 * @Date: 2021/12/22 14:36
 * @Version: 1.0
 */
@Configuration("captchaConfiguration")
@EnableConfigurationProperties(EasyCaptchaProperties.class)
public class CaptchaConfiguration {

    @Autowired
    private EasyCaptchaProperties easyCaptchaProperties;

    @Bean
    public EasyCaptchaService easyCaptchaService() {
        return new EasyCaptchaServiceImpl(easyCaptchaProperties);
    }


    /**
     * 验证码配置,注入bean到spring中
     *
     * @return
     */
    @Bean
    public CaptchaController captchaController() {
        return new CaptchaController();
    }
}
