package ru.roman.gameapp;

import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.rule.PlayResult;
import ru.roman.gameapp.statistic.GameStatistic;
import ru.roman.gameapp.statistic.RoundResult;
import ru.roman.gameapp.statistic.StatisticService;
import ru.roman.gameapp.strategy.DecisionService;

import java.util.Scanner;

import static ru.roman.gameapp.rule.PlayResult.DRAW;
import static ru.roman.gameapp.rule.PlayResult.WIN;


public class ConsoleApp {
    private static final String USER_ID = "userId";

    private static final DecisionService decisionService = null;
    private static final StatisticService statisticService = null;

    public static void main(String[] args) {
        System.out.printf("Let's play the game ROCK, PAPER, SCISSORS.%n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printRules();
            String userInput = scanner.next().trim().toUpperCase();

            if ("EXIT".equals(userInput) || "E".equals(userInput)) {
                break;
            }

            GameMove userMove = GameMove.parse(userInput);
            if (userMove == null) {
                System.out.printf("Invalid input: '%s', try again%n", userInput);
                continue;
            }

            GameMove machineMove = decisionService.getNextMove(USER_ID);
            PlayResult result = userMove.play(machineMove);

            printResult(userMove, machineMove, result);

            statisticService.addRoundResult(new RoundResult(USER_ID, result, userMove, machineMove));
        }

        printStatistic();
    }

    private static void printRules() {
        System.out.print("Type Exit (E) to exit the game, " +
                "or choose your next move: ROCK (R), PAPER (P) or SCISSORS (S): ");
    }

    private static void printResult(GameMove userMove, GameMove machineMove, PlayResult result) {

        System.out.printf("You threw %s against %s. ", userMove, machineMove);

        if (result == DRAW) {
            System.out.println("The game result is a draw.");
        } else if (result == WIN) {
            System.out.println("You win.");
        } else {
            System.out.println("You lose.");
        }
    }

    private static void printStatistic() {

        GameStatistic statistic = statisticService.getGameStatistic(USER_ID);
        System.out.printf("GAME OVER.%n%s%n", statistic.asText());
    }
}
