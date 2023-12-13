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
}
