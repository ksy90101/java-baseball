package baseball.dto;

import baseball.domain.Participant;

public record CheckBallResponse(
        int strikeCount,
        int ballCount,
        boolean isNotting,
        boolean isSuccess,

        boolean isFinished,
        Participant winner
) {
}
