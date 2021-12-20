package club.laiyouxu.cas.custom.config;

import club.laiyouxu.cas.custom.credential.ValidCodeCredential;
import club.laiyouxu.cas.custom.handler.CustomUsernamePasswordAuthentication;
import club.laiyouxu.cas.custom.handler.ValidCodeAuthentication;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

public class Config implements AuthenticationEventExecutionPlanConfigurer {

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    private RedisTemplate redisTemplate;

    //AuthenticationHandler依赖 "dataSource","mapperScannerConfigurer","sqlSessionFactory" 这三个bean
    @Bean
//    @DependsOn({"dataSource","mapperScannerConfigurer","sqlSessionFactory"})
    public AuthenticationHandler authenticationHandler(){

        return new CustomUsernamePasswordAuthentication(
                CustomUsernamePasswordAuthentication.class.getName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                1
        );
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
