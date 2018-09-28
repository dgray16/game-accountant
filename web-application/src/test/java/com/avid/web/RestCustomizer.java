package com.avid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentationConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.StringUtils;

public class RestCustomizer implements WebTestClientBuilderCustomizer {

    private final RestDocsProperties properties;

    private final WebTestClientRestDocumentationConfigurer delegate;

    @Autowired
    ApplicationContext applicationContext;

    RestCustomizer(RestDocsProperties properties,
                   WebTestClientRestDocumentationConfigurer delegate) {
        this.properties = properties;
        this.delegate = delegate;
    }

    @Override
    public void customize(WebTestClient.Builder builder) {
        customizeBaseUrl(builder);
        builder.filter(this.delegate);
    }

    private void customizeBaseUrl(WebTestClient.Builder builder) {
        String scheme = this.properties.getUriScheme();
        String host = this.properties.getUriHost();
        String baseUrl = (StringUtils.hasText(scheme) ? scheme : "http") + "://"
                + (StringUtils.hasText(host) ? host : "localhost");
        Integer port = Integer.parseInt(applicationContext.getEnvironment().getProperty("local.server.port"));
        if (!isStandardPort(scheme, port)) {
            baseUrl += ":" + port;
        }
        builder.baseUrl(baseUrl);
    }

    private boolean isStandardPort(String scheme, Integer port) {
        if (port == null) {
            return true;
        }
        return (scheme.equals("http") && port == 80)
                || (scheme.equals("https") && port == 443);
    }

}
