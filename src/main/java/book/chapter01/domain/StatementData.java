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

  public String getCustomer() {
    return invoice.getCustomer();
  }

  public Performance[] getPerformances() {
    return invoice.getPerformances();
  }

  public int totalAmount() throws Exception {
    int result = 0;
    for (Performance perf : invoice.getPerformances()) {
      result += amountFor(perf);
    }
    return result;
  }

  public int totalVolumeCredits() {
    int result = 0;
    for (Performance perf : invoice.getPerformances()) {
      result += volumeCreditsFor(perf);
    }
    return result;
  }

  public Play playFor(Performance perf) {
    return Arrays.stream(plays)
        .filter(p -> p.getPlayId().equals(perf.getPlayId()))
        .findFirst()
        .get();
  }

  public int amountFor(Performance aPerformance) throws Exception {
    int result = 0;

    switch (playFor(aPerformance).getType()) {
      case "tragedy":
        result = 40000;
        if (aPerformance.getAudience() > 30) {
          result += 1000 * (aPerformance.getAudience() - 30);
        }
        break;
      case "comedy":
        result = 30000;
        if (aPerformance.getAudience() > 20) {
          result += 10000 + 500 * (aPerformance.getAudience() - 20);
        }
        result += 300 * aPerformance.getAudience();
        break;
      default:
        throw new Exception(String.format("알 수 없는 장르: %s", playFor(aPerformance).getType()));
    }

    return result;
  }

  private int volumeCreditsFor(Performance aPerformance) {
    int result = 0;
    result += Math.max(aPerformance.getAudience() - 30, 0);

    if (playFor(aPerformance).getType().equals("comedy")) {
      result += Math.floor(aPerformance.getAudience() / 5);
    }

    return result;
  }
}
