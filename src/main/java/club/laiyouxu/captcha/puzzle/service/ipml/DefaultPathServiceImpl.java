package club.laiyouxu.captcha.puzzle.service.ipml;

import club.laiyouxu.captcha.puzzle.path.*;
import club.laiyouxu.captcha.puzzle.service.PathService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: 默认抠图路径service
 * @Author: Kurt Xu
 * @Date: 2021/12/9 16:06
 * @Version: 1.0
 */
public class DefaultPathServiceImpl implements PathService {

    List<PuzzlePath> paths = new ArrayList<>();

    public DefaultPathServiceImpl() {
        paths.add(new UpperRightConvexLeftConcavePath());
        paths.add(new UpperLeftConvexRightConcavePath());
        paths.add(new RightInferiorConcavePath());
        paths.add(new UpperLeftConcavePath());
        paths.add(new LeftRightConcavePath());
        paths.add(new TotalConcavePath());
    }

    public PuzzlePath randomPath() {
        int randNum = new Random().nextInt(paths.size());
        return paths.get(randNum);
    }


}
