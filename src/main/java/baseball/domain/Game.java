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

    public Game(Computer computer) {
        this.computer = computer;
    }

    public void addPlayerRecord(PlayerRecord playerRecord) {
        playerNumbers.add(playerRecord);
    }

    public void end() {
        endAt = LocalDateTime.now();
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

    public void setId(int id) {
        this.id = id;
    }

    public List<PlayerRecord> getPlayerNumbers() {
        return playerNumbers;
    }
}
