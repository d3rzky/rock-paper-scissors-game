package ru.roman.gameclient.rest;

import ru.roman.gameclient.rest.model.GameMove;
import ru.roman.gameclient.rest.model.PlayResult;
import ru.roman.gameclient.rest.model.Statistic;

public interface GameClient {
    PlayResult play(String userId, GameMove move);

    Statistic getStatistic(String userId);
}
