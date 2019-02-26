package com.avid.web.config.web;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * @see <a href="https://www.baeldung.com/spring-data-solr">Apache Solr</a>
 */
@EnableSolrRepositories(basePackages = { "com.avid.web.solr" })
public class SolrConfig {

    public SolrClient solrClient() {
        return new HttpSolrClient
                .Builder("http://localhost:8983/solr/")
                .build();
    }

    public SolrTemplate solrTemplate(SolrClient solrClient) {
        return new SolrTemplate(solrClient);
    }

}
