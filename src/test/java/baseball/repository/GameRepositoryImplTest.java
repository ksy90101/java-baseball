package baseball.repository;

import baseball.domain.Number;
import baseball.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);

        int gameId = gameRepository.insert(game);

        Game findGame = gameRepository.findById(gameId).get();

        assertThat(findGame).isEqualTo(game);
    }

    @DisplayName("컴퓨터를 저장할수 있다.")
    @Test
    void insertTest() {
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);
        assertDoesNotThrow(() -> gameRepository.insert(game));
    }

    @DisplayName("모든 게임을 조회할 수 있다.")
    @Test
    void findAllTest() {
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);
        gameRepository.insert(game);

        assertThat(gameRepository.findAll()).hasSize(1);
        assertThat(gameRepository.findAll()).contains(game);
    }

    @DisplayName("플레이어 횟수에 따라 게임을 조회할 수 있다.")
    @Test
    void findAllByPlayerTimes() {
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);
        BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game2 = new Game(computer2);
        BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
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
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);
        BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game2 = new Game(computer2);
        BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.getMinPlayerTimes()).isEqualTo(1);
    }

    @Test
    void getMaxPlayerTimes() {
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);
        BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game2 = new Game(computer2);
        BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.getMaxPlayerTimes()).isEqualTo(2);
    }

    @Test
    void getAveragePlayerTimes() {
        Computer computer = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game = new Game(computer);
        BaseBallNumbers playerNumbers1 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord = new PlayerRecord(playerNumbers1, 3, 0);
        game.addPlayerRecord(playerRecord);

        gameRepository.insert(game);

        Computer computer2 = new Computer(Arrays.asList(new Number(1), new Number(2), new Number(3)));
        Game game2 = new Game(computer2);
        BaseBallNumbers playerNumbers2 = new BaseBallNumbers(List.of(
                new Number(4),
                new Number(5),
                new Number(6)));
        PlayerRecord playerRecord2 = new PlayerRecord(playerNumbers2, 0, 0);
        BaseBallNumbers playerNumbers3 = new BaseBallNumbers(List.of(
                new Number(1),
                new Number(2),
                new Number(3)));
        PlayerRecord playerRecord3 = new PlayerRecord(playerNumbers3, 0, 0);
        game2.addPlayerRecord(playerRecord2);
        game2.addPlayerRecord(playerRecord3);

        gameRepository.insert(game2);

        assertThat(gameRepository.getAveragePlayerTimes()).isEqualTo(1.5);
    }

}
