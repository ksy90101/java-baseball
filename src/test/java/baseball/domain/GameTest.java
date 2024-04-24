package baseball.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {
    private static final Count ZERO_COUNT = Count.of(0);
    private static final Count THREE_COUNT = Count.of(3);

    private Game generalGame;

    @BeforeEach
    void setUp() {
        generalGame = new Game(new Computer(List.of(new Number(1), new Number(2), new Number(3))));
    }

    @DisplayName("player가 성공하지 못하면 게임이 종료되지 않는다.")
    @Test
    void AddPlayerRecordTest() {
        final PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(4))), Count.of(2), Count.of(1));
        assertThat(generalGame.getEndAt()).isNull();

        generalGame.addPlayerRecord(playerRecord);

        assertThat(generalGame.getEndAt()).isNull();
    }

    @DisplayName("player가 성공하면 게임이 종료된다.")
    @Test
    void endAfterAddPlayerRecordTest() {
        final PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))), THREE_COUNT, ZERO_COUNT);
        assertThat(generalGame.getEndAt()).isNull();

        generalGame.addPlayerRecord(playerRecord);

        assertThat(generalGame.getEndAt()).isNotNull();
    }

    @DisplayName("게임이 이미 종료되었다면 playerRecord를 추가할 수 없다.")
    @Test
    void addPlayerRecordAfterEndExcpetionTest() {
        final PlayerRecord playerRecord = new PlayerRecord(new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3))), THREE_COUNT, ZERO_COUNT);

        generalGame.addPlayerRecord(playerRecord);

        assertThat(generalGame.getEndAt()).isNotNull();

        assertThatThrownBy(() -> generalGame.addPlayerRecord(playerRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임이 종료되었습니다.");
    }
}
