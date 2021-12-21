package club.laiyouxu.cas.custom.config;

import club.laiyouxu.cas.custom.credential.ValidCodeCredential;
import club.laiyouxu.cas.custom.handler.CustomUsernamePasswordAuthentication;
import club.laiyouxu.cas.custom.handler.ValidCodeAuthentication;
import club.laiyouxu.cas.custom.valid.service.ValidServiceAbstractFactory;
import club.laiyouxu.cas.param.ParamManager;
import club.laiyouxu.user.service.UserService;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

public class Config implements AuthenticationEventExecutionPlanConfigurer {

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidServiceAbstractFactory validServiceAbstractFactory;

    @Value("${enable-validation:false}")
    private boolean isValid;

    @Autowired
    private ParamManager paramManager;

    //AuthenticationHandler依赖 "dataSource","mapperScannerConfigurer","sqlSessionFactory" 这三个bean
    @Bean
//    @DependsOn({"dataSource","mapperScannerConfigurer","sqlSessionFactory"})
    public AuthenticationHandler authenticationHandler() {

        return new CustomUsernamePasswordAuthentication(
                CustomUsernamePasswordAuthentication.class.getName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                1,
                redisTemplate,
                userService,
                validServiceAbstractFactory,
                isValid,
                paramManager);
    }

    @Bean
    public ValidCodeAuthentication validCodeAuthentication() {
        return new ValidCodeAuthentication(
                ValidCodeCredential.class.getName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                2,
                redisTemplate);
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(authenticationHandler());
        plan.registerAuthenticationHandler(validCodeAuthentication());
    }

}
