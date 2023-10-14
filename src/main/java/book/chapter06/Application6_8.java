package book.chapter06;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Application6_8 {
  public static void main(String[] args) {
    Station station = createSampleStation();
  }

  private static Station createSampleStation() {
    String name = "ZB1";
    List<Reading> readings =
        List.of(
            new Reading(47, LocalDateTime.of(2016, 11, 10, 9, 10)),
            new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 20)),
            new Reading(58, LocalDateTime.of(2016, 11, 10, 9, 30)),
            new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 40)),
            new Reading(51, LocalDateTime.of(2016, 11, 10, 9, 50)));

    return new Station(name, readings);
  }
}

@Data
@AllArgsConstructor
class Station {
  private String name;
  private List<Reading> readings;
}

@Data
@AllArgsConstructor
class Reading {
  private int temp;
  private LocalDateTime time;
}