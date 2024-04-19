package baseball.dto;

import java.util.List;

public record GameRecordResponse(
        List<PlayerRecordResponse> playerRecordResponses
) {
}
