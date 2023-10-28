package example._02_duplicated_code;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuctionDashboard {
  public static void main(String[] args) {
    OngoingDashboard ongoingDashboard = new OngoingDashboard();
    ongoingDashboard.printOngoingItems();

    InProgressDashboard inProgressDashboard = new InProgressDashboard();
    inProgressDashboard.printInProgressItems("Seoul");
  }
}

@Getter
@AllArgsConstructor
class OngoingDashboard extends AuctionDashboard {
  public void printOngoingItems() {
    AuctionHub auctionHub = AuctionHub.connect("Seoul");
    Auction auction = auctionHub.getAuction();

    List<String> ongoingItems = new ArrayList<>();
    auction.getItems().forEach(item -> ongoingItems.add(item.getName()));

    ongoingItems.forEach(System.out::println);
  }
}

@Getter
@AllArgsConstructor
class InProgressDashboard extends AuctionDashboard {
  public void printInProgressItems(String region) {
    AuctionHub auctionHub = AuctionHub.connect(region);
    Auction auction = auctionHub.getAuction();

    List<String> inProgressItems = new ArrayList<>();
    auction.getItems().forEach(item -> inProgressItems.add(item.getName()));

    inProgressItems.forEach(System.out::println);
  }
}

@Getter
@AllArgsConstructor
class AuctionHub {

  private String region;
  private List<Auction> auctions;

  public AuctionHub(String region) {
    this.region = region;
    this.auctions =
        List.of(
            new Auction(List.of(new Item("A", 3000), new Item("B", 4000), new Item("C", 5000))));
  }

  public Auction getAuction() {
    return auctions.get(0);
  }

  public static AuctionHub connect(String region) {
    return new AuctionHub(region);
  }
}

@Getter
@AllArgsConstructor
class Auction {
  private List<Item> items;
}

@Getter
@AllArgsConstructor
class Item {
  private String name;
  private int price;
}