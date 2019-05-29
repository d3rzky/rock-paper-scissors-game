package ru.roman.gameapp.restapi;

import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.rule.PlayResult;

import java.util.Map;
import java.util.StringJoiner;

/**
 * User game results statistics
 */
public class Statistic {

    private Map<PlayResult, Long> results;
    private Map<GameMove, Long> userMoves;
    private Map<GameMove, Long> machineMoves;

    public Statistic() {
    }

    Statistic(Map<PlayResult, Long> results,
              Map<GameMove, Long> userMoves,
              Map<GameMove, Long> machineMoves) {
        this.results = results;
        this.userMoves = userMoves;
        this.machineMoves = machineMoves;
    }

    public Map<PlayResult, Long> getResults() {
        return results;
    }

    public void setResults(Map<PlayResult, Long> results) {
        this.results = results;
    }

    public Map<GameMove, Long> getUserMoves() {
        return userMoves;
    }

    public void setUserMoves(Map<GameMove, Long> userMoves) {
        this.userMoves = userMoves;
    }

    public Map<GameMove, Long> getMachineMoves() {
        return machineMoves;
    }

    public void setMachineMoves(Map<GameMove, Long> machineMoves) {
        this.machineMoves = machineMoves;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Statistic.class.getSimpleName() + "[", "]")
                .add("results=" + results)
                .add("userMoves=" + userMoves)
                .add("machineMoves=" + machineMoves)
                .toString();
    }
}
