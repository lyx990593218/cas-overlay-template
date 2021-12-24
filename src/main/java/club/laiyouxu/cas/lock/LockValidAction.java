package club.laiyouxu.cas.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * @Description: 登录次数验证
 * @Author: Kurt Xu
 * @Date: 2021/12/23 11:22
 * @Version: 1.0
 */
@Slf4j
public class LockValidAction extends AbstractAction {

    /**
     * Captcha error event.
     */
    public static final String EVENT_ID_ERROR = "captchaError";

    @Override
    protected Event doExecute(RequestContext requestContext) throws Exception {
        log.debug("第三个");
        return null;
    }

    private Event getError(final RequestContext requestContext) {
        final MessageContext messageContext = requestContext.getMessageContext();
        messageContext.addMessage(new MessageBuilder().error().code(EVENT_ID_ERROR).defaultText(EVENT_ID_ERROR).build());
        return getEventFactorySupport().event(this, EVENT_ID_ERROR);
    }
}
