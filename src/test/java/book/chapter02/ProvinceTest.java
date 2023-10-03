package book.chapter02;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProvinceTest {

  Province province;

  @BeforeEach
  void beforeEach() {
    province = SampleProvinceFactory.createSampleProvinceData();
  }

  @Test
  @DisplayName("샘플데이터 생산 부족분 계산 검증")
  void shortFall() {
    // when
    int result = province.shortFall();

    // then
    assertThat(result).isEqualTo(5);
  }

  @Test
  @DisplayName("샘플데이터 총수익 계산 검증")
  void profit() {
    // when
    int result = province.profit();

    // then
    assertThat(result).isEqualTo(230);
  }

  @Test
  @DisplayName("생산량에 따른 총 수익 및 생산 부족분 계산")
  void setProduction() {
    // given
    int shortFall = -6;
    int profit = 292;

    // when
    province.getProducers().get(0).setProduction(20);
    int actualShortFall = province.shortFall();
    int actualProfit = province.profit();

    // then
    assertThat(actualShortFall).isEqualTo(shortFall);
    assertThat(actualProfit).isEqualTo(profit);
  }

  @Test
  @DisplayName("생산자가 존재하지 않는 경우 테스트")
  void noProducers() {
    // given
    Province province = new Province("No Producers", -1, 20, new ArrayList<>());
    int shortFall = -1;
    int profit = -20;

    // when
    int actualShortFall = province.shortFall();
    int actualProfit = province.profit();

    // then
    assertThat(actualShortFall).isEqualTo(shortFall);
    assertThat(actualProfit).isEqualTo(profit);
  }
}
