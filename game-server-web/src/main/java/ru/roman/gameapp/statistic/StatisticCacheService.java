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

        private static <T> Map<T, LongAdder> prepareMap(T[] keys) {
            return Arrays.stream(keys).collect(Collectors.toUnmodifiableMap(e -> e, e -> new LongAdder()));
        }

        private static <T> Map<T, Long> collectMap(Map<T, LongAdder> map) {
            return map.entrySet().stream()
                    .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, e -> e.getValue().longValue()));
        }
    }
}
