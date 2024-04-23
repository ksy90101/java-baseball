package baseball.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int id;

    private final Computer computer;

    private final PlayerTimes limitPlayerTimes;

    private final List<PlayerRecord> playerNumbers = new ArrayList<>();

    private final LocalDateTime startAt = LocalDateTime.now();

    private LocalDateTime endAt;

    private Participant winner;

    public Game(Computer computer, int playerTimes) {
        this.computer = computer;
        this.limitPlayerTimes = new PlayerTimes(playerTimes);
    }

    public void addPlayerRecord(PlayerRecord playerRecord) {
        if (endAt != null) {
            throw new IllegalArgumentException("이미 종료된 게임입니다.");
        }

        playerNumbers.add(playerRecord);

        if (playerRecord.isSuccess() || isLimit()) {
            this.winner = extraWinner(playerRecord);
            this.endAt = LocalDateTime.now();
        }

    }

    private Participant extraWinner(PlayerRecord lastPlayerRecord) {
        if (lastPlayerRecord.isSuccess()) {
            return Participant.PLAYER;
        }
        return Participant.COMPUTER;
    }

    public boolean isLimit() {
        return getPlayerTimes() >= limitPlayerTimes.value();
    }

    public boolean samePlayerTimes(int playerTimes) {
        return this.getPlayerTimes() == playerTimes;
    }

    public boolean isComputerWin() {
        return this.winner == Participant.COMPUTER;
    }

    public boolean isPlayerWin() {
        return this.winner == Participant.PLAYER;
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

    public Participant getWinner() {
        return winner;
    }

    public int getLimitPlayerTimes() {
        return limitPlayerTimes.value();
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PlayerRecord> getPlayerNumbers() {
        return playerNumbers;
    }

    public boolean isFinished() {
        return endAt != null;
    }

    public boolean sameLimitPlayerTimes(int limitPlayerTimes) {
        return this.limitPlayerTimes.equals(new PlayerTimes(limitPlayerTimes));
    }
}
