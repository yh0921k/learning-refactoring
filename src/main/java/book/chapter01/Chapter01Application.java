package book.chapter01;

import book.chapter01.dto.Invoice;
import book.chapter01.dto.Invoice.Performance;
import book.chapter01.dto.Play;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

public class Chapter01Application {
  public static <T> T readJson(String filepath, Class<T> valueType) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      return objectMapper.readValue(new File(filepath), valueType);
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public static String statement(Invoice invoice, Play[] plays) throws Exception {
    int totalAmount = 0;
    int volumeCredits = 0;
    String result = String.format("청구 내역 (고객명: %s)\n", invoice.getCustomer());

    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en-US"));
    format.setCurrency(Currency.getInstance("USD"));
    format.setMinimumFractionDigits(2);

    for (Performance perf : invoice.getPerformances()) {
      Play play =
          Arrays.stream(plays)
              .filter(p -> p.getPlayId().equals(perf.getPlayId()))
              .findFirst()
              .get();

      int thisAmount = 0;

      switch (play.getType()) {
        case "tragedy":
          thisAmount = 40000;
          if (perf.getAudience() > 30) {
            thisAmount += 1000 * (perf.getAudience() - 30);
          }
          break;
        case "comedy":
          thisAmount = 30000;
          if (perf.getAudience() > 20) {
            thisAmount += 10000 + 500 * (perf.getAudience() - 20);
          }
          thisAmount += 300 * perf.getAudience();
          break;
        default:
          throw new Exception(String.format("알 수 없는 장르: %s", play.getType()));
      }
      // 포인트 적립
      volumeCredits += Math.max(perf.getAudience() - 30, 0);

      // 희극 관객 5명마다 추가 포인트 제공
      if (play.getType().equals("comedy")) {
        volumeCredits += Math.floor(perf.getAudience() / 5);
      }

      // 청구 내역 출력
      result +=
          String.format(
              "%15s:%12s%4s석\n",
              play.getName(), format.format(thisAmount / 100), perf.getAudience());
      totalAmount += thisAmount;
    }

    result += String.format("총액: %s\n", format.format(totalAmount / 100));
    result += String.format("적립 포인트: %s점\n", volumeCredits);
    return result;
  }

  public static void main(String[] args) throws Exception {
    Play[] plays = readJson("resource/plays.json", Play[].class);
    Invoice[] invoices = readJson("resource/invoices.json", Invoice[].class);

    String statement = statement(invoices[0], plays);
    System.out.println(statement);
  }
}
