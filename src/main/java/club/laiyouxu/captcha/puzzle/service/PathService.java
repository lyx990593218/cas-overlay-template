package club.laiyouxu.captcha.puzzle.service;

import club.laiyouxu.captcha.puzzle.path.PuzzlePath;

/**
 * @Description: 抠图路径service
 * @Author: Kurt Xu
 * @Date: 2021/12/9 16:21
 * @Version: 1.0
 */
public interface PathService {
    /**
     * 随机返回一种路径
     * @return 抠图路径
     */
    PuzzlePath randomPath();
}
