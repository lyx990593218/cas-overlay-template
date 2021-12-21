package club.laiyouxu.cas.custom.config;

import club.laiyouxu.cas.custom.configer.CustomLoginWebflowConfiger;
import club.laiyouxu.cas.custom.properties.CustomCasConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration("customCasWebflowContextConfiguration")
@EnableConfigurationProperties({CustomCasConfigurationProperties.class})
@Slf4j
public class CustomCasWebflowContextConfiguration extends CasWebflowContextConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomCasConfigurationProperties casProperties;

    @Bean
    @Order(-1)
    @Override
    public CasWebflowConfigurer defaultWebflowConfigurer() {
        CustomLoginWebflowConfiger c = new CustomLoginWebflowConfiger(builder(), loginFlowRegistry(), this.applicationContext, this.casProperties);
        c.setLogoutFlowDefinitionRegistry(logoutFlowRegistry());
        return c;
    }
}
