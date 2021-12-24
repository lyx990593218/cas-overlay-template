package club.laiyouxu.captcha.config;

import club.laiyouxu.captcha.controller.CaptchaController;
import club.laiyouxu.captcha.controller.PuzzleCaptchaController;
import club.laiyouxu.captcha.easy.properties.EasyCaptchaProperties;
import club.laiyouxu.captcha.easy.service.EasyCaptchaService;
import club.laiyouxu.captcha.easy.service.impl.EasyCaptchaServiceImpl;
import club.laiyouxu.captcha.puzzle.service.FileService;
import club.laiyouxu.captcha.puzzle.service.PathService;
import club.laiyouxu.captcha.puzzle.service.PuzzleCaptchaService;
import club.laiyouxu.captcha.puzzle.service.ipml.DefaultFileServiceImpl;
import club.laiyouxu.captcha.puzzle.service.ipml.DefaultPathServiceImpl;
import club.laiyouxu.captcha.puzzle.service.ipml.PuzzleCaptchaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
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
public class CaptchaConfigurer {

    @Autowired
    private EasyCaptchaProperties easyCaptchaProperties;

    @Bean
    public EasyCaptchaService easyCaptchaService() {
        return new EasyCaptchaServiceImpl(easyCaptchaProperties);
    }

    @Bean
    public FileService fileService() {
        return new DefaultFileServiceImpl();
    }

    @Bean
    public PathService pathService() {
        return new DefaultPathServiceImpl();
    }

    @Bean
    public PuzzleCaptchaService puzzleCaptchaService() {
        return new PuzzleCaptchaServiceImpl();
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

    @Bean
    public PuzzleCaptchaController puzzleCaptchaController() {
        return new PuzzleCaptchaController();
    }
}
