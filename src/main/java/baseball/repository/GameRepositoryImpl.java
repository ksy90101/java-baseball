package baseball.repository;

import baseball.domain.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class GameRepositoryImpl implements GameRepository {
    private static final Map<Integer, Game> GAME_RECORDS = new HashMap<>();
    private static int id = 1;

    @Override
    public int insert(final Game game) {
        game.setId(id);
        GAME_RECORDS.put(id, game);
        id++;

        return game.getId();
    }

    @Override
    public Optional<Game> findById(final int gameId) {
        return Optional.ofNullable(GAME_RECORDS.get(gameId));
    }

    @Override
    public List<Game> findAll() {
        return GAME_RECORDS.values()
                .stream()
                .toList();
    }

    @Override
    public List<Game> findAllByPlayerTimes(final int playerTimes) {
        return GAME_RECORDS.values()
                .stream()
                .filter(game -> game.samePlayerTimes(playerTimes))
                .toList();
    }

    @Override
    public List<Integer> findIdsByLimitPlayerTimes(final int limitPlayerTimes, final Predicate<Game> winnerPredicate) {
        return GAME_RECORDS.values()
                .stream()
                .filter(game -> game.sameLimitPlayerTimes(limitPlayerTimes))
                .filter(winnerPredicate)
                .map(Game::getId)
                .toList();
    }

    @Override
    public int getMinPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .mapToInt(Game::getPlayerTimes)
                .min()
                .orElse(0);
    }

    @Override
    public int getMaxPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .mapToInt(Game::getPlayerTimes)
                .max()
                .orElse(0);
    }

    @Override
    public double getAveragePlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .mapToInt(Game::getPlayerTimes)
                .average()
                .orElse(0);
    }

    @Override
    public int getMaxCountLimitPlayerTimes() {
        final Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        final Optional<Map.Entry<Integer, Long>> mostFrequent = frequencyMap.entrySet()
                .stream()
                .max(comparingByValue());

        return mostFrequent.map(Map.Entry::getKey).orElse(0);
    }

    @Override
    public int getMinCountLimitPlayerTimes() {
        final Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        final Optional<Map.Entry<Integer, Long>> mostFrequent = frequencyMap.entrySet()
                .stream()
                .min(comparingByValue());

        return mostFrequent.map(Map.Entry::getKey).orElse(0);
    }

    @Override
    public int getMaxLimitPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .max(Comparable::compareTo)
                .orElse(0);
    }

    @Override
    public int getMinLimitPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .min(Comparable::compareTo)
                .orElse(0);
    }

    @Override
    public int getMaxLimitPlayerTimesByWinnerComputer() {
        final Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .filter(Game::isComputerWin)
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        final Optional<Map.Entry<Integer, Long>> max = frequencyMap.entrySet()
                .stream()
                .max(comparingByValue());

        return max.map(Map.Entry::getKey).orElse(0);

    }

    @Override
    public int getMaxLimitPlayerTimesByWinnerPlayer() {
        final Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .filter(Game::isPlayerWin)
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        final Optional<Map.Entry<Integer, Long>> max = frequencyMap.entrySet()
                .stream()
                .max(comparingByValue());

        return max.map(Map.Entry::getKey).orElse(0);
    }

    @Override
    public double getAverageLimitPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .mapToInt(Game::getLimitPlayerTimes)
                .average()
                .orElse(0);
    }

    @Override
    public int getCountByWinnerComputer() {
        return (int) GAME_RECORDS.values()
                .stream()
                .filter(Game::isComputerWin)
                .count();
    }

    @Override
    public int getCountByWinnerPlayer() {
        return (int) GAME_RECORDS.values()
                .stream()
                .filter(Game::isPlayerWin)
                .count();
    }

    @Override
    public void clear() {
        GAME_RECORDS.clear();
    }


}
