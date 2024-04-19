package baseball.dto;

import java.util.List;

public record StatisticsResponse(
        int minPlayerTimes,
        List<Integer> gameIdsOfMinPlayerTimes,

        int maxPlayerTimes,
        List<Integer> gameIdsOfMaxPlayerTimes,

        double averagePlayerTimes
) {
}
