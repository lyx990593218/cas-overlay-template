package club.laiyouxu.cas.webflow.phonenumaction;

import club.laiyouxu.cas.webflow.credential.CustomPhoneNumCredential;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy;
import org.apereo.cas.web.flow.actions.AbstractNonInteractiveCredentialsAction;
import org.apereo.cas.web.flow.resolver.CasDelegatingWebflowEventResolver;
import org.apereo.cas.web.flow.resolver.CasWebflowEventResolver;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/23 16:05
 * @Version: 1.0
 */
@Slf4j
public class PhoneNumAction extends AbstractNonInteractiveCredentialsAction {
    public PhoneNumAction(CasDelegatingWebflowEventResolver initialAuthenticationAttemptWebflowEventResolver, CasWebflowEventResolver serviceTicketRequestWebflowEventResolver, AdaptiveAuthenticationPolicy adaptiveAuthenticationPolicy) {
        super(initialAuthenticationAttemptWebflowEventResolver, serviceTicketRequestWebflowEventResolver, adaptiveAuthenticationPolicy);
    }

    @Override
    protected Credential constructCredentialsFromRequest(RequestContext context) {
        try {
            final HttpServletRequest request;
            request = WebUtils.getHttpServletRequestFromExternalWebflowContext(context);
            CustomPhoneNumCredential credentials = new CustomPhoneNumCredential(request.getParameter("phone"), request.getParameter("validCode"));
            if (credentials != null) {
                log.debug("Received phoneNum authentication request from credentials [{}]", credentials);
                return credentials;
            }
        } catch (final Exception e) {
            log.warn(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
