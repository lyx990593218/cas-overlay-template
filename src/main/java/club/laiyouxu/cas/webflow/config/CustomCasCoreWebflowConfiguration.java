package club.laiyouxu.cas.webflow.config;

import club.laiyouxu.cas.webflow.configer.CustomLoginWebflowConfiger;
import club.laiyouxu.cas.webflow.handler.CustomPhoneNumAuthentication;
import club.laiyouxu.cas.webflow.handler.CustomUsernamePasswordAuthentication;
import club.laiyouxu.cas.webflow.properties.CustomCasConfigurationProperties;
import club.laiyouxu.cas.exception.UsernameOrPasswordError;
import club.laiyouxu.cas.exception.VerificationCodeError;
import club.laiyouxu.cas.param.ParamManager;
import club.laiyouxu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.actions.AuthenticationExceptionHandlerAction;
import org.apereo.cas.web.flow.config.CasCoreWebflowConfiguration;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.webflow.execution.Action;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description: 认证异常处理配置类
 * @Author: Kurt Xu
 * @Date: 2021/12/18 10:36
 * @Version: 1.0
 */

@Configuration("customCasCoreWebflowConfiguration")
@EnableConfigurationProperties(CustomCasConfigurationProperties.class)
@EnableTransactionManagement(proxyTargetClass = true)
@Slf4j
public class CustomCasCoreWebflowConfiguration extends CasCoreWebflowConfiguration implements AuthenticationEventExecutionPlanConfigurer {

    @Bean
    @Override
    public Action authenticationExceptionHandler() {
        Set<Class<? extends Throwable>> errors = new LinkedHashSet(this.handledAuthenticationExceptions());
        errors.add(UsernameOrPasswordError.class);
        errors.add(VerificationCodeError.class);
        return new AuthenticationExceptionHandlerAction(errors);
    }


    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    @Qualifier("ticketRedisTemplate")
    RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("userService")
    UserService userService;

    @Autowired
    @Qualifier("paramManager")
    private ParamManager paramManager;

    @Bean
    public AuthenticationHandler customUsernamePasswordAuthentication() {
        return new CustomUsernamePasswordAuthentication(
                CustomUsernamePasswordAuthentication.class.getName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                1,
                redisTemplate,
                userService,
                paramManager
        );
    }

    @Bean
    public AuthenticationHandler customPhoneNumAuthentication() {
        return new CustomPhoneNumAuthentication(
                CustomPhoneNumAuthentication.class.getName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                2);
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(customUsernamePasswordAuthentication());
        plan.registerAuthenticationHandler(customPhoneNumAuthentication());
    }


    @Configuration("customCasWebflowContextConfiguration")
    @EnableConfigurationProperties({CustomCasConfigurationProperties.class})
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
}
