package baseball.view;

import baseball.dto.CheckBallResponse;
import baseball.dto.GameRecordResponse;
import baseball.dto.GameRecordsResponse;
import baseball.dto.StatisticsResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final String DIVIDER = "------------";

    private OutputView() {
    }

    public static void printExitMessage() {
        System.out.println("애플리케이션이 종료되었습니다.");
    }

    public static void printResult(CheckBallResponse checkBallDto) {
        if (checkBallDto.isNotting()) {
            System.out.println("낫싱");
            return;
        }

        System.out.println(checkBallDto.ballCount() + "볼 " + checkBallDto.strikeCount() + "스트라이크");

        if (checkBallDto.isFinished()) {
            if (checkBallDto.isSuccess()) {
                System.out.println("3개의 숫자를 모두 맞히셨습니다.");
            }
            System.out.println("승리자: " + checkBallDto.winner().getValue());
            System.out.println("-------게임 종료-------");
        }
    }

    public static void printPickComputerNumbers() {
        System.out.println("컴퓨터가 숫자를 뽑았습니다.");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public static void printGameRecords(List<GameRecordsResponse> gameRecords) {
        System.out.println("게임 기록");
        if (gameRecords.isEmpty()) {
            System.out.println("기록이 없습니다.");
            System.out.println("-------기록 종료-------");
            return;
        }
        for (GameRecordsResponse gameRecord : gameRecords) {
            String stringBuilder = "[" + gameRecord.id() + "] " +
                    "시작시간: " +
                    gameRecord.startAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) +
                    " / " +
                    "종료시간:" + gameRecord.endAt().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)) +
                    " / " +
                    "횟수: " + gameRecord.playerTimes();

            System.out.println(stringBuilder);
        }
    }

    public static void printGameRecord(GameRecordResponse gameRecord) {
        System.out.println("1번 게임 결과");

        gameRecord.playerRecordResponses().forEach(playerRecord -> {
            String playerNumber = playerRecord.numbers().stream().map(String::valueOf).collect(Collectors.joining(""));
            System.out.println("숫자를 입력해주세요: " + playerNumber);
            if (playerRecord.isNotting()) {
                System.out.println("낫싱");
                return;
            }

            System.out.println(playerRecord.ballCount() + "볼 " + playerRecord.strikeCount() + "스트라이크");
            if (playerRecord.isSuccess()) {
                System.out.println("3개의 숫자를 모두 맞히셨습니다.");
            }
        });

        System.out.println("-------기록 종료-------");
    }

    public static void printStatistics(StatisticsResponse statistics) {
        System.out.println("게임 통계");
        System.out.println("가장 적은 횟수: " + statistics.minPlayerTimes() + "회 - " + statistics.gameIdsOfMinPlayerTimes());
        System.out.println("가장 많은 횟수: " + statistics.maxPlayerTimes() + "회 - " + statistics.gameIdsOfMaxPlayerTimes());
        System.out.println("평균횟수: " + statistics.averagePlayerTimes());

        System.out.println(DIVIDER);

        System.out.println("가장 많이 적용된 승리/패패 횟수: " + statistics.maxCountLimitPlayerTimes() + "회 - " + statistics.gameIdsOfMaxCountLimitPlayerTimes());
        System.out.println("가장 적게 적용된 승리/패패 횟수: " + statistics.minCountLimitPlayerTimes() + "회 - " + statistics.gameIdsOfMinCountLimitPlayerTimes());
        System.out.println("평균 승리/패패 횟수: " + statistics.averageLimitPlayerTimes());

        System.out.println(DIVIDER);

        System.out.println("가장 큰 값으로 적용된 승리/패패 횟수: " + statistics.maxLimitPlayerTimes() + "회 - " + statistics.gameIdsOfMaxLimitPlayerTimes());
        System.out.println("가장 낮은 값으로 적용된 승리/패패 횟수: " + statistics.minLimitPlayerTimes() + "회 - " + statistics.gameIdsOfMinLimitPlayerTimes());

        System.out.println(DIVIDER);

        System.out.println("컴퓨터가 가장 많이 승리한 승리/패패 횟수: " + statistics.maxLimitPlayerTimesByWinnerComputer() + "회 - " + statistics.gameIdsOfMaxLimitPlayerTimesByWinnerComputer());
        System.out.println("플레이어가 가장 많이 승리한 승리/패패 횟수: " + statistics.maxLimitPlayerTimesByWinnerPlayer() + "회 - " + statistics.gameIdsOfMaxLimitPlayerTimesByWinnerPlayer());
    }
}
