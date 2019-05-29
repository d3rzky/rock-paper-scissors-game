package ru.roman.gameclient.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.roman.gameclient.rest.model.GameMove;
import ru.roman.gameclient.rest.model.PlayResult;
import ru.roman.gameclient.rest.model.Statistic;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

/**
 * Access game server via REST API
 */
@Component
class GameRestClient implements GameClient {
    private static final Logger log = LoggerFactory.getLogger(GameRestClient.class);

    private final RestTemplate restTemplate;

    public GameRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PlayResult play(String userId, GameMove move) {

        ResponseEntity<PlayResult> response = restTemplate.exchange(
                fromPath("/game/play")
                        .queryParam("userId", userId)
                        .queryParam("userMove", move).toUriString(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<PlayResult>() {}
        );
        PlayResult result = response.getBody();

        log.trace("Game result received: {}", result);
        return result;
    }

    @Override
    public Statistic getStatistic(String userId) {

        ResponseEntity<Statistic> response = restTemplate.exchange(
                fromPath("/game/statistic")
                        .queryParam("userId", userId).toUriString(),
                HttpMethod.GET,null,
                new ParameterizedTypeReference<Statistic>() {}
        );
        Statistic statistic = response.getBody();

        log.trace("Statistic received: {}", statistic);
        return statistic;
    }
}
