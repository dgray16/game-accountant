package com.avid.web.solr.service;

import com.avid.web.solr.model.SolrGame;
import com.avid.web.solr.repository.SolrGameRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolrGameService extends DefaultSolrService<SolrGame> {

    //SolrGameRepository solrGameRepository;

    /*public SolrGameService(SolrGameRepository repository) {
        super(repository);
        his.solrGameRepository = repository;
    }*/

    /*public Set<ObjectId> findGamesByName(String query) {
        List<SolrGame> foundGames = Collections.emptyList();//solrGameRepository.findAllByNameStartingWith(query);
        return foundGames.stream()
                .map(SolrGame::getId)
                .map(ObjectId::new)
                .collect(Collectors.toSet());
    }*/

}
