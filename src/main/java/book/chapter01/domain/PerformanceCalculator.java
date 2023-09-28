package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;

public class PerformanceCalculator {

  private final Performance performance;
  private final Play play;

  public PerformanceCalculator(Performance performance, Play play) {
    this.performance = performance;
    this.play = play;
  }
}
