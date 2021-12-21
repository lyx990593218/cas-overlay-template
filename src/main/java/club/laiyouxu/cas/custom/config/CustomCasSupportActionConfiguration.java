package club.laiyouxu.cas.custom.config;

import club.laiyouxu.cas.custom.action.CheckValidStatusAction;
import club.laiyouxu.cas.custom.properties.CustomCasConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.web.config.CasSupportActionsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.webflow.execution.Action;

@Configuration("customCasSupportActionConfiguration")
@EnableConfigurationProperties(CustomCasConfigurationProperties.class)
@EnableTransactionManagement(proxyTargetClass = true)
@Slf4j
public class CustomCasSupportActionConfiguration extends CasSupportActionsConfiguration {

    @Autowired
    @Qualifier("centralAuthenticationService")
    protected CentralAuthenticationService centralAuthenticationService;

    @RefreshScope
    @ConditionalOnMissingBean(name = "checkValidStatusAction")
    @Bean
    public Action checkValidStatusAction(){
        return new CheckValidStatusAction(centralAuthenticationService);
    }
}
