package ru.roman.gameclient.rest.model;

/**
 * Possible moves in Game
 */
public enum GameMove {

    ROCK,
    PAPER,
    SCISSORS;

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
}
