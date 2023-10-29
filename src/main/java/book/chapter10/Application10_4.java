package book.chapter10;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;

public class Application10_4 {
  public static void main(String[] args) {
    List<Bird> birds = createSampleBirds();

    System.out.println("plumages(birds) = " + plumages(birds));
    System.out.println("speeds(birds) = " + speeds(birds));
  }

  private static List<Bird> createSampleBirds() {
    return List.of(new Bird("유럽 제비"), new Bird("아프리카 제비"), new Bird("노르웨이 파랑 앵무"), new Bird("없는종"));
  }

  public static Map<String, String> plumages(List<Bird> birds) {
    return birds.stream().collect(Collectors.toMap(bird -> bird.type, Bird::plumage));
  }

  public static Map<String, Integer> speeds(List<Bird> birds) {
    return birds.stream()
        .collect(Collectors.toMap(bird -> bird.type, Bird::airSpeedVelocity));
  }
}

@Data
@RequiredArgsConstructor
class Bird {
  public final String type;
  public int numberOfCoconuts;
  public int voltage;
  public boolean isNailed;

  public String plumage() {
    switch (type) {
      case "유럽 제비":
        return "보통이다.";
      case "아프리카 제비":
        return numberOfCoconuts > 2 ? "지쳤다." : "보통이다.";
      case "노르웨이 파랑 앵무":
        return voltage > 100 ? "그을렸다." : "예쁘다";
      default:
        return "알 수 없다.";
    }
  }

  public int airSpeedVelocity() {
    switch (type) {
      case "유럽 제비":
        return 35;
      case "아프리카 제비":
        return 40 - 2 * numberOfCoconuts;
      case "노르웨이 파랑 앵무":
        return isNailed ? 0 : 10 + voltage / 10;
      default:
        return 0;
    }
  }
}
