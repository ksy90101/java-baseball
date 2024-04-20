package baseball.dto;

import java.util.List;

public record GameRecordResponse(
        int id,
        List<PlayerRecordResponse> playerRecordResponses
) {
}
