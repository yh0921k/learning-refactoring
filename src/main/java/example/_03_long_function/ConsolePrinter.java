package example._03_long_function;

import java.util.Comparator;
import java.util.List;

public class ConsolePrinter extends AuctionPrinter {

  public ConsolePrinter(List<Participant> participants, int totalNumberOfAuctions) {
    super(participants, totalNumberOfAuctions);
  }

  @Override
  public void execute() {
    participants.sort(Comparator.comparing(Participant::getName));
    this.participants.forEach(this::printConsole);
  }

  private void printConsole(Participant p) {
    System.out.printf("%s %s:%s\n", p.getName(), createMark(p), p.getRate(totalNumberOfAuctions));
  }
}
