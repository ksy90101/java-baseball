package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComputerTest {
    private static final Count ZERO_COUNT = Count.of(0);
    private static final Count THREE_COUNT = Count.of(3);

    @DisplayName("숫자가 다르고, strike 갯수를 제외한 갯수가 ball의 반환 값으로 내려준다.")
    @Test
    void getBallCountTest() {
        final List<Number> numbers = List.of(new Number(1),
                new Number(2),
                new Number(3));
        final Computer computer = new Computer(numbers);
        final BaseBallNumbers playNumbers = new BaseBallNumbers(List.of(new Number(2), new Number(3), new Number(1)));

        assertThat(computer.getBallCount(playNumbers, ZERO_COUNT)).isEqualTo(THREE_COUNT);
    }

    @DisplayName("숫자가 존재하지 않는다면 0을 반환한다.")
    @Test
    void getBallCountZeroTest() {
        final List<Number> numbers = List.of(new Number(1),
                new Number(2),
                new Number(3));
        final Computer computer = new Computer(numbers);
        final BaseBallNumbers playNumbers = new BaseBallNumbers(List.of(new Number(4), new Number(5), new Number(6)));
        assertThat(computer.getBallCount(playNumbers, ZERO_COUNT)).isEqualTo(ZERO_COUNT);
    }

    @DisplayName("해당 숫자가 해당 위치에 있으면 스트라이크 갯수로 반환한다.")
    @Test
    void getStrikeTest() {
        final List<Number> numbers = List.of(new Number(1),
                new Number(2),
                new Number(3));
        final Computer computer = new Computer(numbers);
        final BaseBallNumbers playNumbers = new BaseBallNumbers(List.of(new Number(1), new Number(2), new Number(3)));
        assertThat(computer.getStrikeCount(playNumbers)).isEqualTo(THREE_COUNT);
    }

    @DisplayName("해당 숫자가 해당 위치에 존재하지 않으면 스트라이크 갯수로 반환하지 않는다.")
    @Test
    void getStrikeZeroTest() {
        final List<Number> numbers = List.of(new Number(1),
                new Number(2),
                new Number(3));
        final Computer computer = new Computer(numbers);
        final BaseBallNumbers playNumbers = new BaseBallNumbers(List.of(new Number(2), new Number(3), new Number(1)));
        assertThat(computer.getStrikeCount(playNumbers)).isEqualTo(ZERO_COUNT);
    }

    @DisplayName("해당 숫자가 존재하지 않으면 스트라이크 갯수로 반환하지 않는다.")
    @Test
    void getStrikeZeroTest2() {
        final List<Number> numbers = List.of(new Number(1),
                new Number(2),
                new Number(3));
        final Computer computer = new Computer(numbers);
        final BaseBallNumbers playNumbers = new BaseBallNumbers(List.of(new Number(4), new Number(5), new Number(6)));
        assertThat(computer.getStrikeCount(playNumbers)).isEqualTo(ZERO_COUNT);
    }
}
