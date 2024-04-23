package baseball.repository;

import baseball.domain.Number;
import baseball.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GameRepositoryImplTest {
    private static final GameRepository gameRepository = new GameRepositoryImpl();

    @AfterEach
    void tearDown() {
        gameRepository.clear();
    }

    @DisplayName("해당 게임을 조회할 수 있다.")
    @Test
    void findByIdTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);

        final int gameId = gameRepository.insert(game);

        final Game findGame = gameRepository.findById(gameId).get();

        assertThat(findGame).isEqualTo(game);
    }

    @DisplayName("컴퓨터를 저장할수 있다.")
    @Test
    void insertTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);
        assertDoesNotThrow(() -> gameRepository.insert(game));
    }

    @DisplayName("모든 게임을 조회할 수 있다.")
    @Test
    void findAllTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);
        gameRepository.insert(game);

        assertThat(gameRepository.findAll()).hasSize(1);
        assertThat(gameRepository.findAll()).contains(game);
    }

    @DisplayName("플레이어 횟수에 따라 게임을 조회할 수 있다.")
    @Test
    void findAllByPlayerTimes() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);
        final BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        final Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game2 = new Game(computer2, 10);
        final BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        final PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        final BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.findAllByPlayerTimes(1)).hasSize(1);
        assertThat(gameRepository.findAllByPlayerTimes(1)).contains(game);
        assertThat(gameRepository.findAllByPlayerTimes(2)).hasSize(1);
        assertThat(gameRepository.findAllByPlayerTimes(2)).contains(game2);
    }

    @Test
    void getMinPlayerTimes() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);
        final BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        final Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game2 = new Game(computer2, 10);
        final BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        final PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        final BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.getMinPlayerTimes()).isEqualTo(1);
    }

    @Test
    void getMaxPlayerTimes() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);
        final BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        final Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game2 = new Game(computer2, 10);
        final BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        final PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        final BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.getMaxPlayerTimes()).isEqualTo(2);
    }

    @DisplayName("LimitedPlayerTimes를 가지고 조회할 수 있다.")
    @Test
    void findAllByLimitPlayerTimesTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game1 = new Game(computer, 10);
        final Game game2 = new Game(computer, 5);
        gameRepository.insert(game1);
        gameRepository.insert(game2);

        final List<Integer> game_ids = gameRepository.findIdsByLimitPlayerTimes(10, game -> true);

        assertAll(
                () -> assertThat(game_ids).hasSize(1),
                () -> assertThat(game_ids).contains(game1.getId()),
                () -> assertThat(game_ids).doesNotContain(game2.getId())
        );
    }

    @Test
    void getAveragePlayerTimes() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game = new Game(computer, 10);
        final BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        final Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game2 = new Game(computer2, 10);
        final BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        final PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        final BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        final PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.getAveragePlayerTimes()).isEqualTo(1.5);
    }

    @DisplayName("가장 많이 적용된 승리/패패 횟수")
    @Test
    void getMaxCountLimitPlayerTimesTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game1 = new Game(computer, 10);
        final Game game2 = new Game(computer, 10);
        final Game game3 = new Game(computer, 5);
        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);

        assertThat(gameRepository.getMaxCountLimitPlayerTimes()).isEqualTo(10);
    }

    @DisplayName("가장 적게 적용된 승리/패패 횟수")
    @Test
    void getMinCountLimitPlayerTimesTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game1 = new Game(computer, 10);
        final Game game2 = new Game(computer, 10);
        final Game game3 = new Game(computer, 5);
        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);

        assertThat(gameRepository.getMinCountLimitPlayerTimes()).isEqualTo(5);
    }

    @DisplayName("승리/패패 횟수에 대한 가장 큰값")
    @Test
    void getMaxLimitPlayerTimesTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game1 = new Game(computer, 20);
        final Game game2 = new Game(computer, 10);
        final Game game3 = new Game(computer, 5);
        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);

        assertThat(gameRepository.getMaxLimitPlayerTimes()).isEqualTo(20);
    }

    @DisplayName("승리/패패 횟수에 대한 가장 낮은값")
    @Test
    void getMinLimitPlayerTimesTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game1 = new Game(computer, 20);
        final Game game2 = new Game(computer, 10);
        final Game game3 = new Game(computer, 5);
        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);

        assertThat(gameRepository.getMinLimitPlayerTimes()).isEqualTo(5);
    }

    @DisplayName("컴퓨터가 가장 많이 승리한 승리/패패 횟수")
    @Test
    void getMaxLimitPlayerTimesByWinnerComputerTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final PlayerRecord successPlayerRecord = new PlayerRecord(new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3))), 3, 0);
        final PlayerRecord failPlayerRecord = new PlayerRecord(new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6))), 0, 0);

        final Game game1 = new Game(computer, 1);
        game1.addPlayerRecord(failPlayerRecord);
        final Game game2 = new Game(computer, 1);
        game2.addPlayerRecord(failPlayerRecord);
        final Game game3 = new Game(computer, 2);
        game3.addPlayerRecord(failPlayerRecord);
        game3.addPlayerRecord(failPlayerRecord);
        final Game game4 = new Game(computer, 1);
        game4.addPlayerRecord(successPlayerRecord);
        final Game game5 = new Game(computer, 1);
        game5.addPlayerRecord(successPlayerRecord);

        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);
        gameRepository.insert(game4);
        gameRepository.insert(game5);

        assertThat(gameRepository.getMaxLimitPlayerTimesByWinnerComputer()).isEqualTo(1);
    }

    @DisplayName("플레이어가 가장 많이 승리한 승리/패패 횟수")
    @Test
    void getMaxLimitPlayerTimesByWinnerPlayerTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final PlayerRecord successPlayerRecord = new PlayerRecord(new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3))), 3, 0);
        final PlayerRecord failPlayerRecord = new PlayerRecord(new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6))), 0, 0);

        final Game game1 = new Game(computer, 1);
        game1.addPlayerRecord(failPlayerRecord);
        final Game game2 = new Game(computer, 1);
        game2.addPlayerRecord(failPlayerRecord);
        final Game game3 = new Game(computer, 2);
        game3.addPlayerRecord(failPlayerRecord);
        game3.addPlayerRecord(failPlayerRecord);
        final Game game4 = new Game(computer, 1);
        game4.addPlayerRecord(successPlayerRecord);
        final Game game5 = new Game(computer, 1);
        game5.addPlayerRecord(successPlayerRecord);

        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);
        gameRepository.insert(game4);
        gameRepository.insert(game5);

        assertThat(gameRepository.getMaxLimitPlayerTimesByWinnerPlayer()).isEqualTo(1);
    }

    @DisplayName("승리/패패 시도 횟수 평균 값")
    @Test
    void getAverageLimitPlayerTimesTest() {
        final Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        final Game game1 = new Game(computer, 20);
        final Game game2 = new Game(computer, 10);
        final Game game3 = new Game(computer, 5);
        gameRepository.insert(game1);
        gameRepository.insert(game2);
        gameRepository.insert(game3);

        assertThat(gameRepository.getAverageLimitPlayerTimes()).isEqualTo(11.666666666666666);
    }
}
