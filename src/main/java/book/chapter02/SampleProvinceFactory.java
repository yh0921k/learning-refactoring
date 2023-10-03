package book.chapter02;

import java.util.ArrayList;
import java.util.List;

public class SampleProvinceFactory {
  public static Province createSampleProvinceData() {
    List<Producer> producers =
        new ArrayList<>(
            List.of(
                new Producer("Byzantium", 10, 9),
                new Producer("Attalia", 12, 10),
                new Producer("Sinope", 10, 6)));

    return new Province("Asia", 30, 20, producers);
  }
}
