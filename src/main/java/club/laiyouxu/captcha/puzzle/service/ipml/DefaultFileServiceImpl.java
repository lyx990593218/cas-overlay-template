package club.laiyouxu.captcha.puzzle.service.ipml;

import club.laiyouxu.captcha.puzzle.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * @Description: 默认文件服务类
 * @Author: Kurt Xu
 * @Date: 2021/12/8 12:10
 * @Version: 1.0
 */
@Slf4j
public class DefaultFileServiceImpl implements FileService {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public InputStream randomFile() throws IOException {
        Resource[] resources = applicationContext.getResources("classpath*:images/captcha/*.jpg");
        int randNum = new Random().nextInt(resources.length);
        return resources[randNum].getInputStream();
    }
}
