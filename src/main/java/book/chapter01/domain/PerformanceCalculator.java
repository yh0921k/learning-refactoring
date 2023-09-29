package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;
import book.chapter01.dto.PlayType;

public class PerformanceCalculator {

  private final Performance performance;
  private final Play play;

  public PerformanceCalculator(Performance performance, Play play) {
    this.performance = performance;
    this.play = play;
  }

  public int getAmount() throws Exception {
    int result = 0;

    switch (play.getType()) {
      case TRAGEDY:
        result = 40000;
        if (performance.getAudience() > 30) {
          result += 1000 * (performance.getAudience() - 30);
        }
        break;
      case COMEDY:
        result = 30000;
        if (performance.getAudience() > 20) {
          result += 10000 + 500 * (performance.getAudience() - 20);
        }
        result += 300 * performance.getAudience();
        break;
      default:
        throw new Exception(String.format("알 수 없는 장르: %s", play.getType()));
    }

    return result;
  }

  public int getVolumeCredits() {
    int result = 0;
    result += Math.max(performance.getAudience() - 30, 0);

    if (play.getType() == PlayType.COMEDY) {
      result += Math.floor(performance.getAudience() / 5);
    }

    return result;
  }
}
