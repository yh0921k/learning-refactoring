package example._03_long_function;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuctionDashboard {

  private final AuctionHub auctionHub;
  private final int totalNumberOfAuctions;
  private final List<Participant> participants;
  private final Participant[] sameBidders;

  public AuctionDashboard(AuctionHub auctionHub) {
    this.auctionHub = auctionHub;
    this.totalNumberOfAuctions = auctionHub.getAuctionSize();
    this.participants = new CopyOnWriteArrayList<>();
    this.sameBidders = new Participant[totalNumberOfAuctions];
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    AuctionDashboard auctionDashboard = new AuctionDashboard(connectAuctionHub());
    auctionDashboard.print();
  }

  public void print() throws IOException, InterruptedException {
    checkAuctionHub();
    new AuctionPrinter(participants, totalNumberOfAuctions, PrinterMode.CONSOLE).execute();
    printSameBidder();
  }

  private void printSameBidder() {
    Arrays.stream(sameBidders)
        .filter(Objects::nonNull)
        .forEach((bidder) -> System.out.println("bidder = " + bidder.getName()));
  }

  private void checkAuctionHub() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(4);
    CountDownLatch latch = new CountDownLatch(totalNumberOfAuctions);

    for (int index = 1; index <= totalNumberOfAuctions; index++) {
      int auctionId = index;
      service.execute(
          () -> {
            try {
              checkAuction(auctionId);
              latch.countDown();
            } catch (Exception e) {
              throw new IllegalArgumentException(e);
            }
          });
    }

    latch.await();
    service.shutdown();
  }

  private void checkAuction(int auctionId) throws InterruptedException {
    Auction auction = auctionHub.getAuction(auctionId);
    List<History> histories = auction.getHistories();

    checkParticipatingAuction(histories, auctionId);
    checkSameBidders(auctionId, histories);
  }

  private void checkSameBidders(int auctionId, List<History> histories) {
    sameBidders[auctionId - 1] = findSameBidder(histories);
  }

  private Participant findSameBidder(List<History> histories) {
    return findFirstBidder(histories) == findLastBidder(histories)
        ? findLastBidder(histories)
        : null;
  }

  private Participant findLastBidder(List<History> histories) {
    return histories.stream()
        .max(Comparator.comparingLong(History::getBidAmount))
        .map(History::getParticipant)
        .orElse(null);
  }

  private Participant findFirstBidder(List<History> histories) {
    return histories.stream()
        .min(Comparator.comparingLong(History::getBidAmount))
        .map(History::getParticipant)
        .orElse(null);
  }

  private void checkParticipatingAuction(List<History> histories, int auctionId) {
    histories.stream()
        .map(history -> findParticipant(history.getUserName(), participants))
        .forEach(participant -> participant.participate(auctionId));
  }

  private Participant findParticipant(String username, List<Participant> participants) {
    return isNewParticipant(username, participants)
        ? createNewParticipant(username, participants)
        : findExistingParticipant(username, participants);
  }

  private Participant createNewParticipant(String username, List<Participant> participants) {
    Participant participant = new Participant(username);
    participants.add(participant);
    return participant;
  }

  private Participant findExistingParticipant(String username, List<Participant> participants) {
    return participants.stream()
        .filter(p -> p.getName().equals(username))
        .findFirst()
        .orElseThrow();
  }

  private boolean isNewParticipant(String username, List<Participant> participants) {
    return participants.stream().noneMatch(p1 -> p1.getName().equals(username));
  }

  private static AuctionHub connectAuctionHub() {
    return new AuctionHub("서울", new SampleDataGenerator().generate());
  }
}
