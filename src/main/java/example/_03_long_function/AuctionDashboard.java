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

    execute(participants);
  }

  private static AuctionHub connectAuctionHub() {
    return new AuctionHub("서울", new SampleDataGenerator().generate());
  }

  private String getMarkdownForParticipant(Participant p) {
    return String.format(
        "| %s %s | %.2f%% |\n", p.getName(), createMark(p), p.getRate(totalNumberOfAuctions));
  }

  /* |:white_check_mark:|:white_check_mark:|:white_check_mark:|:x:| */
  private StringBuilder createMark(Participant p) {
    StringBuilder line = new StringBuilder();
    for (int i = 1; i <= totalNumberOfAuctions; i++) {
      if (p.getParticipatingAuctions().containsKey(i) && p.getParticipatingAuctions().get(i)) {
        line.append("|:white_check_mark:");
      } else {
        line.append("|:x:");
      }
    }
    return line;
  }

  /* | 참여자 (420) | 1주차 | 2주차 | 3주차 | 참석율 | | --- | --- | --- | --- | --- | */
  private StringBuilder createHeader(int totalNumberOfParticipants) {
    StringBuilder header =
        new StringBuilder(String.format("| 참여자 (%d) |", totalNumberOfParticipants));

    for (int index = 1; index <= totalNumberOfAuctions; index++) {
      header.append(String.format(" %d주차 |", index));
    }
    header.append(" 참석율 |\n");

    header.append("| --- ".repeat(Math.max(0, totalNumberOfAuctions + 2)));
    header.append("|\n");
    return header;
  }
}