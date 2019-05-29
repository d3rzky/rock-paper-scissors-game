package ru.roman.gameclient;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import ru.roman.gameclient.rest.GameClient;
import ru.roman.gameclient.rest.model.GameMove;
import ru.roman.gameclient.rest.model.PlayResult;
import ru.roman.gameclient.rest.model.Statistic;

import java.util.Scanner;
import java.util.UUID;

import static ru.roman.gameclient.rest.model.GameMove.parse;
import static ru.roman.gameclient.rest.model.PlayResult.WIN;

@Component
class ConsoleGame implements SmartInitializingSingleton {
    private String userId;

    private final GameClient gameClient;
    private final StatisticPrintHelper statisticPrintHelper;

    public ConsoleGame(GameClient gameClient,
                       StatisticPrintHelper statisticPrintHelper) {
        this.gameClient = gameClient;
        this.statisticPrintHelper = statisticPrintHelper;
    }

    @Override
    public void afterSingletonsInstantiated() {

        userId = UUID.randomUUID().toString();
        playGame();
    }

    private void playGame() {
        System.out.printf("Let's play the game ROCK, PAPER, SCISSORS.%n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printRules();
            String userInput = scanner.next().trim().toUpperCase();

            if ("EXIT".equals(userInput) || "E".equals(userInput)) {
                break;
            }

            GameMove userMove = parse(userInput);
            if (userMove == null) {
                System.out.printf("Invalid input: '%s', try again%n", userInput);
                continue;
            }

            PlayResult result = gameClient.play(userId, userMove);
            printResult(userMove, result);
        }

        printStatistic();
    }

    private void printRules() {
        System.out.print("Type Exit (E) to exit the game, " +
                "or choose your next move: ROCK (R), PAPER (P) or SCISSORS (S): ");
    }

    private void printResult(GameMove userMove, PlayResult result) {

        System.out.printf("You threw %s. ", userMove);

        if (result == PlayResult.DRAW) {
            System.out.println("The game result is a draw.");
        } else if (result == WIN) {
            System.out.println("You win.");
        } else {
            System.out.println("You lose.");
        }
    }

    private void printStatistic() {

        Statistic statistic = gameClient.getStatistic(userId);
        System.out.printf("GAME OVER.%n%s%n", statisticPrintHelper.asText(statistic));
    }
}
