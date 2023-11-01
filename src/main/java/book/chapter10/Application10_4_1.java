package book.chapter10;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.*;

public class Application10_4_1 {
  public static void main(String[] args) {
    List<Bird> birds = createSampleBirds();

    System.out.println("plumages(birds) = " + plumages(birds));
    System.out.println("speeds(birds) = " + speeds(birds));
  }

  private static List<Bird> createSampleBirds() {
    return List.of(new Bird("유럽 제비"), new Bird("아프리카 제비"), new Bird("노르웨이 파랑 앵무"), new Bird("없는종"));
  }

  public static Bird createBird(Bird bird) {
    switch (bird.type) {
      case "유럽 제비":
        return new EuropeanSwallow(bird);
      case "아프리카 제비":
        return new AfricanSwallow(bird);
      case "노르웨이 파랑 앵무":
        return new NorwegianBlueParrot(bird);
      default:
        return new Bird(bird);
    }
  }

  public static Map<String, String> plumages(List<Bird> birds) {
    return birds.stream()
        .map(bird -> createBird(bird))
        .collect(Collectors.toMap(bird -> bird.type, Bird::plumage));
  }

  public static Map<String, Integer> speeds(List<Bird> birds) {
    return birds.stream()
        .map(bird -> createBird(bird))
        .collect(Collectors.toMap(bird -> bird.type, Bird::airSpeedVelocity));
  }
}

@Data
@NoArgsConstructor
class Bird {
  public String type;
  public int numberOfCoconuts;
  public int voltage;
  public boolean isNailed;

  public Bird(String type) {
    this.type = type;
  }

  public Bird(Bird bird) {
    this.type = bird.type;
  }

  public String plumage() {
    return "알 수 없다.";
  }

  public int airSpeedVelocity() {
        return 0;
  }
}

class EuropeanSwallow extends Bird {
  public EuropeanSwallow(Bird bird) {
    super(bird);
  }

  public String plumage() {
    return "보통이다.";
  }

  public int airSpeedVelocity() {
    return 35;
  }
}

class AfricanSwallow extends Bird {
  public AfricanSwallow(Bird bird) {
    super(bird);
  }

  public String plumage() {
    return numberOfCoconuts > 2 ? "지쳤다." : "보통이다.";
  }

  public int airSpeedVelocity() {
    return 40 - 2 * numberOfCoconuts;
  }
}

class NorwegianBlueParrot extends Bird {
  public NorwegianBlueParrot(Bird bird) {
    super(bird);
  }

  public String plumage() {
    return voltage > 100 ? "그을렸다." : "예쁘다";
  }

  public int airSpeedVelocity() {
    return isNailed ? 0 : 10 + voltage / 10;
  }
}
