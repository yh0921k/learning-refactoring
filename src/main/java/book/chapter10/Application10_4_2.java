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
    System.out.println(rating(voyage, history));
  }

  // 투자 등급
  public static String rating(Voyage voyage, History history) {
    int vpf = voyageProfitFactor(voyage, history);
    int vr = voyageRisk(voyage);
    int chr = captainHistoryRisk(voyage, history);
    if (vpf * 3 > vr + chr * 2) return "A";
    return "B";
  }

  // 항해 경로 위험요소
  private static int voyageRisk(Voyage voyage) {
    int result = 1;
    if (voyage.length > 4) result += 2;
    if (voyage.length > 8) result += voyage.length - 8;
    if (Stream.of("중국", "동인도").anyMatch(v -> voyage.zone.equals(v))) result += 4;
    return Math.max(result, 0);
  }

  // 선장의 항해 이력 위험 요소
  private static int captainHistoryRisk(Voyage voyage, History history) {
    int result = 1;
    if (history.getSize() < 5) result += 4;
    result += history.voyages.stream().filter(v -> v.profit < 0).count();
    if (voyage.zone.equals("중국") && hasChina(history)) result -= 2;
    return Math.max(result, 0);
  }

  // 중국 경유 여부
  private static boolean hasChina(History history) {
    return history.voyages.stream().anyMatch(v -> v.zone.equals("중국"));
  }

  // 수익 요인
  private static int voyageProfitFactor(Voyage voyage, History history) {
    int result = 2;
    if (voyage.zone.equals("중국")) result += 1;
    if (voyage.zone.equals("동인도")) result += 1;
    if (voyage.zone.equals("중국") && hasChina(history)) {
      result += 3;
      if (history.getSize() > 10) result += 1;
      if (voyage.length > 12) result += 1;
      if (voyage.length > 18) result -= 1;
    } else {
      if (history.getSize() > 8) result += 1;
      if (voyage.length > 14) result -= 1;
    }
    return result;
  }
}

class Rating {
  Voyage voyage;
  History history;

  public Rating(Voyage voyage, History history) {
    this.voyage = voyage;
    this.history = history;
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
}
