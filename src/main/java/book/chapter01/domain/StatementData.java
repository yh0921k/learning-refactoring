package book.chapter01.domain;

import book.chapter01.dto.Invoice;
import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatementData {
  private final Invoice invoice;
  private final Play[] plays;
  private final PerformanceCalculatorFactory calculatorFactory = new PerformanceCalculatorFactory();

  public String getPlayName(Performance performance) {
    try {
      return playFor(performance).getName();
    } catch (Exception exception) {
      return "";
    }
  }

  public String getCustomer() {
    return invoice.getCustomer();
  }

  public Performance[] getPerformances() {
    return invoice.getPerformances();
  }

  public int totalAmount() {
    return Arrays.stream(invoice.getPerformances()).map(this::amountFor).reduce(0, Integer::sum);
  }

  public int totalVolumeCredits() {
    return Arrays.stream(invoice.getPerformances())
        .map(this::volumeCreditsFor)
        .reduce(0, (Integer::sum));
  }

  public Play playFor(Performance performance) throws Exception {
    return Arrays.stream(plays)
        .filter(p -> p.getPlayId().equals(performance.getPlayId()))
        .findFirst()
        .orElseThrow(() -> new Exception("해당하는 공연을 찾지 못했습니다."));
  }

  public int amountFor(Performance performance) {
    try {
      return calculatorFactory
          .createPerformanceCalculator(performance, playFor(performance))
          .getAmount();
    } catch (Exception exception) {
      return 0;
    }
  }

  private int volumeCreditsFor(Performance performance) {
    try {
      return calculatorFactory
          .createPerformanceCalculator(performance, playFor(performance))
          .getVolumeCredits();
    } catch (Exception exception) {
      return 0;
    }
  }
}
