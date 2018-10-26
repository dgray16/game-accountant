package com.avid.web.game.v1.service;

import com.avid.core.domain.service.GameService;
import com.avid.web.config.web.HATEOASRelationship;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.model.GameDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameWebService {

    GameService gameService;

    ModelMapper modelMapper;

    public Flux<GameDTO> getGames() {
        return gameService.findAll()
                .map(game -> {
                    GameDTO dto = modelMapper.map(game, GameDTO.class);

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
                    GameDTO dto = modelMapper.map(game, GameDTO.class);

                    Link deleteLink = ControllerLinkBuilder
                            .linkTo(ControllerLinkBuilder.methodOn(GameController.class).deleteGame(game.getId()))
                            .withRel(HATEOASRelationship.DELETE.getValue());
                    dto.add(deleteLink);

                    return dto;
                });
    }

    public Mono<Void> delete(ObjectId id) {
        return gameService.delete(id);
    }
}
