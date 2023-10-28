package book.chapter10;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Application10_4 {
  public static void main(String[] args) {}

  public static String plumage(Bird bird) {
    switch (bird.type) {
      case "유럽 제비":
        return "보통이다.";
      case "아프리카 제비":
        return bird.numberOfCoconuts > 2 ? "지쳤다." : "보통이다.";
      case "노르웨이 파랑 앵무":
        return bird.voltage > 100 ? "그을렸다." : "예쁘다";
      default:
        return "알 수 없다.";
    }
  }

  public static int airSpeedVelocity(Bird bird) {
    switch (bird.type) {
      case "유럽 제비":
        return 35;
      case "아프리카 제비":
        return 40 - 2 * bird.numberOfCoconuts;
      case "노르웨이 파랑 앵무":
        return bird.isNailed ? 0 : 10 + bird.voltage / 10;
      default:
        return 0;
    }
  }
}

@Data
@AllArgsConstructor
class Bird {
  String type;
  int numberOfCoconuts;
  int voltage;
  boolean isNailed;
}
