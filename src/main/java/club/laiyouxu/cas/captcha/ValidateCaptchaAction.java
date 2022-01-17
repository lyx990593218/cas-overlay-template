package club.laiyouxu.cas.captcha;

import club.laiyouxu.cas.captcha.properties.CaptchaProperties;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * This is {@link ValidateCaptchaAction}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Slf4j
@AllArgsConstructor
public class ValidateCaptchaAction extends AbstractAction {
    /**
     * Recaptcha response as a request parameter.
     */
    public static final String REQUEST_PARAM_RECAPTCHA_RESPONSE = "recaptcha";
    /**
     * Captcha error event.
     */
    public static final String EVENT_ID_ERROR = "captchaError";

    private static final ObjectReader READER = new ObjectMapper().findAndRegisterModules().reader();

    private final CaptchaProperties captchaProperties;

    @Override
    protected Event doExecute(final RequestContext requestContext) {
        final HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
        final String gRecaptchaResponse = request.getParameter(REQUEST_PARAM_RECAPTCHA_RESPONSE);
        log.debug("第一个");
        String loginMethod = request.getParameter("loginMethod");
        String validCode = request.getParameter("validCode");
        if ("validCode".equals(loginMethod)) {
            if (StrUtil.isBlank(validCode)) {
                log.debug("第一个报错!");
                return getError(requestContext);
            }
        }
        return null;
    }

    private Event getError(final RequestContext requestContext) {
        final MessageContext messageContext = requestContext.getMessageContext();
        messageContext.addMessage(new MessageBuilder().error().code(EVENT_ID_ERROR).defaultText(EVENT_ID_ERROR).build());
        return getEventFactorySupport().event(this, EVENT_ID_ERROR);
    }
}
