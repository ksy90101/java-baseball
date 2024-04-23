package baseball.repository;

import baseball.domain.Game;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface GameRepository {
    int insert(Game game);

    Optional<Game> findById(int gameId);

    List<Game> findAll();

    List<Game> findAllByPlayerTimes(int playerTimes);

    List<Integer> findIdsByLimitPlayerTimes(int limitPlayerTimes, final Predicate<Game> winnerPredicate);

    int getMinPlayerTimes();

    int getMaxPlayerTimes();

    double getAveragePlayerTimes();

    int getMaxCountLimitPlayerTimes();

    int getMinCountLimitPlayerTimes();

    int getMaxLimitPlayerTimes();

    int getMinLimitPlayerTimes();

    double getAverageLimitPlayerTimes();

    int getMaxLimitPlayerTimesByWinnerComputer();

    int getMaxLimitPlayerTimesByWinnerPlayer();

    int getCountByWinnerComputer();

    int getCountByWinnerPlayer();

    void clear();

}
