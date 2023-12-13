package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class CsvPrinter extends AuctionPrinter {

  public CsvPrinter(List<Participant> participants, int totalNumberOfAuctions) {
    super(participants, totalNumberOfAuctions);
  }

  @Override
  public void execute() throws IOException {
    try (FileWriter fileWriter = new FileWriter("participants.cvs");
        PrintWriter writer = new PrintWriter(fileWriter)) {
      participants.sort(Comparator.comparing(Participant::getName));
      writer.println(createCsvHeader(this.participants.size()));
      this.participants.forEach(p -> writer.println(getCsvForParticipant(p)));
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
}
