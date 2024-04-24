package baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class CountTest {

    @DisplayName("최소 수인 경우에는 true를 반환한다.")
    @Test
    void isMinValueTest() {
        final Count count = Count.of(0);
        assertThat(count.isMinValue()).isTrue();
    }

    @DisplayName("최소 수가 아닌 경우에는 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void isNotMinValueTest(final int value) {
        final Count count = Count.of(value);
        assertThat(count.isMinValue()).isFalse();
    }

    @DisplayName("최대 수인 경우에는 true를 반환한다.")
    @Test
    void isMaxValueTest() {
        final Count count = Count.of(3);
        assertThat(count.isMaxValue()).isTrue();
    }

    @DisplayName("최대 수가 아닌 경우에는 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void isNotMaxValueTest(final int value) {
        final Count count = Count.of(value);
        assertThat(count.isMaxValue()).isFalse();
    }

    @DisplayName("0~3 숫자가 아닌 다른 값이 들어오면 null을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 4})
    void ofWithValidValueTest(final int value) {
        assertThat(Count.of(value)).isNull();
    }
}
