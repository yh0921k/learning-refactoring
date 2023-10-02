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

  public String readPlainText() {
    return renderPlainText();
  }

  public String readHtml() {
    return renderHtml();
  }

  private String renderPlainText() {
    StringBuilder result =
        new StringBuilder(String.format("청구 내역 (고객명: %s)\n", statementData.getCustomer()));

    for (Performance performance : statementData.getPerformances()) {
      result.append(
          String.format(
              "%15s:%12s%4s석\n",
              statementData.getPlayName(performance),
              usd(statementData.amountFor(performance)),
              performance.getAudience()));
    }

    result.append(String.format("총액: %s\n", usd(statementData.totalAmount())));
    result.append(String.format("적립 포인트: %s점\n", statementData.totalVolumeCredits()));
    return result.toString();
  }

  private String renderHtml() {
    StringBuilder result =
        new StringBuilder(String.format("<h1>청구내역 (고객명: %s)\n</h1>", statementData.getCustomer()));
    result.append("<table>\n");

    result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th>");
    for (Performance performance : statementData.getPerformances()) {
      result.append(
          String.format(
              "<tr><td>%s</td><td>%s</td><td>%d석</td></tr>",
              statementData.getPlayName(performance),
              usd(statementData.amountFor(performance)),
              performance.getAudience()));
    }
    result.append("</table><br>");

    result.append(String.format("총액: %s<br>", usd(statementData.totalAmount())));
    result.append(String.format("적립 포인트: %s점<br>", statementData.totalVolumeCredits()));
    return result.toString();
  }

  private String usd(long aNumber) {
    NumberFormat usdFormatter = NumberFormat.getCurrencyInstance(new Locale("en-US"));
    usdFormatter.setCurrency(Currency.getInstance("USD"));
    usdFormatter.setMinimumFractionDigits(2);

    return usdFormatter.format(aNumber / 100);
  }
}
