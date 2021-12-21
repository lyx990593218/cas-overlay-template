package club.laiyouxu.cas.webflow.configer;

import club.laiyouxu.cas.webflow.properties.CustomCasConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Slf4j
public abstract class AbstractCustomLoginWebflowConfigurer extends DefaultLoginWebflowConfigurer {

    protected final CustomCasConfigurationProperties customCasConfigurationProperties;

    public AbstractCustomLoginWebflowConfigurer(FlowBuilderServices flowBuilderServices,
                                                FlowDefinitionRegistry loginFlowDefinitionRegistry,
                                                ApplicationContext applicationContext,
                                                CustomCasConfigurationProperties customCasConfigurationProperties) {
        super(flowBuilderServices, loginFlowDefinitionRegistry,
                applicationContext, customCasConfigurationProperties.getCasProperties());
        this.customCasConfigurationProperties = customCasConfigurationProperties;
    }
}
