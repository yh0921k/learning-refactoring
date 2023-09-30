package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;

public class ComedyCalculator extends PerformanceCalculator {
  public ComedyCalculator(Performance performance, Play play) {
    super(performance, play);
  }
}
