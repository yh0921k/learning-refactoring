package example._03_long_function;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ConsolePrinter extends AuctionPrinter {

  public ConsolePrinter(List<Participant> participants, int totalNumberOfAuctions) {
    super(participants, totalNumberOfAuctions);
  }

  @Override
  public void execute() throws IOException {
    participants.sort(Comparator.comparing(Participant::getName));
    this.participants.forEach(
        p ->
            System.out.printf(
                "%s %s:%s\n", p.getName(), createMark(p), p.getRate(totalNumberOfAuctions)));
  }
}
