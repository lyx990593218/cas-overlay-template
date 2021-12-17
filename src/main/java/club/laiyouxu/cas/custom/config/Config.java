package club.laiyouxu.cas.custom.config;

import club.laiyouxu.cas.custom.handler.CustomUsernamePasswordAuthentication;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

public class Config implements AuthenticationEventExecutionPlanConfigurer {

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

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

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(authenticationHandler());
    }

}
