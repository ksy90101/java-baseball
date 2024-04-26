package baseball.view;

import baseball.dto.CheckBallResponse;
import baseball.dto.GameRecordResponse;
import baseball.dto.GameRecordsResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final String DELIMITER = "";

    private OutputView() {
    }

    public static void printExitMessage() {
        System.out.println("애플리케이션이 종료되었습니다.");
    }

    public static void printResult(final CheckBallResponse checkBallResponse) {
        if (checkBallResponse.isNotting()) {
            System.out.println("낫싱");
            return;
        }

        printStrikeAndBallCount(checkBallResponse.strikeCount(), checkBallResponse.ballCount());
        printGameSuccessMessage(checkBallResponse.isSuccess());
    }

    public static void printPickComputerNumbers() {
        System.out.println("컴퓨터가 숫자를 뽑았습니다.");
    }

    public static void printErrorMessage(final String message) {
        System.out.println(message);
    }

    public static void printGameRecords(final List<GameRecordsResponse> gameRecords) {
        System.out.println("게임 기록");
        if (gameRecords.isEmpty()) {
            System.out.println("기록이 없습니다.");
            System.out.println("-------기록 종료-------");
            return;
        }
        for (final GameRecordsResponse gameRecord : gameRecords) {
            final String stringBuilder = "[" + gameRecord.id() + "] " +
                    "시작시간: " +
                    gameRecord.startAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) +
                    " / " +
                    "종료시간:" + gameRecord.endAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) +
                    " / " +
                    "횟수: " + gameRecord.playerTimes();

            System.out.println(stringBuilder);
        }
    }

    public static void printGameRecord(final GameRecordResponse gameRecord) {
        System.out.println(gameRecord.id() + "번 게임 결과");

        gameRecord.playerRecordResponses().forEach(playerRecord -> {
            final String playerNumber = playerRecord.numbers()
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(DELIMITER));
            System.out.println("숫자를 입력해주세요: " + playerNumber);
            if (playerRecord.isNotting()) {
                System.out.println("낫싱");
                return;
            }

            printStrikeAndBallCount(playerRecord.strikeCount(), playerRecord.ballCount());
            printGameSuccessMessage(playerRecord.isSuccess());
        });

        System.out.println("-------기록 종료-------");
    }

    private static void printStrikeAndBallCount(final int strikeCount, final int ballCount) {
        System.out.println(strikeCount + "스트라이크 " + ballCount + "볼");
    }

    private static void printGameSuccessMessage(final boolean isSuccess) {
        if (isSuccess) {
            System.out.println("3개의 숫자를 모두 맞히셨습니다.");
            System.out.println("-------게임 종료-------");
        }
    }
}
