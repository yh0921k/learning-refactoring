package example._03_long_function;

import java.time.LocalDate;
import java.util.List;
import lombok.ToString;

@ToString
public class Auction {
  private final Item item;
  private final List<History> histories;
  private final Participant winner;

  private final LocalDate auctionDate;
  private final Long startAmount;
  private final Long finalAmount;

  public Auction(
      Item item,
      List<History> histories,
      Participant winner,
      LocalDate auctionDate,
      Long startAmount,
      Long finalAmount) {
    this.item = item;
    this.histories = histories;
    this.winner = winner;
    this.auctionDate = auctionDate;
    this.startAmount = startAmount;
    this.finalAmount = finalAmount;
  }

  public List<History> getHistories() {
    return this.histories;
  }
}
