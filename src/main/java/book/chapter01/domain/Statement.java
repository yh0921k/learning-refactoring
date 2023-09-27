package book.chapter01.domain;

import book.chapter01.dto.Invoice;
import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Statement {

  private final StatementData statementData;

  public Statement(Invoice invoice, Play[] plays) {
    statementData = new StatementData(invoice, plays);
  }

  public String readPlainText() throws Exception {
    return renderPlainText();
  }

  public String readHtml() throws Exception {
    return renderHtml();
  }

  private String renderPlainText() throws Exception {
    String result = String.format("청구 내역 (고객명: %s)\n", statementData.getCustomer());

    for (Performance perf : statementData.getPerformances()) {
      result +=
          String.format(
              "%15s:%12s%4s석\n",
              statementData.playFor(perf).getName(),
              usd(statementData.amountFor(perf)),
              perf.getAudience());
    }

    result += String.format("총액: %s\n", usd(statementData.totalAmount()));
    result += String.format("적립 포인트: %s점\n", statementData.totalVolumeCredits());
    return result;
  }

  private String renderHtml() throws Exception {
    String result = String.format("<h1>청구내역 (고객명: %s)\n</h1>", statementData.getCustomer());
    result += "<table>\n";

    result += "<tr><th>연극</th><th>좌석 수</th><th>금액</th>";
    for (Performance performance : statementData.getPerformances()) {
      result +=
          String.format(
              "<tr><td>%s</td><td>%s</td><td>%d석</td></tr>",
              statementData.playFor(performance).getName(),
              usd(statementData.amountFor(performance)),
              performance.getAudience());
    }
    result += "</table><br>";

    result += String.format("총액: %s<br>", usd(statementData.totalAmount()));
    result += String.format("적립 포인트: %s점<br>", statementData.totalVolumeCredits());
    return result;
  }

  private String usd(long aNumber) {
    NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(new Locale("en-US"));
    usdFormatter.setCurrency(Currency.getInstance("USD"));
    usdFormatter.setMinimumFractionDigits(2);

    return usdFormatter.format(aNumber / 100);
  }
}
