package example._03_long_function;

import lombok.ToString;

@ToString
public class History {
  private final Participant participant;
  private final Long bidAmount;

  public History(Participant participant, Long bidAmount) {
    this.participant = participant;
    this.bidAmount = bidAmount;
  }

  public String getUserName() {
    return this.participant.getName();
  }
}
