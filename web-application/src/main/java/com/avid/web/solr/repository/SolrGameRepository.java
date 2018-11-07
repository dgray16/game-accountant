package com.avid.web.solr.repository;

import com.avid.web.solr.model.SolrGame;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface SolrGameRepository extends SolrCrudRepository<SolrGame, String> {



}
