package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {
    @DisplayName("player가 성공하면 게임이 종료된다.")
    @Test
    void endAfterAddPlayerRecordTest() {
        final Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))));
        final PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))), 3, 0);
        assertThat(game.getEndAt()).isNull();

        game.addPlayerRecord(playerRecord);

        assertThat(game.getEndAt()).isNotNull();
    }

    @DisplayName("게임이 이미 종료되었다면 playerRecord를 추가할 수 없다.")
    @Test
    void addPlayerRecordAfterEndExcpetionTest() {
        final Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))));
        final PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))), 3, 0);

        game.addPlayerRecord(playerRecord);

        assertThat(game.getEndAt()).isNotNull();

        assertThatThrownBy(() -> game.addPlayerRecord(playerRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임이 종료되었습니다.");
    }
}
