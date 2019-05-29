package ru.roman.gameapp.rule;

import static ru.roman.gameapp.rule.PlayResult.DRAW;
import static ru.roman.gameapp.rule.PlayResult.LOSE;
import static ru.roman.gameapp.rule.PlayResult.WIN;

/**
 * Possible moves in Game
 */
public enum GameMove {

    ROCK,
    PAPER,
    SCISSORS;

    static {
        ROCK.bittenBy = PAPER;
        PAPER.bittenBy = SCISSORS;
        SCISSORS.bittenBy = ROCK;
    }

    /**
     * Move which beats
     */
    private GameMove bittenBy;
    /**
     * Shortcut of move
     */
    private final String alias;

    GameMove() {
        this.alias = String.valueOf(this.name().toCharArray()[0]);
    }

    /**
     * Finds the move corresponding to string value
     */
    public static GameMove parse(String value) {
        for (GameMove move : values()) {
            if (move.alias.equals(value) ||
                    move.toString().equals(value)) {
                return move;
            }
        }
        return null;
    }

    /**
     * Returns the result of the game relative to the opponent’s move
     */
    public PlayResult play(GameMove other) {
        return this == other
                ? DRAW
                : this.bittenBy == other ? LOSE : WIN;
    }

    /**
     * Returns Move which beats
     */
    public GameMove getBittenBy() {
        return bittenBy;
    }
}
