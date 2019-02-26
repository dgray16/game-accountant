package com.avid.web.game.v1.service;

import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.game.v1.model.dto.GameDTO;
import com.avid.web.game.v1.model.request.GetGamesRequest;
import com.avid.web.solr.service.SolrGameService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameWebService {

    GameService gameService;

    SolrGameService solrGameService;

    public Flux<GameDTO> getGames(GetGamesRequest request) {
        Flux<Game> games;

        if (request.isQueryPresent()) {
            Set<ObjectId> gamesIds = solrGameService.findGamesByName(request.getQuery());
            games = gameService.findAllByIds(gamesIds);
        } else {
            games = gameService.findAll();
        }

        return games.map(GameDTO::of);
    }

    public Mono<GameDTO> getGame(ObjectId id) {
        return gameService.findById(id).map(GameDTO::of);
    }

    /* TODO verify that tests are working with it */
    public Mono<Void> delete(ObjectId id) {
        return gameService
                .delete(id)
                .doOnSuccess(res -> solrGameService.removeIndex(id));
    }

}
