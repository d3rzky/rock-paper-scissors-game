package ru.roman.gameapp.statistic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;
import ru.roman.gameapp.rule.GameMove;
import ru.roman.gameapp.rule.PlayResult;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import static ru.roman.gameapp.rule.GameMove.PAPER;
import static ru.roman.gameapp.rule.GameMove.ROCK;
import static ru.roman.gameapp.rule.GameMove.SCISSORS;
import static ru.roman.gameapp.rule.PlayResult.DRAW;
import static ru.roman.gameapp.rule.PlayResult.LOSE;
import static ru.roman.gameapp.rule.PlayResult.WIN;

/**
 * Сollects statistics on the game results in local cache
 */
@Service
class StatisticCacheService implements StatisticService {

    /** Cache UserID to Statistic */
    private final Cache<String, GameStatisticData> cache = CacheBuilder.newBuilder()
            .maximumSize(1000L).build();


    @Override
    public void addRoundResult(RoundResult roundResult) {

        String userId = roundResult.getUserId();
        GameStatisticData stat = findInCache(userId);

        // !! concurrent modification very unlikely, but if any, possibly inconsistent state
        stat.userMoves.get(roundResult.getUserMove()).increment();
        stat.machineMoves.get(roundResult.getMachineMove()).increment();
        stat.results.get(roundResult.getResult()).increment();
    }

    @Override
    public GameStatistic getGameStatistic(String userId) {
        return findInCache(userId);
    }

    private GameStatisticData findInCache(String userId) {
        try {
            return cache.get(userId, GameStatisticData::new);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static class GameStatisticData implements GameStatistic {
        private final Map<PlayResult, LongAdder> results = prepareMap(PlayResult.values());
        private final Map<GameMove, LongAdder> userMoves = prepareMap(GameMove.values());
        private final Map<GameMove, LongAdder> machineMoves = prepareMap(GameMove.values());

        @Override
        public boolean isEmpty() {
            return results.isEmpty();
        }

        @Override
        public Map<PlayResult, Long> getResults() {
            return collectMap(results);
        }

        @Override
        public Map<GameMove, Long> getUserMoves() {
            return collectMap(userMoves);
        }

        @Override
        public Map<GameMove, Long> getMachineMoves() {
            return collectMap(machineMoves);
        }

        @Override
        public String asText() {
            String ls = System.lineSeparator();

            return new StringBuilder("Game statistic:").append(ls)
                    .append("\tUser won in ").append(getValue(results, WIN)).append(" games").append(ls)
                    .append("\tUser lost in ").append(getValue(results, LOSE)).append(" games").append(ls)
                    .append("\tDraw in ").append(getValue(results, DRAW)).append(" games").append(ls)
                    .append("\tTotal games played: ").append(sumOfValues(results)).append(ls)
                    .append("\tUser threw ").append(movesAsText(userMoves)).append(ls)
                    .append("\tMachine threw ").append(movesAsText(machineMoves)).append(ls)
                    .toString();
        }

        private static <T> Map<T, LongAdder> prepareMap(T[] keys) {
            return Arrays.stream(keys).collect(Collectors.toUnmodifiableMap(e -> e, e -> new LongAdder()));
        }

        private static <T> Map<T, Long> collectMap(Map<T, LongAdder> map) {
            return map.entrySet().stream()
                    .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, e -> e.getValue().longValue()));
        }

        private static long sumOfValues(Map<?, LongAdder> map) {
            return map.values().stream().mapToLong(LongAdder::longValue).sum();
        }

        private static String movesAsText(Map<GameMove, LongAdder> stat) {

            long total = sumOfValues(stat);
            return moveAsText(ROCK, stat, total)
                    + " " + moveAsText(PAPER, stat, total)
                    + " " + moveAsText(SCISSORS, stat, total);
        }

        private static String moveAsText(GameMove move, Map<GameMove, LongAdder> stat, long total) {
            long count = getValue(stat, move);
            return move + ": " + count + " (~" + (count * 100 / total) + "%)";
        }

        private static <T> long getValue(Map<T, LongAdder> stat, T key) {
            LongAdder val = stat.get(key);
            return val == null ? 0L : val.longValue();
        }
    }
}
