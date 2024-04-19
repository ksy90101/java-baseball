package baseball.dto;

import java.util.List;

public record PlayerRecordResponse(
        List<Integer> numbers,
        int strikeCount,
        int ballCount,
        boolean isNotting,
        boolean isSuccess
) {
}
