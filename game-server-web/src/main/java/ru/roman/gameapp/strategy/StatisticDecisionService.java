package ru.roman.gameapp.strategy;

import org.springframework.stereotype.Service;
import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.statistic.GameStatistic;
import ru.roman.gameapp.statistic.StatisticService;

/**
 * Decisions about next move against user,
 * based on statistic.
 */
@Service
class StatisticDecisionService implements DecisionService {

    private final StatisticService statisticService;
    private final RandomMoveGenerator randomMoveGenerator;
    private final MovePredictionService movePredictionService;

    StatisticDecisionService(StatisticService statisticService,
                             RandomMoveGenerator randomMoveGenerator,
                             MovePredictionService movePredictionService) {
        this.statisticService = statisticService;
        this.randomMoveGenerator = randomMoveGenerator;
        this.movePredictionService = movePredictionService;
    }

    @Override
    public GameMove getNextMove(String userId) {

        GameStatistic statistic = statisticService.getGameStatistic(userId);

        if (statistic.isEmpty()) {
            return randomMoveGenerator.getNextRandomMove();
        }

        return movePredictionService.getNextOptimalMove(statistic);
    }
}
