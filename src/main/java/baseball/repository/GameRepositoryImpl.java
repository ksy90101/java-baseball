package baseball.repository;

import baseball.domain.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public void clear() {
        GAME_RECORDS.clear();
    }


}
