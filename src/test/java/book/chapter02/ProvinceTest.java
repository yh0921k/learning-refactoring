package book.chapter02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProvinceTest {

  @Test
  @DisplayName("샘플데이터 생산 부족분 계산 검증")
  void shortFall() {
    // given
    Province province = SampleProvinceFactory.createSampleProvinceData();

    // when
    int result = province.shortFall();

    // then
    assertThat(result).isEqualTo(5);
  }
}
