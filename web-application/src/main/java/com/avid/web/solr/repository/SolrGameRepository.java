package com.avid.web.solr.repository;

import com.avid.web.solr.model.SolrGame;

import java.util.List;

public interface SolrGameRepository /*extends SolrCrudRepository<SolrGame, String>*/ {

    List<SolrGame> findAllByNameStartingWith(String name);

}
