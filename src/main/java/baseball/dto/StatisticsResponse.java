package baseball.dto;

import java.util.List;

public record StatisticsResponse(
        int maxPlayerTimes,
        List<Integer> gameIdsOfMaxPlayerTimes,
        int minPlayerTimes,
        List<Integer> gameIdsOfMinPlayerTimes,
        double averagePlayerTimes,
        int maxCountLimitPlayerTimes,
        List<Integer> gameIdsOfMaxCountLimitPlayerTimes,
        int minCountLimitPlayerTimes,
        List<Integer> gameIdsOfMinCountLimitPlayerTimes,
        int maxLimitPlayerTimes,
        List<Integer> gameIdsOfMaxLimitPlayerTimes,
        int minLimitPlayerTimes,
        List<Integer> gameIdsOfMinLimitPlayerTimes,
        int maxLimitPlayerTimesByWinnerComputer,
        List<Integer> gameIdsOfMaxLimitPlayerTimesByWinnerComputer,
        int maxLimitPlayerTimesByWinnerPlayer,
        List<Integer> gameIdsOfMaxLimitPlayerTimesByWinnerPlayer,
        double averageLimitPlayerTimes,
        int countByWinnerComputer,
        int countByWinnerPlayer,
        int playerWinningPercentage
) {
}
