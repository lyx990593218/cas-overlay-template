package club.laiyouxu.cas.custom.configer;

import club.laiyouxu.cas.custom.properties.CustomCasConfigurationProperties;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.web.flow.configurer.DefaultLoginWebflowConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Slf4j
public abstract class AbstractCustomLoginWebflowConfigurer extends DefaultLoginWebflowConfigurer {

    protected final CustomCasConfigurationProperties casProperties;

    private int order;
    private String name = this.getClass().getSimpleName();

    public AbstractCustomLoginWebflowConfigurer(FlowBuilderServices flowBuilderServices, FlowDefinitionRegistry loginFlowDefinitionRegistry, ApplicationContext applicationContext, CustomCasConfigurationProperties casProperties) {
        super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties.getCasProperties());
        this.casProperties = casProperties;
    }
}
