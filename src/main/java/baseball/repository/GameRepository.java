package baseball.repository;

import baseball.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    int insert(Game game);

    Optional<Game> findById(int gameId);

    List<Game> findAll();

    List<Game> findAllByPlayerTimes(int playerTimes);

    int getMinPlayerTimes();

    int getMaxPlayerTimes();

    double getAveragePlayerTimes();

    void clear();

}
