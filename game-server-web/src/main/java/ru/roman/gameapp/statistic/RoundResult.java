package ru.roman.gameapp.statistic;

import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.rule.PlayResult;

import java.util.StringJoiner;

/**
 * Result of one user game round
 */
public class RoundResult {

    private String userId;
    private PlayResult result;
    private GameMove userMove;
    private GameMove machineMove;

    public RoundResult() {
    }

    public RoundResult(String userId, PlayResult result, GameMove userMove, GameMove machineMove) {
        this.userId = userId;
        this.result = result;
        this.userMove = userMove;
        this.machineMove = machineMove;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PlayResult getResult() {
        return result;
    }

    public void setResult(PlayResult result) {
        this.result = result;
    }

    public GameMove getUserMove() {
        return userMove;
    }

    public void setUserMove(GameMove userMove) {
        this.userMove = userMove;
    }

    public GameMove getMachineMove() {
        return machineMove;
    }

    public void setMachineMove(GameMove machineMove) {
        this.machineMove = machineMove;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RoundResult.class.getSimpleName() + "[", "]")
                .add("userId='" + userId + "'")
                .add("result=" + result)
                .add("userMove=" + userMove)
                .add("machineMove=" + machineMove)
                .toString();
    }
}
