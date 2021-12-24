package club.laiyouxu.captcha.controller;

import club.laiyouxu.captcha.easy.service.EasyCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/22 17:51
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private EasyCaptchaService easyCaptchaService;

    @GetMapping("/{deviceId}")
    public void captcha(@PathVariable String deviceId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug(deviceId);
        easyCaptchaService.create(deviceId,response);
    }

    @GetMapping("/check/{deviceId}")
    public boolean check(@PathVariable String deviceId, @RequestParam(value = "code") String code, HttpServletResponse response){
        return easyCaptchaService.check(deviceId,code);
    }
}
