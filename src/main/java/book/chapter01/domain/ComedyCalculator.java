package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;

public class ComedyCalculator extends PerformanceCalculator {
  public ComedyCalculator(Performance performance, Play play) {
    super(performance, play);
  }

  public int getAmount() throws Exception {
    int result = 30000;

    if (performance.getAudience() > 20) {
      result += 10000 + 500 * (performance.getAudience() - 20);
    }
    result += 300 * performance.getAudience();

    return result;
  }

  public int getVolumeCredits() {
    int result = 0;

    result += super.getVolumeCredits();
    result += Math.floor(performance.getAudience() / 5);

    return result;
  }
}
