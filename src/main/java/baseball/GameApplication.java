package baseball;

import baseball.controller.BaseBallGameController;
import baseball.domain.Commend;
import baseball.dto.CheckBallResponse;
import baseball.dto.CheckBallsRequest;
import baseball.dto.GameRecordResponse;
import baseball.dto.GameRecordsResponse;
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
                if (commend == Commend.START) {
                    final int gameId = baseBallGameController.gameStart(baseBallNumberGenerator);
                    OutputView.printPickComputerNumbers();
                    gameInProgress(gameId);
                } else if (commend == Commend.GAME_RECORD) {
                    final List<GameRecordsResponse> gameRecords = baseBallGameController.getGames();
                    OutputView.printGameRecords(gameRecords);
                    final int gameId = InputView.inputGameRecordId();

                    final GameRecordResponse game = baseBallGameController.getGame(gameId);

                    OutputView.printGameRecord(game);
                }
            }
            while (commend != Commend.END);
            applicationEnd();
        } catch (final Exception e) {
            OutputView.printErrorMessage(e.getMessage());
            run();
        }
    }

    private static void applicationEnd() {
        OutputView.printExitMessage();
    }

    private static void gameInProgress(final int gameId) {
        try {
            boolean isFinished = true;

            while (isFinished) {
                final List<Integer> userNumbers = InputView.inputNumbers();
                final CheckBallsRequest checkBallsRequest = new CheckBallsRequest(userNumbers, gameId);
                final CheckBallResponse checkBallResponse = baseBallGameController.checkBalls(checkBallsRequest);
                OutputView.printResult(checkBallResponse);
                isFinished = !checkBallResponse.isSuccess();
            }
        } catch (final Exception e) {
            OutputView.printErrorMessage(e.getMessage());
            gameInProgress(gameId);
        }
    }
}
