package ru.roman.gameclient.rest;

import ru.roman.gameclient.rest.model.GameMove;
import ru.roman.gameclient.rest.model.PlayResult;
import ru.roman.gameclient.rest.model.Statistic;

/**
 * Access to game server
 */
public interface GameClient {

    /**
     * Play game request
     */
    PlayResult play(String userId, GameMove move);

    /**
     * Statistic request
     */
    Statistic getStatistic(String userId);
}
