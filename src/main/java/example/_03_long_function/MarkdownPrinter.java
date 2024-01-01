package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class MarkdownPrinter extends AuctionPrinter {

  public MarkdownPrinter(List<Participant> participants, int totalNumberOfAuctions) {
    super(participants, totalNumberOfAuctions);
  }

  @Override
  public void execute() throws IOException {
    try (FileWriter fileWriter = new FileWriter("participants.md");
        PrintWriter writer = new PrintWriter(fileWriter)) {
      participants.sort(Comparator.comparing(Participant::getName));
      writer.print(createHeader(participants.size()));
      participants.forEach(p -> writer.print(getMarkdownForParticipant(p)));
    }
  }

  private String getMarkdownForParticipant(Participant p) {
    return String.format(
        "| %s %s | %.2f%% |\n", p.getName(), createMark(p), p.getRate(totalNumberOfAuctions));
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
