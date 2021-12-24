package club.laiyouxu.captcha.puzzle.service;

import club.laiyouxu.captcha.puzzle.PuzzleCaptchaDTO;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaResult;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaVerifyReq;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: puzzleCaptcha
 * @Author: Kurt Xu
 * @Date: 2021/12/22 19:35
 * @Version: 1.0
 */
public interface PuzzleCaptchaService {

    /**
     * 生成验证码通道
     * @return 验证码通道
     */
    String challenge();

    /**
     * 生成验证码
     *
     * @param challenge 验证码通道
     * @param response HttpServletResponse
     * @throws IOException 异常
     */
    PuzzleCaptchaDTO create(String challenge) throws IOException;

    /**
     * 校验验证码
     * @param verifyReq
     * @return
     */
    PuzzleCaptchaResult check(PuzzleCaptchaVerifyReq verifyReq);
}
