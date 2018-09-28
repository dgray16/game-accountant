package com.avid.web;

import com.avid.core.domain.model.entity.Game;
import org.junit.Rule;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsProperties;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsWebTestClientConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.reactive.SpringBootWebTestClientBuilderCustomizer;
import org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientBuilderCustomizer;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentationConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import java.util.Collection;
import java.util.List;

@Configuration
@EnableConfigurationProperties(RestDocsProperties.class)
public class Config {

    /*@LocalServerPort
    private int port;*/

    @Autowired
    ApplicationContext applicationContext;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    /*@Bean*/
    WebTestClient webTestClient(List<WebTestClientBuilderCustomizer> customizers) {
        String port = this.applicationContext.getEnvironment()
                .getProperty("local.server.port", "8080");
        String baseUrl = "http" + "://localhost:" + port;
        WebTestClient.Builder builder = WebTestClient.bindToServer();
        customizeWebTestClientCodecs(builder, this.applicationContext);

        for (WebTestClientBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }

        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebTestClientRestDocumentationConfigurer restDocsWebTestClientConfigurer(
            ObjectProvider<RestDocsWebTestClientConfigurationCustomizer> configurationCustomizerProvider) {
        WebTestClientRestDocumentationConfigurer configurer = WebTestClientRestDocumentation
                .documentationConfiguration(restDocumentation);
        RestDocsWebTestClientConfigurationCustomizer configurationCustomizer = configurationCustomizerProvider
                .getIfAvailable();
        if (configurationCustomizer != null) {
            configurationCustomizer.customize(configurer);
        }
        return configurer;
    }

    @Bean
    public WebTestClientBuilderCustomizer restDocumentationConfigurer(RestDocsProperties properties,
                                                                      WebTestClientRestDocumentationConfigurer configurer) {
        return new RestCustomizer(properties, configurer);
    }

    private void customizeWebTestClientCodecs(WebTestClient.Builder clientBuilder,
                                              ApplicationContext context) {
        Collection<CodecCustomizer> codecCustomizers = context.getBeansOfType(CodecCustomizer.class).values();
        if (!CollectionUtils.isEmpty(codecCustomizers)) {
            clientBuilder.exchangeStrategies(ExchangeStrategies.builder()
                    .codecs((codecs) -> codecCustomizers.forEach((codecCustomizer) -> codecCustomizer.customize(codecs)))
                    .build());
        }
    }

}
