package com.avid.web.system.config.web;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * @see <a href="https://www.baeldung.com/spring-data-solr">Apache Solr</a>
 */
@Configuration
@EnableSolrRepositories(basePackages = { "com.avid.web.solr" })
public class SolrConfig {

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient
                .Builder("http://localhost:8983/solr/")
                .build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        return new SolrTemplate(solrClient);
    }

}
