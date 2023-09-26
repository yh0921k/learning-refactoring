package book.chapter01.domain;

import book.chapter01.dto.Invoice;
import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Statement {

  private final Invoice invoice;
  private final Play[] plays;

  public String readPlainText() throws Exception {
    return renderPlainText();
  }

  private String renderPlainText() throws Exception {
    String result = String.format("청구 내역 (고객명: %s)\n", invoice.getCustomer());

    for (Performance perf : invoice.getPerformances()) {
      // 청구 내역 출력
      result +=
          String.format(
              "%15s:%12s%4s석\n",
              playFor(perf).getName(), usd(amountFor(perf)), perf.getAudience());
    }

    result += String.format("총액: %s\n", usd(totalAmount()));
    result += String.format("적립 포인트: %s점\n", totalVolumeCredits());
    return result;
  }

  private int totalAmount() throws Exception {
    int result = 0;
    for (Performance perf : invoice.getPerformances()) {
      result += amountFor(perf);
    }
    return result;
  }

  private int totalVolumeCredits() {
    int result = 0;
    for (Performance perf : invoice.getPerformances()) {
      result += volumeCreditsFor(perf);
    }
    return result;
  }

  private Play playFor(Performance perf) {
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

    // 희극 관객 5명마다 추가 포인트 제공
    if (playFor(aPerformance).getType().equals("comedy")) {
      result += Math.floor(aPerformance.getAudience() / 5);
    }

    return result;
  }

  private String usd(long aNumber) {
    NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(new Locale("en-US"));
    usdFormatter.setCurrency(Currency.getInstance("USD"));
    usdFormatter.setMinimumFractionDigits(2);

    return usdFormatter.format(aNumber / 100);
  }
}
