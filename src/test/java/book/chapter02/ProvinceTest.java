package book.chapter02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
