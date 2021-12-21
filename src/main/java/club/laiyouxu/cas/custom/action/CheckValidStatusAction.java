package club.laiyouxu.cas.custom.action;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author laiyouxu
 */
@Slf4j
public class CheckValidStatusAction extends AbstractAction {

    private CentralAuthenticationService centralAuthenticationService;

    public CheckValidStatusAction(CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    @Override
    protected Event doExecute(RequestContext requestContext) throws IOException, URISyntaxException {
        String tgt = WebUtils.getTicketGrantingTicketId(requestContext);
        TicketGrantingTicket ticketGrantingTicket = this.centralAuthenticationService.getTicket(tgt, TicketGrantingTicket.class);
        Principal principal = ticketGrantingTicket.getAuthentication().getPrincipal();

        String userJson = principal.getId();

        log.debug("||||||||||, {}", userJson);
//        User user = JSON.toJavaObject(JSON.parseObject(userJson), User.class);
//        requestContext.getFlowScope().put("user_info", user);

        WebApplicationService webApplicationService = WebUtils.getService(requestContext);
        String service = webApplicationService == null ? null : webApplicationService.getOriginalUrl();
        requestContext.getFlowScope().put("redirect_service", service);

        if (noRegisterAndCompany("response")) {
            return this.getEventFactorySupport().event(this, "failed");
        } else {
            return this.getEventFactorySupport().event(this, "success");
        }
    }

    private boolean noRegisterAndCompany(String response) {
//        Object data = JSON.parseObject(response).get("data");
//        return data == null ? true :
//                !"2d0f4e4b-4a7f-463c-abb1-20d33ea14f1a".equals(JSON.parseObject(data.toString()).get("type"))
//                ;
        return true;
    }


}