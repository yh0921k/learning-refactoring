package book.chapter01.domain;

import book.chapter01.dto.Performance;
import book.chapter01.dto.Play;

public abstract class PerformanceCalculator {

  protected final Performance performance;
  protected final Play play;

  public PerformanceCalculator(Performance performance, Play play) {
    this.performance = performance;
    this.play = play;
  }

  public abstract int getAmount() throws Exception;

  public abstract int getVolumeCredits();
}
