package com.avid.web.test.game;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.game.v1.controller.GameController;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

/**
 * Test for {@link GameController}.
 */
//@AutoConfigureRestDocs
// webTestClient filter - JunitRestDocumentation
// @AutoConfigureDataMongo
// EmbeddedMongoAutoConfiguration
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameTest {

    MongodExecutable mongoExecutable;

    static final String HOST = "localhost";
    static final Integer PORT = 27018;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    GameService gameService;

    @Before
    @SneakyThrows
    public void setupDb() {
        IMongodConfig mongoConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(HOST, PORT, Network.localhostIsIPv6()))
                .build();

        mongoExecutable = MongodStarter.getDefaultInstance().prepare(mongoConfig);
        /*mongoExecutable.start();*/
    }

    @After
    public void cleanDb() {
        /* TODO try to stop executable, because data has been shared between tests or remove all data after each test? */
        /*mongoExecutable.stop();*/
    }

    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(new MongoClient(HOST, PORT), "game_accountant_test");
    }

    @Test
    public void testGetGames() {
        Game game = new Game();
        game.setName("PUBG");
        game.setGenres(List.of(GameGenre.SHOOTER, GameGenre.SURVIVAL));
        gameService.create(game).subscribe();

        /* TODO
         * 1. Create In memory instance of mongo for tests
         * 2. REST DOCS!
         */
        webTestClient.get().uri("/api/v1/games")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].name").isEqualTo(game.getName());
    }

    @Test
    public void testAnother() {
        Game game = gameService.findAll().blockFirst();
        System.out.println("");
    }


}
