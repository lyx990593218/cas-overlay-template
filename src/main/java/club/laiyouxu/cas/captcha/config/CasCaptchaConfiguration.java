package club.laiyouxu.cas.captcha.config;

import club.laiyouxu.cas.captcha.properties.CasCaptchaProperties;
import club.laiyouxu.cas.captcha.CasCaptchaWebflowConfigurer;
import club.laiyouxu.cas.captcha.ValidateCaptchaAction;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.webflow.execution.Action;

/**
 * This is {@link CasCaptchaConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("casCaptchaConfiguration")
@EnableConfigurationProperties(CasCaptchaProperties.class)
@ConditionalOnProperty(prefix = CasCaptchaProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class CasCaptchaConfiguration extends CasWebflowContextConfiguration implements CasWebflowExecutionPlanConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CasCaptchaProperties casCaptchaProperties;

    @ConditionalOnMissingBean(name = "captchaWebflowConfigurer")
    @Bean
    @DependsOn("defaultWebflowConfigurer")
    public CasWebflowConfigurer captchaWebflowConfigurer() {
        return new CasCaptchaWebflowConfigurer(builder(), loginFlowRegistry(), applicationContext, casCaptchaProperties);
    }

    @RefreshScope
    @Bean
    public Action validateCaptchaAction() {
        return new ValidateCaptchaAction(casCaptchaProperties.getCaptcha());
    }

    @Override
    public void configureWebflowExecutionPlan(final CasWebflowExecutionPlan plan) {
        plan.registerWebflowConfigurer(captchaWebflowConfigurer());
    }
}
