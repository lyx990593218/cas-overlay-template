package club.laiyouxu.cas.oauth.config;

import club.laiyouxu.cas.oauth.custom.CustomOAuth20UserProfileViewRenderer;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.support.oauth.web.views.OAuth20UserProfileViewRenderer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("customAuthenticationConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomOAuthConfiguration {
    @Bean
    @RefreshScope
    public OAuth20UserProfileViewRenderer oauthUserProfileViewRenderer() {
        CustomOAuth20UserProfileViewRenderer customOAuth20UserProfileViewRenderer = new CustomOAuth20UserProfileViewRenderer();
        return customOAuth20UserProfileViewRenderer;
    }
}
