package book.chapter06;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class Application6_8 {
  public static void main(String[] args) {
    Station station = createSampleStation();
    OperationPlan operationPlan = new OperationPlan(50, 55);

    List<Reading> alerts =
        readingsOutsideRange(
            station, operationPlan.getTemperatureFloor(), operationPlan.getTemperatureCeiling());
  }

  public static List<Reading> readingsOutsideRange(Station station, int min, int max) {
    return station.getReadings().stream()
        .filter(r -> r.getTemp() < min || r.getTemp() > max)
        .collect(Collectors.toList());
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

@Getter
@AllArgsConstructor
class OperationPlan {
  private int temperatureFloor;
  private int temperatureCeiling;
}

@Getter
@AllArgsConstructor
class NumberRange {
  private int min;
  private int max;
}