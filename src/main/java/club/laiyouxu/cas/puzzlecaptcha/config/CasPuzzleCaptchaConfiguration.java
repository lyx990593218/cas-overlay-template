package club.laiyouxu.cas.puzzlecaptcha.config;

import club.laiyouxu.cas.captcha.properties.CasCaptchaProperties;
import club.laiyouxu.cas.puzzlecaptcha.CasPuzzleCaptchaWebflowConfigurer;
import club.laiyouxu.cas.puzzlecaptcha.PuzzleCaptchaAction;
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
 * This is {@link CasPuzzleCaptchaConfiguration}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Configuration("casPuzzleCaptchaConfiguration")
@EnableConfigurationProperties(CasCaptchaProperties.class)
@ConditionalOnProperty(prefix = CasCaptchaProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class CasPuzzleCaptchaConfiguration extends CasWebflowContextConfiguration implements CasWebflowExecutionPlanConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CasCaptchaProperties casCaptchaProperties;

    @ConditionalOnMissingBean(name = "puzzleCaptchaWebflowConfigurer")
    @Bean
    @DependsOn("defaultWebflowConfigurer")
    public CasWebflowConfigurer puzzleCaptchaWebflowConfigurer() {
        return new CasPuzzleCaptchaWebflowConfigurer(builder(), loginFlowRegistry(), applicationContext, casCaptchaProperties);
    }
    @RefreshScope
    @Bean
    public Action puzzleCaptchaAction() {
        return new PuzzleCaptchaAction(casCaptchaProperties.getCaptcha());
    }

    @Override
    public void configureWebflowExecutionPlan(final CasWebflowExecutionPlan plan) {
        plan.registerWebflowConfigurer(puzzleCaptchaWebflowConfigurer());
    }
}
