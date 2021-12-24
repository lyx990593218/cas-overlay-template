package club.laiyouxu.captcha.puzzle.service.ipml;

import club.laiyouxu.captcha.puzzle.PuzzleCaptcha;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaDTO;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaResult;
import club.laiyouxu.captcha.puzzle.PuzzleCaptchaVerifyReq;
import club.laiyouxu.captcha.puzzle.service.FileService;
import club.laiyouxu.captcha.puzzle.service.PathService;
import club.laiyouxu.captcha.puzzle.service.PuzzleCaptchaService;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static cn.hutool.core.util.StrUtil.uuid;

/**
 * @Description: puzzleCaptcha实现
 * @Author: Kurt Xu
 * @Date: 2021/12/22 19:39
 * @Version: 1.0
 */
@Slf4j
public class PuzzleCaptchaServiceImpl implements PuzzleCaptchaService {

    @Autowired
    private FileService fileService;

    @Autowired
    private PathService pathService;

    private final Map<String, Object> map = new ConcurrentHashMap<>();

    /**
     * 验证码常量
     */
    private static final String PUZZLE_CAPTCHA = "puzzle:captcha:";
    /**
     * 验证码验证结果常量
     */
    private static final String PUZZLE_CAPTCHA_RESULT = "puzzle:captcha:result:";
    /**
     * 验证码刷新次数常量
     */
    private static final String PUZZLE_CAPTCHA_REFRESH = "puzzle:captcha:refresh:";
    /**
     * 验证通道常量
     */
    private static final String PUZZLE_CAPTCHA_CHALLENGE = "puzzle:captcha:challenge:";
    /**
     * 验证码刷新次数限制
     */
    private static final int LIMIT = 4;

    /**
     * x轴误差范围
     */
    private static final int X_OFFSET = 8;
    /**
     * 时间间隔
     */
    private static final int SPEED = 500;

    @Override
    public String challenge() {
        String challengeId = uuid();
        map.put(PUZZLE_CAPTCHA_CHALLENGE + challengeId, challengeId);
        return challengeId;
    }

    @Override
    public PuzzleCaptchaDTO create(String challenge) throws IOException {
        if (!map.containsKey(PUZZLE_CAPTCHA_CHALLENGE + challenge)) {
            throw new RuntimeException("验证码不正确!");
        }
        int number = 1;
        String limit = (String) map.get(PUZZLE_CAPTCHA_REFRESH + challenge);
        log.debug("" + limit);
        if (limit != null) {
            number += Integer.parseInt(limit);
            if (number > LIMIT) {
                map.remove(PUZZLE_CAPTCHA_CHALLENGE + challenge);
                map.remove(PUZZLE_CAPTCHA + challenge);
                map.remove(PUZZLE_CAPTCHA_REFRESH + challenge);
                throw new RuntimeException("尝试过多");
            }
        }
        PuzzleCaptcha puzzleCaptcha = null;
        try {
            puzzleCaptcha = new PuzzleCaptcha(fileService.randomFile(), pathService.randomPath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("文件不存在");
        }
        puzzleCaptcha.setImageQuality(Image.SCALE_AREA_AVERAGING);
        puzzleCaptcha.run();
        Map<String, Object> cacheMap = new HashMap<>();
        PuzzleCaptchaDTO captchaVo = new PuzzleCaptchaDTO();
        captchaVo.setChallenge(challenge);
        captchaVo.setBigImage(ImgUtil.toBase64DataUri(puzzleCaptcha.getArtwork(), "png"));
        captchaVo.setSmallImage(ImgUtil.toBase64DataUri(puzzleCaptcha.getVacancy(), "png"));
        // 偏移量
        cacheMap.put("x", puzzleCaptcha.getX());
        cacheMap.put("time", System.currentTimeMillis());
        cacheMap.put("width", puzzleCaptcha.getWidth());
        log.debug("" + puzzleCaptcha.getX());
        log.debug("" + puzzleCaptcha.getWidth());
        map.put(PUZZLE_CAPTCHA + challenge, cacheMap);
        map.put(PUZZLE_CAPTCHA_REFRESH + challenge, number);
        return captchaVo;
    }

    @Override
    public PuzzleCaptchaResult check(PuzzleCaptchaVerifyReq verifyReq) {
        PuzzleCaptchaResult result = new PuzzleCaptchaResult();
        String key = PUZZLE_CAPTCHA + verifyReq.getChallenge();
        // 偏移量
        Integer vx = verifyReq.getX();
        // 宽度
        Integer width = verifyReq.getWidth();
        //缓存
        Map<String, Object> cacheMap = (Map<String, Object>) map.get(key);
        if (cacheMap == null) {
            throw new RuntimeException("验证码已过期，请刷新验证码！");
        }
        Integer x = Integer.parseInt(cacheMap.get("x").toString());
        Integer realWidth = Integer.parseInt(cacheMap.get("width").toString());
        long time = Long.parseLong(cacheMap.get("time").toString());
        long s = System.currentTimeMillis() - time;
        // 查看前端的缩放比例
        double ratio = NumberUtil.div(realWidth, width).doubleValue();
        if (x == null || vx == null) {
            throw new RuntimeException("验证失败，请重试！");
        } else if (Math.abs(x - (vx * ratio)) > X_OFFSET * ratio || s < SPEED) {
            throw new RuntimeException("验证失败，请重试！");
        }
        result.setSuccess(true);
        result.setMessage("验证成功");
        result.setValidCode(uuid());
        map.put(PUZZLE_CAPTCHA_RESULT + result.getValidCode(), result);
        map.remove(key);
        map.remove(PUZZLE_CAPTCHA_REFRESH + verifyReq.getChallenge());
        map.remove(PUZZLE_CAPTCHA_CHALLENGE + verifyReq.getChallenge());
        return result;
    }
}
