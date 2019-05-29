package ru.roman.gameapp.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.rule.PlayResult;
import ru.roman.gameapp.statistic.GameStatistic;
import ru.roman.gameapp.statistic.RoundResult;
import ru.roman.gameapp.statistic.StatisticService;
import ru.roman.gameapp.strategy.DecisionService;

/**
 * REST API Controller
 */
@RestController
class GameController {

    private final DecisionService decisionService;
    private final StatisticService statisticService;

    GameController(DecisionService decisionService, StatisticService statisticService) {
        this.decisionService = decisionService;
        this.statisticService = statisticService;
    }

    @GetMapping("/api/game/play")
    public PlayResult play(@RequestParam("userId") String userId,
                           @RequestParam("userMove") GameMove userMove) {

        GameMove machineMove = decisionService.getNextMove(userId);
        PlayResult result = userMove.play(machineMove);

        statisticService.addRoundResult(new RoundResult(userId, result, userMove, machineMove));

        return result;
    }

    @GetMapping("/api/game/statistic")
    public Statistic getStatistic(@RequestParam("userId") String userId) {

        GameStatistic statistic = statisticService.getGameStatistic(userId);

        return new Statistic(
                statistic.getResults(),
                statistic.getUserMoves(),
                statistic.getMachineMoves()
        );
    }
}
