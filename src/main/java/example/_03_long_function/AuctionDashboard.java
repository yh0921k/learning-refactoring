package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

      /* | 참여자 (420) | 1주차 | 2주차 | 3주차 | 참석율 | | --- | --- | --- | --- | --- | */
      StringBuilder header =
          new StringBuilder(String.format("| 참여자 (%d) |", participants.size()));

      for (int index = 1; index <= totalNumberOfAuctions; index++) {
        header.append(String.format(" %d주차 |", index));
      }
      header.append(" 참석율 |\n");

      header.append("| --- ".repeat(Math.max(0, totalNumberOfAuctions + 2)));
      header.append("|\n");
      writer.print(header);

      participants.forEach(
          p -> {
            String name = p.getName();
            Map<Integer, Boolean> participatingAuctions = p.getParticipatingAuctions();

            long count =
                participatingAuctions.values().stream().filter(v -> v == true).count();
            double rate = count * 100 / totalNumberOfAuctions;

            /* |:white_check_mark:|:white_check_mark:|:white_check_mark:|:x:| */
            StringBuilder line = new StringBuilder();
            for (int i = 1; i <= totalNumberOfAuctions; i++) {
              if (participatingAuctions.containsKey(i) && participatingAuctions.get(i)) {
                line.append("|:white_check_mark:");
              } else {
                line.append("|:x:");
              }
            }

            String markdownForParticipant =
                String.format(
                    "| %s %s | %.2f%% |\n", name, line, rate);
            writer.print(markdownForParticipant);
          });
    } finally{
      writer.close();
      fileWriter.close();
    }
  }
}