package baseball.controller;

import baseball.domain.Game;
import baseball.domain.Number;
import baseball.dto.CheckBallResponse;
import baseball.dto.CheckBallsRequest;
import baseball.repository.GameRepository;
import baseball.repository.GameRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BaseBallGameControllerTest {

    private static final GameRepository gameRepository = new GameRepositoryImpl();
    private static final BaseBallGameController baseBallGameController = new BaseBallGameController(gameRepository);

    @AfterEach
    void tearDown() {
        gameRepository.clear();
    }

    @DisplayName("컴퓨터를 생성한다.")
    @Test
    void computerStartTest() {
        int computerId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)));

        assertThat(gameRepository.findById(computerId)).isPresent();
    }

    @DisplayName("1스트라이크 1볼을 확인한다.")
    @Test
    void checkBallTest() {
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)));

        CheckBallResponse checkBallDto = baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 3, 5), gameId));

        assertThat(checkBallDto.strikeCount()).isEqualTo(1);
        assertThat(checkBallDto.ballCount()).isEqualTo(1);
        assertThat(checkBallDto.isNotting()).isFalse();
        assertThat(checkBallDto.isSuccess()).isFalse();
    }

    @DisplayName("낫싱을 확인한다.")
    @Test
    void checkBallTest2() {
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)));

        CheckBallResponse checkBallDto = baseBallGameController.checkBalls(new CheckBallsRequest(List.of(4, 5, 6), gameId));

        assertAll(() -> assertThat(checkBallDto.strikeCount()).isZero(),
                () -> assertThat(checkBallDto.ballCount()).isZero(),
                () -> assertThat(checkBallDto.isNotting()).isTrue(),
                () -> assertThat(checkBallDto.isSuccess()).isFalse()
        );
    }

    @DisplayName("3스트라이크를 확인한다.")
    @Test
    void checkBallTest3() {
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)));

        CheckBallResponse checkBallDto = baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 2, 3), gameId));
        Game game = gameRepository.findById(gameId).get();

        assertAll(() -> assertThat(checkBallDto.strikeCount()).isEqualTo(3),
                () -> assertThat(checkBallDto.ballCount()).isZero(),
                () -> assertThat(checkBallDto.isNotting()).isFalse(),
                () -> assertThat(checkBallDto.isSuccess()).isTrue(),
                () -> assertThat(game.getEndAt()).isNotNull()
        );
    }

    @DisplayName("게임을 조회한다.")
    @Test
    void getGamesTest() {
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)));

        assertThat(baseBallGameController.getGames()).hasSize(1);
        assertThat(baseBallGameController.getGames().get(0).id()).isEqualTo(gameId);
    }

    @DisplayName("게임을 조회한다.")
    @Test
    void getGameTest() {
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)));

        baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 2, 3), gameId));

        assertThat(baseBallGameController.getGame(gameId).playerRecordResponses()).isNotEmpty();
    }
}
