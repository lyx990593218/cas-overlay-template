package club.laiyouxu.captcha.puzzle.service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 文件服务类
 * @Author: Kurt Xu
 * @Date: 2021/12/8 12:09
 * @Version: 1.0
 */
public interface FileService {
    /**
     * 随机获取一个文件
     * @return InputStream
     */
    InputStream randomFile() throws IOException;
}
