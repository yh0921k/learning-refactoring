package example._03_long_function;

import java.io.IOException;
import java.util.List;

public abstract class AuctionPrinter {
  protected final List<Participant> participants;
  protected final int totalNumberOfAuctions;

  public AuctionPrinter(List<Participant> participants, int totalNumberOfAuctions) {
    this.participants = participants;
    this.totalNumberOfAuctions = totalNumberOfAuctions;
  }

  public abstract void execute() throws IOException;

  /* |:white_check_mark:|:white_check_mark:|:white_check_mark:|:x:| */
  protected StringBuilder createMark(Participant p) {
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
