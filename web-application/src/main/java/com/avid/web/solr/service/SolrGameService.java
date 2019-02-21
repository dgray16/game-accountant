package com.avid.web.solr.service;

import com.avid.web.solr.model.SolrGame;
import com.avid.web.solr.repository.SolrGameRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolrGameService extends DefaultSolrService<SolrGame> {

    SolrGameRepository solrGameRepository;

    public SolrGameService(SolrGameRepository repository) {
        super(repository);
        this.solrGameRepository = repository;
    }

    public Set<ObjectId> findGamesByName(String query) {
        List<SolrGame> foundGames = solrGameRepository.findAllByNameStartingWith(query);
        return foundGames.stream()
                .map(SolrGame::getId)
                .map(ObjectId::new)
                .collect(Collectors.toSet());
    }

    public void removeIndex(ObjectId objectId) {
        solrGameRepository.deleteById(objectId.toHexString());
    }

}
