package club.laiyouxu.cas.custom.config;

import club.laiyouxu.cas.exception.UsernameOrPasswordError;
import club.laiyouxu.cas.exception.ValidTypeError;
import club.laiyouxu.cas.exception.VerificationCodeError;
import org.apereo.cas.web.flow.actions.AuthenticationExceptionHandlerAction;
import org.apereo.cas.web.flow.config.CasCoreWebflowConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.webflow.execution.Action;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description: 认证异常处理配置类
 * @Author: Kurt Xu
 * @Date: 2021/12/18 10:36
 * @Version: 1.0
 */
public class AuthenticationExceptionHandlerConfig extends CasCoreWebflowConfiguration {
    @Bean
    @Override
    public Action authenticationExceptionHandler() {
        Set<Class<? extends Throwable>> errors = new LinkedHashSet(this.handledAuthenticationExceptions());
        errors.add(UsernameOrPasswordError.class);
        errors.add(VerificationCodeError.class);
        errors.add(ValidTypeError.class);
        return new AuthenticationExceptionHandlerAction(errors);
    }
}
