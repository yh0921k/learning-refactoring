package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class AuctionPrinter {
  private final List<Participant> participants;
  private final int totalNumberOfAuctions;

  public AuctionPrinter(
      List<Participant> participants, int totalNumberOfAuctions) {
    this.participants = participants;
    this.totalNumberOfAuctions = totalNumberOfAuctions;
  }

  public void execute() throws IOException {
    switch (printerMode) {
      case CSV -> {

      }
      case CONSOLE -> {

      }
      case MARKDOWN -> {
        try (FileWriter fileWriter = new FileWriter("participants.md");
            PrintWriter writer = new PrintWriter(fileWriter)) {
          participants.sort(Comparator.comparing(Participant::getName));
          writer.print(createHeader(participants.size()));
          participants.forEach(p -> writer.print(getMarkdownForParticipant(p)));
        }
      }
    }
  }

  private String getCsvForParticipant(Participant participant) {
    StringBuilder line = new StringBuilder();
    line.append(participant.getName());
    for (int i = 1; i <= this.totalNumberOfAuctions; i++) {
      if (participant.getParticipatingAuctions().containsKey(i)
          && participant.getParticipatingAuctions().get(i)) {
        line.append(",O");
      } else {
        line.append(",X");
      }
    }
    line.append(",").append(participant.getRate(this.totalNumberOfAuctions));
    return line.toString();
  }

  private String createCsvHeader(int totalNumberOfParticipants) {
    StringBuilder header = new StringBuilder(String.format("참여자 (%d),", totalNumberOfParticipants));
    for (int index = 1; index <= this.totalNumberOfAuctions; index++) {
      header.append(String.format("%d주차,", index));
    }
    header.append("참석율");
    return header.toString();
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
