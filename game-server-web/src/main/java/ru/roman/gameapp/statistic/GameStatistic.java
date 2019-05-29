package ru.roman.gameapp.statistic;

import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.rule.PlayResult;

import java.util.Map;

/**
 * User game results statistics
 */
public interface GameStatistic {

    /**
     * Returns true if statistic still empty for user
     */
    boolean isEmpty();

    /**
     * Returns game results statistic
     */
    Map<PlayResult, Long> getResults();

    /**
     * Returns statistic of user moves
     */
    Map<GameMove, Long> getUserMoves();

    /**
     * Returns statistic of machine moves
     */
    Map<GameMove, Long> getMachineMoves();
}
