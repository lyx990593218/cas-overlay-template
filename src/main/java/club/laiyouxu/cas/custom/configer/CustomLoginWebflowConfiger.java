package club.laiyouxu.cas.custom.configer;

import club.laiyouxu.cas.custom.credential.MyUsernamePasswordCredential;
import club.laiyouxu.cas.custom.properties.CustomCasConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.BinderConfiguration;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Slf4j
public class CustomLoginWebflowConfiger extends AbstractCustomLoginWebflowConfigurer {

    public CustomLoginWebflowConfiger(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry flowDefinitionRegistry, ApplicationContext applicationContext, CustomCasConfigurationProperties casProperties) {
        super(flowBuilderServices, flowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void createRememberMeAuthnWebflowConfig(Flow flow){

        if (this.casProperties.getCasProperties().getTicket().getTgt().getRememberMe().isEnabled()) {
            this.createFlowVariable(flow, "credential", RememberMeUsernamePasswordCredential.class);
            ViewState state = (ViewState)this.getState(flow, "viewLoginForm", ViewState.class);
            BinderConfiguration cfg = this.getViewStateBinderConfiguration(state);
            cfg.addBinding(new BinderConfiguration.Binding("rememberMe", (String)null, false));
        } else {
            this.createFlowVariable(flow, "credential", MyUsernamePasswordCredential.class);
        }
    }

    //generateServiceTicket后加入验证用户是否进行过二次注册checkValidStatus
//    @Override
//    protected void createGenerateServiceTicketAction(final Flow flow) {
//        ActionState handler = this.createActionState(flow, "generateServiceTicket", this.createEvaluateAction("generateServiceTicketAction"));
//        this.createTransitionForState(handler, "success", "checkValidStatus");
//        this.createTransitionForState(handler, "warn", "warn");
//        this.createTransitionForState(handler, "authenticationFailure", "handleAuthenticationFailure");
//        this.createTransitionForState(handler, "error", "initializeLoginForm");
//        this.createTransitionForState(handler, "gateway", "gatewayServicesManagementCheck");
//
//        createCheckValidStatusAction(flow);
//    }

//    @Override
//    protected void createInitialAuthenticationRequestValidationCheckAction(final Flow flow) {
//        ActionState action = this.createActionState(flow, "initialAuthenticationRequestValidationCheck", "initialAuthenticationRequestValidationAction");
//        this.createTransitionForState(action, "authenticationFailure", "handleAuthenticationFailure");
//        this.createTransitionForState(action, "error", "initializeLoginForm");
//        this.createTransitionForState(action, "success", "checkValidStatus");
//        this.createTransitionForState(action, "successWithWarnings", "showAuthenticationWarningMessages");
//
//        createCheckValidStatusAction(flow);
//    }

    //serviceCheck后加入验证用户是否进行过状态验证checkValidStatus
//    @Override
//    protected void createServiceCheckDecisionState(Flow flow) {
//        this.createDecisionState(flow, "serviceCheck", "flowScope.service != null", "generateServiceTicket", "checkValidStatus");
//    }

    //hasServiceCheck后加入验证用户是否进行过状态验证checkValidStatus
//    @Override
//    protected void createHasServiceCheckDecisionState(Flow flow) {
//        this.createDecisionState(flow, "hasServiceCheck", "flowScope.service != null", "renewRequestCheck", "checkValidStatus");
//    }

    //创建serviceCheck action
/*    private void createCheckValidStatusAction(Flow flow) {
        ActionState actionState = this.createActionState(flow, "checkValidStatus", this.createEvaluateAction("checkValidStatusAction"));
        //验证没有通过，跳转回登录页
        this.createTransitionForState(actionState, "failed", CasWebflowConstants.STATE_ID_HANDLE_AUTHN_FAILURE);
        //验证通过，跳转到判断是否有service回调地址
        this.createTransitionForState(actionState, "success", "ticketGrantingTicketCheck");

//        createAddUserView(flow);
//        createRegisteredUserView(flow);
//        createCheckServiceDecisionState(flow);

    }*/

    //如果有回调地址service则重定向,如果么有则跳转到默认页面
//    private void createCheckServiceDecisionState(Flow flow){
//        this.createDecisionState(flow, "checkServiceDecisionState", "flowScope.service != null", "redirect", "registeredUser");
//    }
//
//    private void createAddUserView(Flow flow){
//        EndState state = this.createEndState(flow, "addUser", "addUser");
//    }
//
//    private void createRegisteredUserView(Flow flow){
//        EndState state = this.createEndState(flow, "registeredUser", "registeredUser");
//    }

}
