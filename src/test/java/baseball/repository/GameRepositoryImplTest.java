package baseball.repository;

import baseball.domain.Computer;
import baseball.domain.Game;
import baseball.domain.Number;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
}
