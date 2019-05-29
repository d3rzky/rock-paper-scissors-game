package ru.roman.gameapp.strategy;

import ru.roman.gameapp.rule.GameMove;

/**
 * Makes decisions about next move against user
 */
public interface DecisionService {

    /**
     * Returns next move against user
     */
    GameMove getNextMove(String userId);
}
