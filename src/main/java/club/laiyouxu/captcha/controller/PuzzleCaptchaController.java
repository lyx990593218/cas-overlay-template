package club.laiyouxu.captcha.controller;

import club.laiyouxu.captcha.puzzle.PuzzleCaptchaDTO;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaResult;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaVerifyReq;
import club.laiyouxu.captcha.puzzle.service.PuzzleCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 拼图验证码获取
 * @Author: Kurt Xu
 * @Date: 2021/12/23 10:00
 * @Version: 1.0
 */
@RestController
@RequestMapping("/puzzle/captcha")
public class PuzzleCaptchaController {

    @Autowired
    private PuzzleCaptchaService puzzleCaptchaService;

    @GetMapping("/")
    public Map<String, String> getChallenge() {
        Map<String, String> map = new HashMap<>();
        map.put("challenge", puzzleCaptchaService.challenge());
        return map;
    }

    @GetMapping("/{challenge}")
    public PuzzleCaptchaDTO create(@PathVariable String challenge) throws IOException {
        return puzzleCaptchaService.create(challenge);
    }

    @PostMapping("/verify")
    public PuzzleCaptchaResult verify(@RequestBody PuzzleCaptchaVerifyReq verifyReq) throws IOException {
        return puzzleCaptchaService.check(verifyReq);
    }
}
