package baseball.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record BaseBallNumbers(List<Number> numbers) {
    public static final int TOTAL_COUNT = 3;

    public BaseBallNumbers {
        validateDuplicateNumbers(numbers);
        validateSize(numbers);
    }

    public boolean isContains(Number number) {
        return numbers.contains(number);
    }

    public boolean isSameIndexOf(Number number, int index) {
        return numbers.get(index).equals(number);
    }

    public int indexOf(Number number) {
        return numbers.indexOf(number);
    }

    @Override
    public List<Number> numbers() {
        return Collections.unmodifiableList(numbers);
    }

    public List<Integer> getValueNumbers() {
        return numbers.stream()
                .map(Number::value)
                .toList();
    }

    private boolean isTotalSize(List<Number> numbers) {
        return numbers.size() == TOTAL_COUNT;
    }

    private boolean isDuplication(List<Number> numbers) {
        return numbers.stream()
                .distinct()
                .count() != numbers.size();
    }

    private void validateDuplicateNumbers(List<Number> numbers) {
        if (isDuplication(numbers)) {
            throw new IllegalArgumentException("중복된 숫자가 있습니다.");
        }
    }

    private void validateSize(List<Number> numbers) {
        if (!isTotalSize(numbers)) {
            throw new IllegalArgumentException("3개의 숫자를 입력해주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBallNumbers that = (BaseBallNumbers) o;
        return Objects.equals(numbers, that.numbers);
    }

}
