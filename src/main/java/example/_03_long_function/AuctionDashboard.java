package example._03_long_function;

import java.io.IOException;
import java.util.Arrays;
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
    Participant[] sameBidders = new Participant[totalNumberOfAuctions];

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

                Long firstBidAmount = Long.MAX_VALUE;
                Participant firstBidder = null;

                Long lastBidAmount = 0L;
                Participant lastBidder = null;

                for (History history : histories) {
                  Participant participant = findParticipant(history.getUserName(), participants);
                  participant.participate(auctionId);

                  if (firstBidder == null || history.getBidAmount() < firstBidAmount) {
                    firstBidder = history.getParticipant();
                    firstBidAmount = history.getBidAmount();
                  }

                  if (firstBidder == null || history.getBidAmount() > lastBidAmount) {
                    lastBidder = history.getParticipant();
                    lastBidAmount = history.getBidAmount();
                  }
                }

                if (firstBidder == lastBidder) {
                  sameBidders[auctionId - 1] = lastBidder;
                } else {
                  sameBidders[auctionId - 1] = null;
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
    Arrays.stream(sameBidders)
        .filter(bidder -> bidder != null)
        .forEach((bidder) -> System.out.println("bidder = " + bidder.getName()));
  }

  private Participant findParticipant(String username, List<Participant> participants) {
    return isNewParticipant(username, participants)
        ? createNewParticipant(username, participants)
        : findExistingParticipant(username, participants);
  }

  private Participant createNewParticipant(String username, List<Participant> participants) {
    Participant participant;
    participant = new Participant(username);
    participants.add(participant);
    return participant;
  }

  private Participant findExistingParticipant(String username, List<Participant> participants) {
    return participants.stream()
        .filter(p -> p.getName().equals(username))
        .findFirst()
        .orElseThrow();
  }

  private static boolean isNewParticipant(String username, List<Participant> participants) {
    return participants.stream().noneMatch(p1 -> p1.getName().equals(username));
  }

  private static AuctionHub connectAuctionHub() {
    return new AuctionHub("서울", new SampleDataGenerator().generate());
  }
}
