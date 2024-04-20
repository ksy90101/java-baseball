package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

}
