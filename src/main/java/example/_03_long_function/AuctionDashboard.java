package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuctionDashboard {

  public static void main(String[] args) throws IOException, InterruptedException {
    AuctionDashboard auctionDashboard = new AuctionDashboard();
    auctionDashboard.print();
  }

  public void print() throws IOException, InterruptedException {
    List<Auction> auctions = new SampleDataGenerator().generate();
    AuctionHub auctionHub = new AuctionHub("서울", auctions);
    List<Participant> participants = new CopyOnWriteArrayList<>();

    int totalNumberOfAuctions = auctionHub.getAuctionSize();
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

    FileWriter fileWriter = new FileWriter("participants.md");
    PrintWriter writer = new PrintWriter(fileWriter);
    try {
      participants.sort(Comparator.comparing(Participant::getName));

      writer.print(createHeader(participants.size(), totalNumberOfAuctions));

      participants.forEach(p -> writer.print(getMarkdownForParticipant(totalNumberOfAuctions, p)));
    } finally {
      writer.close();
      fileWriter.close();
    }
  }

  private double getRate(int totalNumberOfAuctions, Participant p) {
    long count = p.getParticipatingAuctions().values().stream().filter(v -> v == true).count();
    return (double) (count * 100 / totalNumberOfAuctions);
  }

  private String getMarkdownForParticipant(int totalNumberOfAuctions, Participant p) {
    return String.format(
        "| %s %s | %.2f%% |\n",
        p.getName(), createMark(totalNumberOfAuctions, p), getRate(totalNumberOfAuctions, p));
  }

  private StringBuilder createMark(int totalNumberOfAuctions, Participant p) {
    /* |:white_check_mark:|:white_check_mark:|:white_check_mark:|:x:| */
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

  private StringBuilder createHeader(int totalNumberOfParticipants, int totalNumberOfAuctions) {
    /* | 참여자 (420) | 1주차 | 2주차 | 3주차 | 참석율 | | --- | --- | --- | --- | --- | */
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