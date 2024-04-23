package baseball.controller;

import baseball.domain.Game;
import baseball.domain.Number;
import baseball.dto.CheckBallResponse;
import baseball.dto.CheckBallsRequest;
import baseball.dto.GameStartRequest;
import baseball.dto.StatisticsResponse;
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
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int computerId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

        assertThat(gameRepository.findById(computerId)).isPresent();
    }

    @DisplayName("1스트라이크 1볼을 확인한다.")
    @Test
    void checkBallTest() {
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

        CheckBallResponse checkBallDto = baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 3, 5), gameId));

        assertThat(checkBallDto.strikeCount()).isEqualTo(1);
        assertThat(checkBallDto.ballCount()).isEqualTo(1);
        assertThat(checkBallDto.isNotting()).isFalse();
        assertThat(checkBallDto.isSuccess()).isFalse();
    }

    @DisplayName("낫싱을 확인한다.")
    @Test
    void checkBallTest2() {
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

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
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

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
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

        assertThat(baseBallGameController.getGames()).hasSize(1);
        assertThat(baseBallGameController.getGames().get(0).id()).isEqualTo(gameId);
    }

    @DisplayName("게임을 조회한다.")
    @Test
    void getGameTest() {
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int gameId = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

        baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 2, 3), gameId));

        assertThat(baseBallGameController.getGame(gameId).playerRecordResponses()).isNotEmpty();
    }

    @DisplayName("통계를 조회한다.")
    @Test
    void getStatisticsTest() {
        GameStartRequest gameStartRequest = new GameStartRequest(10);
        int gameId1 = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

        baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 2, 3), gameId1));

        int gameId2 = baseBallGameController.gameStart(() -> List.of(
                new Number(1), new Number(2), new Number(3)), gameStartRequest);

        baseBallGameController.checkBalls(new CheckBallsRequest(List.of(2, 3, 4), gameId2));
        baseBallGameController.checkBalls(new CheckBallsRequest(List.of(1, 2, 3), gameId2));

        StatisticsResponse statistics = baseBallGameController.getStatistics();

        assertAll(
                () -> assertThat(statistics.minPlayerTimes()).isEqualTo(1),
                () -> assertThat(statistics.gameIdsOfMinPlayerTimes()).isEqualTo(List.of(gameId1)),
                () -> assertThat(statistics.maxPlayerTimes()).isEqualTo(2),
                () -> assertThat(statistics.gameIdsOfMaxPlayerTimes()).isEqualTo(List.of(gameId2)),
                () -> assertThat(statistics.averagePlayerTimes()).isEqualTo(1.5)
        );
    }
}
