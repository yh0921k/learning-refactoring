package example._03_long_function;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuctionDashboard {

  private final AuctionHub auctionHub;
  private final int totalNumberOfAuctions;

  public AuctionDashboard(AuctionHub auctionHub) {
    this.auctionHub = auctionHub;
    this.totalNumberOfAuctions = auctionHub.getAuctionSize();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    AuctionDashboard auctionDashboard = new AuctionDashboard(connectAuctionHub());
    auctionDashboard.print();
  }

  public void print() throws IOException, InterruptedException {
    List<Participant> participants = new CopyOnWriteArrayList<>();
    ExecutorService service = Executors.newFixedThreadPool(4);
    CountDownLatch latch = new CountDownLatch(totalNumberOfAuctions);

    for (int index = 1; index <= totalNumberOfAuctions; index++) {
      int auctionId = index;
      service.execute(
          new Runnable() {
            @Override
            public void run() {
              try {
                Auction auction = auctionHub.getAuction(auctionId);
                List<History> histories = auction.getHistories();
                for (History history : histories) {
                  String username = history.getUserName();
                  boolean isNewUser =
                      participants.stream().noneMatch(p -> p.getName().equals(username));
                  Participant participant = null;
                  if (isNewUser) {
                    participant = new Participant(username);
                    participants.add(participant);
                  } else {
                    participant =
                        participants.stream()
                            .filter(p -> p.getName().equals(username))
                            .findFirst()
                            .orElseThrow();
                  }

                  participant.participate(auctionId);
                }

                latch.countDown();
              } catch (Exception e) {
                throw new IllegalArgumentException(e);
              }
            }
          });
    }

    latch.await();
    service.shutdown();

    new AuctionPrinter(participants, totalNumberOfAuctions).execute();
  }

  private static AuctionHub connectAuctionHub() {
    return new AuctionHub("서울", new SampleDataGenerator().generate());
  }
}