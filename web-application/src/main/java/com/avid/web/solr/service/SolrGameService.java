package com.avid.web.solr.service;

import com.avid.core.domain.model.entity.Game;
import com.avid.web.solr.model.SolrGame;
import com.avid.web.solr.repository.SolrGameRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* TODO make base class, updateIndex could be as default method for any service */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolrGameService {

    SolrGameRepository solrGameRepository;

    public Set<ObjectId> findGamesByName(String query) {
        List<SolrGame> foundGames = solrGameRepository.findAllByNameStartingWith(query);
        return foundGames.stream()
                .map(SolrGame::getId)
                .map(ObjectId::new)
                .collect(Collectors.toSet());
    }

    public void updateIndex(Game game) {
        solrGameRepository.save(SolrGame.of(game));
    }

    public void removeIndex(ObjectId objectId) {
        solrGameRepository.deleteById(objectId.toHexString());
    }

}
