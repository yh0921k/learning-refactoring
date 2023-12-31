package book.chapter10;

import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

public class Application10_4_2 {
  public static void main(String[] args) {
    Voyage voyage = new Voyage("서인도", 10, 0);
    List<Voyage> voyages =
        List.of(
            new Voyage("동인도", 0, 5),
            new Voyage("서인도", 0, 15),
            new Voyage("중국", 0, -2),
            new Voyage("서아프리카", 0, 7));
    History history = new History(voyages);
    Rating rating = createRating(voyage, history);
    System.out.println(rating.rating(voyage, history));
  }

  public static Rating createRating(Voyage voyage, History history) {
    if (voyage.zone.equals("중국") && history.hasChina()) {
      return new ExperiencedChinaRating(voyage, history);
    }
    return new Rating(voyage, history);
  }
}

class ExperiencedChinaRating extends Rating {
  public ExperiencedChinaRating(Voyage voyage, History history) {
    super(voyage, history);
  }

  @Override
  protected int captainHistoryRisk(Voyage voyage, History history) {
    return super.captainHistoryRisk(voyage, history) - 2;
  }

  @Override
  protected int voyageLengthFactor(Voyage voyage) {
    int result = 0;
    if (voyage.length > 12) result += 1;
    if (voyage.length > 18) result -= 1;

    return result;
  }

  @Override
  protected int historyLengthFactor(History history) {
    return history.getSize() > 10 ? 1 : 0;
  }

  @Override
  protected int voyageProfitFactor(Voyage voyage, History history) {
    return super.voyageProfitFactor(voyage, history) + 3;
  }
}

class Rating {
  Voyage voyage;
  History history;

  public Rating(Voyage voyage, History history) {
    this.voyage = voyage;
    this.history = history;
  }

  // 투자 등급
  public String rating(Voyage voyage, History history) {
    int vpf = voyageProfitFactor(voyage, history);
    int vr = voyageRisk(voyage);
    int chr = captainHistoryRisk(voyage, history);
    if (vpf * 3 > vr + chr * 2) return "A";
    return "B";
  }

  // 항해 경로 위험요소
  private int voyageRisk(Voyage voyage) {
    int result = 1;
    if (voyage.length > 4) result += 2;
    if (voyage.length > 8) result += voyage.length - 8;
    if (Stream.of("중국", "동인도").anyMatch(v -> voyage.zone.equals(v))) result += 4;
    return Math.max(result, 0);
  }

  // 선장의 항해 이력 위험 요소
  protected int captainHistoryRisk(Voyage voyage, History history) {
    int result = 1;
    if (history.getSize() < 5) result += 4;
    result += history.voyages.stream().filter(v -> v.profit < 0).count();
    return Math.max(result, 0);
  }

  // 수익 요인
  protected int voyageProfitFactor(Voyage voyage, History history) {
    int result = 2;
    if (voyage.zone.equals("중국")) result += 1;
    if (voyage.zone.equals("동인도")) result += 1;
    result += historyLengthFactor(history);
    result += voyageLengthFactor(voyage);
    return result;
  }

  protected int voyageLengthFactor(Voyage voyage) {
    return voyage.length > 14 ? -1 : 0;
  }

  protected int historyLengthFactor(History history) {
    return history.getSize() > 8 ? 1 : 0;
  }
}

@AllArgsConstructor
class Voyage {
  public String zone;
  public int length;
  public int profit;
}

class History {
  List<Voyage> voyages;

  public History(List<Voyage> voyages) {
    this.voyages = voyages;
  }

  public int getSize() {
    return voyages.size();
  }

  public boolean hasChina() {
    return voyages.stream().anyMatch(v -> v.zone.equals("중국"));
  }
}
