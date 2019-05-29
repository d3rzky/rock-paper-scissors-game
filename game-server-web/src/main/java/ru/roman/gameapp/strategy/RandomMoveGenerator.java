package ru.roman.gameapp.strategy;

import org.springframework.stereotype.Service;
import ru.roman.gameapp.rule.GameMove;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generation of random moves
 */
@Service
class RandomMoveGenerator {

    /**
     * Returns next random move
     */
    GameMove getNextRandomMove() {

        int randomVal = ThreadLocalRandom.current().nextInt(3);
        return GameMove.values()[randomVal];
    }
}
