package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class AuctionPrinter {
  public void execute(List<Participant> participants) throws IOException {
    FileWriter fileWriter = new FileWriter("participants.md");
    PrintWriter writer = new PrintWriter(fileWriter);
    try {
      participants.sort(Comparator.comparing(Participant::getName));
      writer.print(createHeader(participants.size()));
      participants.forEach(p -> writer.print(getMarkdownForParticipant(p)));
    } finally {
      writer.close();
      fileWriter.close();
    }
  }
}
