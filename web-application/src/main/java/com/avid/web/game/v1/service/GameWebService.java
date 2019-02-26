package com.avid.web.game.v1.service;

import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.config.web.HATEOASRelationship;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.model.dto.GameDTO;
import com.avid.web.game.v1.model.request.GetGamesRequest;
import com.avid.web.solr.service.SolrGameService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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

        return games
                .map(game -> {
                    GameDTO dto = new GameDTO();

                    dto.setName(game.getName());
                    dto.setGameGenres(game.getGenres());

                    Link getLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder.methodOn(GameController.class).getGame(game.getId()))
                            .withSelfRel();
                    dto.add(getLink);

                    Link deleteLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder.methodOn(GameController.class).deleteGame(game.getId()))
                            .withRel(HATEOASRelationship.DELETE.getValue());
                    dto.add(deleteLink);

                    return dto;
                });
    }

    public Mono<GameDTO> getGame(ObjectId id) {
        return gameService.findById(id)
                .map(game -> {
                    GameDTO dto = new GameDTO();

                    dto.setGameGenres(game.getGenres());
                    dto.setName(game.getName());

                    Link deleteLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder.methodOn(GameController.class).deleteGame(game.getId()))
                            .withRel(HATEOASRelationship.DELETE.getValue());
                    dto.add(deleteLink);

                    return dto;
                });
    }

    /* TODO verify that tests are working with it */
    public Mono<Void> delete(ObjectId id) {
        return gameService
                .delete(id)
                .doOnSuccess(res -> solrGameService.removeIndex(id));
    }
}
