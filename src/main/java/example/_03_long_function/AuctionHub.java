package example._03_long_function;

import java.util.List;
import lombok.ToString;

@ToString
public class AuctionHub {
  private final String region;
  private final List<Auction> auctions;

  public AuctionHub(String region, List<Auction> auctions) {
    this.region = region;
    this.auctions = auctions;
  }

  public int getAuctionSize() {
    return this.auctions.size();
  }

  public Auction getAuction(int auctionId) throws InterruptedException {
    Thread.sleep((long)(Math.random() * 4000));
    return this.auctions.get(auctionId - 1);
  }
}
