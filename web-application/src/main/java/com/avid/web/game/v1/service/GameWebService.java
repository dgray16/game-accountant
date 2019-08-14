package com.avid.web.game.v1.service;

import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.game.v1.model.dto.GameDto;
import com.avid.web.game.v1.model.mapper.GameDtoMapper;
import com.avid.web.game.v1.model.request.GetGamesRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameWebService {

    GameService gameService;

    //SolrGameService solrGameService; TODO enable in future

    public Flux<GameDto> getGames(GetGamesRequest request) {
        Flux<Game> games = Flux.empty();

        if (request.isQueryPresent()) {
            /*Set<ObjectId> gamesIds = solrGameService.findGamesByName(request.getQuery());
            games = gameService.findAllByIds(gamesIds);*/
        } else {
            games = gameService.findAll();
        }

        return games.map(GameDtoMapper.INSTANCE::map);
    }

    public Mono<GameDto> getGame(ObjectId id) {
        log.debug("{}", gameService.findById(id));
        return gameService.findById(id).map(GameDtoMapper.INSTANCE::map);
    }

    /* TODO verify that tests are working with it */
    public Mono<Void> delete(ObjectId id) {
        return gameService
                .delete(id)
                /*.doOnSuccess(res -> solrGameService.removeIndex(id))*/;
    }

}
