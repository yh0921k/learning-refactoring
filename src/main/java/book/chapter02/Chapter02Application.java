package book.chapter02;

import java.util.List;

public class Chapter02Application {
  public static void main(String[] args) {
    Province sampleProvinceData = SampleProvinceFactory.createSampleProvinceData();
    Province province =
        new Province(
            sampleProvinceData.getName(),
            sampleProvinceData.getDemand(),
            sampleProvinceData.getPrice(),
            sampleProvinceData.getProducers());
  }
}
