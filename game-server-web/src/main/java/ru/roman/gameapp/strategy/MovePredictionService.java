package ru.roman.gameapp.strategy;

import org.springframework.stereotype.Service;
import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.statistic.GameStatistic;

import java.util.Comparator;
import java.util.Map;

/**
 * Prediction of the next user move
 */
@Service
class MovePredictionService {

    /**
     * Returns next optimal move, based on statistics provided
     */
    GameMove getNextOptimalMove(GameStatistic statistic) {
        if (statistic.isEmpty()) {
            throw new IllegalStateException("Statistic cannot by empty");
        }

        GameMove move = findMostFrequentMove(statistic);

        return move.getBittenBy();
    }

    private GameMove findMostFrequentMove(GameStatistic statistic) {
        Map<GameMove, Long> userMoves = statistic.getUserMoves();

        Map.Entry<GameMove, Long> mostFrequent = userMoves.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)).get();
        return mostFrequent.getKey();
    }
}
