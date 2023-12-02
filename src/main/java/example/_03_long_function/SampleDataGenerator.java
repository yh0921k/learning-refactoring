package example._03_long_function;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class SampleDataGenerator {

  private final Participant p1 = new Participant("P1");
  private final Participant p2 = new Participant("P2");
  private final Participant p3 = new Participant("P3");
  private final Participant p4 = new Participant("P4");
  private final Participant p5 = new Participant("P5");

  public List<Auction> generate() {
    List<Auction> auctions = new LinkedList<>();
    auctions.add(getAuctionSample01());
    auctions.add(getAuctionSample02());
    auctions.add(getAuctionSample03());
    auctions.add(getAuctionSample04());
    auctions.add(getAuctionSample05());
    auctions.add(getAuctionSample06());
    auctions.add(getAuctionSample07());
    auctions.add(getAuctionSample08());

    return auctions;
  }

  private Auction getAuctionSample01() {
    Item item = new Item("ItemA");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p2, 1000L));
    histories.add(new History(p5, 3000L));
    histories.add(new History(p2, 5000L));
    histories.add(new History(p5, 6000L));
    histories.add(new History(p5, 8000L));
    return new Auction(item, histories, p5, LocalDate.of(2023, 12, 16), 1000L, 8000L);
  }

  private Auction getAuctionSample02() {
    Item item = new Item("ItemB");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p2, 2000L));
    histories.add(new History(p1, 3000L));
    histories.add(new History(p3, 4000L));
    histories.add(new History(p2, 8000L));
    histories.add(new History(p3, 150000L));
    return new Auction(item, histories, p3, LocalDate.of(2023, 12, 17), 2000L, 150000L);
  }

  private Auction getAuctionSample03() {
    Item item = new Item("ItemC");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p3, 1000L));
    histories.add(new History(p2, 2000L));
    histories.add(new History(p1, 3000L));
    histories.add(new History(p3, 4000L));
    histories.add(new History(p5, 5000L));
    histories.add(new History(p4, 6000L));
    histories.add(new History(p1, 7000L));
    histories.add(new History(p2, 8000L));
    histories.add(new History(p1, 9000L));
    histories.add(new History(p3, 10000L));
    return new Auction(item, histories, p3, LocalDate.of(2023, 12, 18), 1000L, 10000L);
  }

  private Auction getAuctionSample04() {
    Item item = new Item("ItemD");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p5, 5000L));
    histories.add(new History(p1, 9000L));
    return new Auction(item, histories, p1, LocalDate.of(2023, 12, 19), 5000L, 9000L);
  }

  private Auction getAuctionSample05() {
    Item item = new Item("ItemE");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p5, 5000L));
    histories.add(new History(p1, 9000L));
    histories.add(new History(p2, 15000L));
    return new Auction(item, histories, p2, LocalDate.of(2023, 12, 20), 5000L, 15000L);
  }

  private Auction getAuctionSample06() {
    Item item = new Item("ItemF");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p4, 5000L));
    histories.add(new History(p1, 9000L));
    histories.add(new History(p4, 10000L));
    histories.add(new History(p4, 12000L));
    return new Auction(item, histories, p1, LocalDate.of(2023, 12, 21), 5000L, 12000L);
  }

  private Auction getAuctionSample07() {
    Item item = new Item("ItemG");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p1, 3000L));
    histories.add(new History(p4, 6000L));
    histories.add(new History(p1, 9000L));
    histories.add(new History(p3, 12000L));
    return new Auction(item, histories, p3, LocalDate.of(2023, 12, 22), 3000L, 9000L);
  }

  private Auction getAuctionSample08() {
    Item item = new Item("ItemH");
    List<History> histories = new LinkedList<>();
    histories.add(new History(p3, 2000L));
    histories.add(new History(p1, 4000L));
    histories.add(new History(p2, 6000L));
    histories.add(new History(p3, 8000L));
    return new Auction(item, histories, p3, LocalDate.of(2023, 12, 23), 2000L, 8000L);
  }
}
