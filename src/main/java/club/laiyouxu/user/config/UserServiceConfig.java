package club.laiyouxu.user.config;

import club.laiyouxu.user.mock.MockUserService;
import club.laiyouxu.user.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 用户相关配置
 * @Author: Kurt Xu
 * @Date: 2021/12/20 11:46
 * @Version: 1.0
 */
@Configuration
public class UserServiceConfig {
    @Bean
    @ConditionalOnMissingBean(name = "userService")
    public UserService userService(){
        return new MockUserService();
    }
}
