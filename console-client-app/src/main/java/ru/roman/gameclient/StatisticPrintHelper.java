package ru.roman.gameclient;

import org.springframework.stereotype.Component;
import ru.roman.gameclient.rest.model.GameMove;
import ru.roman.gameclient.rest.model.PlayResult;
import ru.roman.gameclient.rest.model.Statistic;

import java.util.Map;

import static ru.roman.gameclient.rest.model.GameMove.PAPER;
import static ru.roman.gameclient.rest.model.GameMove.ROCK;
import static ru.roman.gameclient.rest.model.GameMove.SCISSORS;
import static ru.roman.gameclient.rest.model.PlayResult.DRAW;
import static ru.roman.gameclient.rest.model.PlayResult.LOSE;
import static ru.roman.gameclient.rest.model.PlayResult.WIN;

/**
 * Text representation of statistic
 */
@Component
class StatisticPrintHelper {

    String asText(Statistic statistic) {

        Map<PlayResult, Long> results = statistic.getResults();
        Map<GameMove, Long> userMoves = statistic.getUserMoves();
        Map<GameMove, Long> machineMoves = statistic.getMachineMoves();
        String ls = System.lineSeparator();

        return new StringBuilder("Game statistic:").append(ls)
                .append("\tUser won in ").append(results.get(WIN)).append(" games").append(ls)
                .append("\tUser lost in ").append(results.get(LOSE)).append(" games").append(ls)
                .append("\tDraw in ").append(results.get(DRAW)).append(" games").append(ls)
                .append("\tTotal games played: ").append(sumOfValues(results)).append(ls)
                .append("\tUser threw ").append(movesAsText(userMoves)).append(ls)
                .append("\tMachine threw ").append(movesAsText(machineMoves)).append(ls)
                .toString();
    }

    private long sumOfValues(Map<?, Long> map) {
        return map.values().stream().mapToLong(Long::longValue).sum();
    }

    private String movesAsText(Map<GameMove, Long> stat) {

        long total = sumOfValues(stat);
        return moveAsText(ROCK, stat, total)
                + " " + moveAsText(PAPER, stat, total)
                + " " + moveAsText(SCISSORS, stat, total);
    }

    private String moveAsText(GameMove move, Map<GameMove, Long> stat, long total) {
        long count = stat.get(move);
        return move + ": " + count + " (~" + (count * 100 / total) + "%)";
    }
}
