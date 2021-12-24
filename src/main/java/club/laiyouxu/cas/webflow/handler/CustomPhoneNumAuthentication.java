package club.laiyouxu.cas.webflow.handler;

import club.laiyouxu.cas.webflow.credential.CustomPhoneNumCredential;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import java.security.GeneralSecurityException;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/23 14:00
 * @Version: 1.0
 */
@Slf4j
public class CustomPhoneNumAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler {
    public CustomPhoneNumAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        log.debug("做点什么");
        return null;
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof CustomPhoneNumCredential;
    }
}
