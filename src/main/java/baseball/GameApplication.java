package baseball;

import baseball.controller.BaseBallGameController;
import baseball.domain.Commend;
import baseball.dto.*;
import baseball.generator.BaseBallNumberGenerator;
import baseball.generator.BaseBallNumberShuffleGenerator;
import baseball.repository.GameRepositoryImpl;
import baseball.view.InputView;
import baseball.view.OutputView;

import java.util.List;

public class GameApplication {
    private static final BaseBallNumberGenerator baseBallNumberGenerator = new BaseBallNumberShuffleGenerator();
    private static final BaseBallGameController baseBallGameController = new BaseBallGameController(new GameRepositoryImpl());

    public static void run() {
        Commend commend = Commend.END;
        try {
            do {
                commend = Commend.of(InputView.inputMenu());
                if (commend == Commend.GAME_RECORD) {
                    List<GameRecordsResponse> gameRecords = baseBallGameController.getGames();
                    OutputView.printGameRecords(gameRecords);
                    if (gameRecords.isEmpty()) {
                        continue;
                    }
                    int gameId = InputView.inputGameRecordId();

                    GameRecordResponse game = baseBallGameController.getGame(gameId);

                    OutputView.printGameRecord(game);
                } else if (commend == Commend.START) {
                    int gameId = baseBallGameController.gameStart(baseBallNumberGenerator);
                    OutputView.printPickComputerNumbers();
                    gameInProgress(gameId);
                } else if (commend == Commend.STATISTICS) {
                    StatisticsResponse statistics = baseBallGameController.getStatistics();

                    OutputView.printStatistics(statistics);
                }
            }
            while (commend != Commend.END);
            applicationEnd();
        } catch (Exception e) {
            OutputView.printErrorMessage(e.getMessage());
            run();
        }
    }

    private static void applicationEnd() {
        OutputView.printExitMessage();
    }

    private static void gameInProgress(int gameId) {
        try {
            boolean isFinished = true;

            while (isFinished) {
                List<Integer> userNumbers = InputView.inputNumbers();
                CheckBallsRequest checkBallsRequest = new CheckBallsRequest(userNumbers, gameId);
                CheckBallResponse checkBallDto = baseBallGameController.checkBalls(checkBallsRequest);
                OutputView.printResult(checkBallDto);
                isFinished = !checkBallDto.isSuccess();
            }
        } catch (Exception e) {
            OutputView.printErrorMessage(e.getMessage());
            gameInProgress(gameId);
        }
    }
}
