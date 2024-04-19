package baseball.dto;

import java.time.LocalDateTime;

public record GameRecordsResponse(
        int id,
        LocalDateTime startAt,
        LocalDateTime endAt,
        int playerTimes
) {
}
