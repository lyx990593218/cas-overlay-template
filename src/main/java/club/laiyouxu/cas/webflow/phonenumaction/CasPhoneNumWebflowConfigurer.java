package club.laiyouxu.cas.webflow.phonenumaction;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: Kurt Xu
 * @Date: 2021/12/23 16:08
 * @Version: 1.0
 */
public class CasPhoneNumWebflowConfigurer extends AbstractCasWebflowConfigurer {
    public CasPhoneNumWebflowConfigurer(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry loginFlowDefinitionRegistry, ApplicationContext applicationContext, CasConfigurationProperties casProperties) {
        super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void doInitialize() {

        final Flow flow = getLoginFlow();
        if (flow != null) {
            final ActionState state = getState(flow, CasWebflowConstants.STATE_ID_REAL_SUBMIT, ActionState.class);
            final List<Action> currentActions = new ArrayList<>();
            state.getActionList().forEach(currentActions::add);
            currentActions.forEach(a -> state.getActionList().remove(a));

            state.getActionList().add(createEvaluateAction("phoneNumAction"));
            currentActions.forEach(a -> state.getActionList().add(a));
            state.getTransitionSet().add(createTransition("captchaError", CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));
        }
    }
}
