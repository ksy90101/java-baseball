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
    public void clear() {
        GAME_RECORDS.clear();
    }


}
