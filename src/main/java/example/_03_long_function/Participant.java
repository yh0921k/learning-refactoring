package example._03_long_function;

import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

@ToString
public class Participant {
  private final String name;
  private final Map<Integer, Boolean> participatingAuctions;
  public Participant(String name) {
    this.name = name;
    this.participatingAuctions = new HashMap<>();
  }

  public void participate(int index) {
    this.participatingAuctions.put(index, true);
  }

  public String getName() {
    return this.name;
  }

  public Map<Integer, Boolean> getParticipatingAuctions() {
    return participatingAuctions;
  }
}