package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class GameTest {
    @DisplayName("게임이 종료되었는지 확인한다.")
    @Test
    void endTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))));
        PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))), 3, 0);
        game.addPlayerRecord(playerRecord);
        assertThat(game.getEndAt()).isNull();

        game.end();

        assertThat(game.getEndAt()).isNotNull();
    }

    @DisplayName("사용자가 한번도 시도를 하지 않았다면 종료할 수 없다.")
    @Test
    void endByNotPlayerTimesTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))));

        assertThatThrownBy(game::end)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자가 한번도 실행하지 않았습니다.");
    }

    @DisplayName("사용자가 성공하지 못하면 종료할 수 없다.")
    @Test
    void endByNotSuccessTest() {
        Game game = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))));
        PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(4))), 2, 0);
        game.addPlayerRecord(playerRecord);

        assertThatThrownBy(game::end)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자가 성공하지 못했습니다.");
    }
}
