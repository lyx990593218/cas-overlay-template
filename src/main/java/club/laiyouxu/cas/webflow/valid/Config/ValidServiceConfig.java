package club.laiyouxu.cas.webflow.valid.Config;

import club.laiyouxu.cas.webflow.valid.service.Impl.ValidServiceFactory;
import club.laiyouxu.cas.webflow.valid.service.ValidServiceAbstractFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 验证service配置
 * @Author: Kurt Xu
 * @Date: 2021/12/20 11:49
 * @Version: 1.0
 */
@Configuration
public class ValidServiceConfig {

    @Bean
    @ConditionalOnMissingBean(name = "validServiceAbstractFactory")
    public ValidServiceAbstractFactory validServiceAbstractFactory(){
        return new ValidServiceFactory();
    }
}
