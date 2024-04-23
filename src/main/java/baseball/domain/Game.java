package baseball.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id;

    private final Computer computer;

    private final List<PlayerRecord> playerNumbers = new ArrayList<>();

    private final LocalDateTime startAt = LocalDateTime.now();

    private LocalDateTime endAt;

    public Game(final Computer computer) {
        this.computer = computer;
    }

    public void addPlayerRecord(final PlayerRecord playerRecord) {
        if (endAt != null) {
            throw new IllegalArgumentException("게임이 종료되었습니다.");
        }

        playerNumbers.add(playerRecord);

        if (playerRecord.isSuccess()) {
            endAt = LocalDateTime.now();
        }
    }

    public int getId() {
        return id;
    }

    public Computer getComputer() {
        return computer;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public int getPlayerTimes() {
        return playerNumbers.size();
    }

    public void setId(final int id) {
        this.id = id;
    }

    public List<PlayerRecord> getPlayerNumbers() {
        return playerNumbers;
    }
}
