package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {
    @DisplayName("플레이어가 횟수 내로 성공하면 게임이 종료되며 승자는 플레이어가 된다.")
    @Test
    void addPlayerRecordByPlayerWinner() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 10);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        assertThat(game.getEndAt()).isNull();
        assertThat(game.getWinner()).isNull();

        game.addPlayerRecord(playerRecord);

        assertThat(game.getEndAt()).isNotNull();
        assertThat(game.getWinner()).isEqualTo(Participant.PLAYER);
    }

    @DisplayName("플레이어 시도 횟수 limit에 걸리면 컴퓨터가 승리한다.")
    @Test
    void addPlayerRecordByLimitTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(4))),
                2,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.getEndAt()).isNotNull();
        assertThat(game.getWinner()).isEqualTo(Participant.COMPUTER);
    }

    @DisplayName("이미 종료된 게임에 대해선 Record를 추가할 수 없다.")
    @Test
    void addPlayerRecordExceptionByFinishedTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 10);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThatThrownBy(() -> game.addPlayerRecord(playerRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 종료된 게임입니다.");
    }

    @DisplayName("플레이어의 시도 횟수와 정해진 횟수에서 플레이어 시도 횟수를 초과하면 true를 반한한다.")
    @Test
    void isLimitByMoreThanLimitPlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(4))),
                2,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.isLimit()).isTrue();
    }

    @DisplayName("플레이어의 시도 횟수와 정해진 횟수에서 플레이어 시도 횟수를 넘지 않으면 false 반한한다.")
    @Test
    void isLimitByLessThanLimitPlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);

        assertThat(game.isLimit()).isFalse();
    }

    @DisplayName("컴퓨터가 승리하면 true를 반환한다.")
    @Test
    void isComputerWinOfWinComputer() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(4))),
                2,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.isComputerWin()).isTrue();
    }

    @DisplayName("플레이어가 승리하면 false를 반환한다.")
    @Test
    void isComputerWinOfWinPlayer() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.isComputerWin()).isFalse();
    }

    @DisplayName("컴퓨터가 승리하면 false를 반환한다.")
    @Test
    void isPlayerWinOfWinComputer() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(4))),
                2,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.isPlayerWin()).isFalse();
    }

    @DisplayName("플레이어가 승리하면 true를 반환한다.")
    @Test
    void isPlayerWinOfWinPlayer() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.isPlayerWin()).isTrue();
    }

    @DisplayName("같은 PlayerTimes를 가지고 있으면 true를 반환한다.")
    @Test
    void samePlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.samePlayerTimes(1)).isTrue();
    }

    @DisplayName("다른 PlayerTimes를 가지고 있으면 false를 반환한다.")
    @Test
    void notSamePlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.samePlayerTimes(2)).isFalse();
    }

    @DisplayName("게임이 종료되지 않았다면 false를 반환한다.")
    @Test
    void isFinishedByNotFinishedTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);

        assertThat(game.isFinished()).isFalse();
    }

    @DisplayName("게임이 종료되었다면 true를 반환한다.")
    @Test
    void isFinishedByFinishedTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        PlayerRecord playerRecord = new PlayerRecord(
                new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))),
                3,
                0
        );

        game.addPlayerRecord(playerRecord);

        assertThat(game.isFinished()).isTrue();
    }

    @DisplayName("LimitPlayerTimes의 수가 같으면 true이다.")
    @Test
    void trueOfSameLimitPlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 1);
        assertThat(game.sameLimitPlayerTimes(1)).isTrue();
    }

    @DisplayName("LimitPlayerTimes의 수가 다르면 false이다.")
    @Test
    void falseOfSameLimitPlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))), 2);
        assertThat(game.sameLimitPlayerTimes(1)).isFalse();
    }
}
