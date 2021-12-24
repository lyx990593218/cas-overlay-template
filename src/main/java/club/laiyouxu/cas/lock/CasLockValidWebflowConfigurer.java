package club.laiyouxu.cas.lock;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 登录次数验证
 * @Author: Kurt Xu
 * @Date: 2021/12/23 11:25
 * @Version: 1.0
 */
@Slf4j
public class CasLockValidWebflowConfigurer extends AbstractCasWebflowConfigurer {
    public CasLockValidWebflowConfigurer(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry loginFlowDefinitionRegistry, ApplicationContext applicationContext, CasConfigurationProperties casProperties) {
        super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
    }

    @Override
    protected void doInitialize() {
        final Flow flow = getLoginFlow();
        if (flow != null) {
            createValidateRecaptchaAction(flow);
        }
    }

    private void createValidateRecaptchaAction(final Flow flow) {
        final ActionState state = getState(flow, CasWebflowConstants.STATE_ID_REAL_SUBMIT, ActionState.class);
        final List<Action> currentActions = new ArrayList<>();
        state.getActionList().forEach(currentActions::add);
        currentActions.forEach(a -> state.getActionList().remove(a));

        state.getActionList().add(createEvaluateAction("lockValidAction"));
        currentActions.forEach(a -> state.getActionList().add(a));
        state.getTransitionSet().add(createTransition("captchaError", CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM));
    }
}
