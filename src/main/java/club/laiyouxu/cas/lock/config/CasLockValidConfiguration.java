package club.laiyouxu.cas.lock.config;

import club.laiyouxu.cas.captcha.properties.CasCaptchaProperties;
import club.laiyouxu.cas.lock.CasLockValidWebflowConfigurer;
import club.laiyouxu.cas.lock.LockValidAction;
import club.laiyouxu.cas.puzzlecaptcha.CasPuzzleCaptchaWebflowConfigurer;
import club.laiyouxu.cas.puzzlecaptcha.PuzzleCaptchaAction;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.configuration.CasConfigurationProperties;
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
 * @Description: 登录次数验证配置
 * @Author: Kurt Xu
 * @Date: 2021/12/23 11:35
 * @Version: 1.0
 */
@Configuration("casLockValidConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@Slf4j
public class CasLockValidConfiguration extends CasWebflowContextConfiguration implements CasWebflowExecutionPlanConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CasConfigurationProperties casConfigurationProperties;

    @ConditionalOnMissingBean(name = "casLockValidWebflowConfigurer")
    @Bean
    @DependsOn("defaultWebflowConfigurer")
    public CasWebflowConfigurer casLockValidWebflowConfigurer() {
        return new CasLockValidWebflowConfigurer(builder(), loginFlowRegistry(), applicationContext, casConfigurationProperties);
    }

    @RefreshScope
    @Bean
    public Action lockValidAction() {
        return new LockValidAction();
    }

    @Override
    public void configureWebflowExecutionPlan(final CasWebflowExecutionPlan plan) {
        plan.registerWebflowConfigurer(casLockValidWebflowConfigurer());
    }
}
