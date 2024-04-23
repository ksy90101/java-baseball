package baseball.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Commend {
    START(1),
    GAME_RECORD(2),
    END(9);

    private static final String DELIMITER = ", ";
    private static final Map<Integer, Commend> COMMEND_VALUES = Arrays.stream(values())
            .collect(Collectors.toMap(commend -> commend.value, Function.identity()));
    private static final String VALUE_STRING = COMMEND_VALUES.keySet()
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(DELIMITER));

    private final int value;

    Commend(final int value) {
        this.value = value;
    }

    public static Commend of(final int value) {
        return Optional.ofNullable(COMMEND_VALUES.get(value))
                .orElseThrow(() -> new IllegalArgumentException(VALUE_STRING + "만 입력 가능합니다."));
    }
}
