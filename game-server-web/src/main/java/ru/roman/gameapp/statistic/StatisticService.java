package ru.roman.gameapp.statistic;

/**
 * Сollects statistics on the game results
 */
public interface StatisticService {

    /**
     * Add Result of one user game round to statistic
     */
    void addRoundResult(RoundResult roundResult);

    /**
     * Returns game statistic for specified userId
     */
    GameStatistic getGameStatistic(String userId);
}
