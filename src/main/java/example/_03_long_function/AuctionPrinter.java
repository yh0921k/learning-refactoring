package example._03_long_function;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class AuctionPrinter {
  protected final List<Participant> participants;
  protected final int totalNumberOfAuctions;

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

      }
    }
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
}
