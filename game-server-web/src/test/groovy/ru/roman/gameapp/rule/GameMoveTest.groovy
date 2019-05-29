package ru.roman.gameapp.rule

import groovy.transform.CompileStatic
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static ru.roman.gameapp.rule.GameMove.PAPER
import static ru.roman.gameapp.rule.GameMove.ROCK
import static ru.roman.gameapp.rule.GameMove.SCISSORS
import static ru.roman.gameapp.rule.PlayResult.DRAW
import static ru.roman.gameapp.rule.PlayResult.LOSE
import static ru.roman.gameapp.rule.PlayResult.WIN

@CompileStatic
@Test
class GameMoveTest {

    @DataProvider
    private Object[][] provider() {
        [
                [PAPER, SCISSORS, LOSE],
                [SCISSORS, SCISSORS, DRAW],
                [ROCK, SCISSORS, WIN],
        ] as Object[][]
    }

    @Test(dataProvider = "provider")
    void test(GameMove userMove, GameMove otherMove, PlayResult expected) {

        def actual = userMove.play(otherMove)
        assert actual == expected
    }
}