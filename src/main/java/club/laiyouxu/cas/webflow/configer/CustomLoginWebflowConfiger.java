package club.laiyouxu.cas.webflow.configer;

import club.laiyouxu.cas.webflow.credential.CustomPhoneNumCredential;
import club.laiyouxu.cas.webflow.credential.CustomUsernamePasswordCredential;
import club.laiyouxu.cas.webflow.properties.CustomCasConfigurationProperties;
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

        if (this.customCasConfigurationProperties
                .getCasProperties()
                .getTicket()
                .getTgt()
                .getRememberMe()
                .isEnabled()) {
            this.createFlowVariable(flow, "credential", RememberMeUsernamePasswordCredential.class);
            ViewState state = (ViewState)this.getState(flow, "viewLoginForm", ViewState.class);
            BinderConfiguration cfg = this.getViewStateBinderConfiguration(state);
            cfg.addBinding(new BinderConfiguration.Binding("rememberMe", (String)null, false));
        } else {
            this.createFlowVariable(flow, "credential", CustomUsernamePasswordCredential.class);
            //this.createFlowVariable(flow, "credential2", CustomPhoneNumCredential.class);
        }
    }
}
