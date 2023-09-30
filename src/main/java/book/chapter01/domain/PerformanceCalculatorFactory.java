package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;

public class PerformanceCalculatorFactory {
  public PerformanceCalculator createPerformanceCalculator(Performance performance, Play play) throws Exception {
    switch (play.getType()) {
      case TRAGEDY:
        return new TragedyCalculator(performance, play);
      case COMEDY:
        return new ComedyCalculator(performance, play);
      default:
        throw new Exception("해당 장르를 지원하지 않습니다.");
    }
  }
}
