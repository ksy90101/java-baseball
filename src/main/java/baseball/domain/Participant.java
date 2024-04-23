package baseball.domain;

public enum Participant {
    COMPUTER("컴퓨터"),
    PLAYER("사용자");

    private final String value;

    Participant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
