package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;

public class TragedyCalculator extends PerformanceCalculator {
  public TragedyCalculator(Performance performance, Play play) {
    super(performance, play);
  }

  public int getAmount() throws Exception {
    int result = 40000;

    if (performance.getAudience() > 30) {
      result += 1000 * (performance.getAudience() - 30);
    }

    return result;
  }
}
