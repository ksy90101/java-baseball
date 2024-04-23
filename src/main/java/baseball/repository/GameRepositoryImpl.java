package baseball.repository;

import baseball.domain.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class GameRepositoryImpl implements GameRepository {
    private static final Map<Integer, Game> GAME_RECORDS = new HashMap<>();
    private static int id = 1;

    @Override
    public int insert(Game game) {
        game.setId(id);
        GAME_RECORDS.put(id, game);
        id++;

        return game.getId();
    }

    @Override
    public Optional<Game> findById(int gameId) {
        return Optional.ofNullable(GAME_RECORDS.get(gameId));
    }

    @Override
    public List<Game> findAll() {
        return GAME_RECORDS.values()
                .stream()
                .toList();
    }

    @Override
    public List<Game> findAllByPlayerTimes(int playerTimes) {
        return GAME_RECORDS.values()
                .stream()
                .filter(game -> game.samePlayerTimes(playerTimes))
                .toList();
    }

    @Override
    public List<Integer> findIdsByLimitPlayerTimes(int limitPlayerTimes) {
        return GAME_RECORDS.values()
                .stream()
                .filter(game -> game.sameLimitPlayerTimes(limitPlayerTimes))
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

    // 가장 많이 적용된 승리/패패 횟수
    @Override
    public int getMaxCountLimitPlayerTimes() {
        Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> mostFrequent = frequencyMap.entrySet()
                .stream()
                .max(comparingByValue());

        return mostFrequent.map(Map.Entry::getKey).orElse(0);
    }

    // 가장 적게 적용된 승리/패패 횟수
    @Override
    public int getMinCountLimitPlayerTimes() {
        Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> mostFrequent = frequencyMap.entrySet()
                .stream()
                .min(comparingByValue());

        return mostFrequent.map(Map.Entry::getKey).orElse(0);
    }

    // 가장 큰 값으로 승리/패패 횟수
    @Override
    public int getMaxLimitPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .max(Comparable::compareTo)
                .orElse(0);
    }

    // 가장 낮은 값으로 승리/패패 횟수
    @Override
    public int getMinLimitPlayerTimes() {
        return GAME_RECORDS.values()
                .stream()
                .map(Game::getLimitPlayerTimes)
                .min(Comparable::compareTo)
                .orElse(0);
    }

    // 컴퓨터가 가장 많이 승리한 승리/패패 횟수
    @Override
    public int getMaxLimitPlayerTimesByWinnerComputer() {
        Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .filter(Game::isComputerWin)
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> mostFrequent = frequencyMap.entrySet().stream()
                .max(comparingByValue());

        return mostFrequent.map(Map.Entry::getKey).orElse(0);

    }

    // 플레이어가 가장 많이 승리한 승리/패패 횟수
    @Override
    public int getMaxLimitPlayerTimesByWinnerPlayer() {
        Map<Integer, Long> frequencyMap = GAME_RECORDS.values()
                .stream()
                .filter(Game::isPlayerWin)
                .map(Game::getLimitPlayerTimes)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> mostFrequent = frequencyMap.entrySet().stream()
                .max(comparingByValue());

        return mostFrequent.map(Map.Entry::getKey).orElse(0);
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
    public void clear() {
        GAME_RECORDS.clear();
    }


}
