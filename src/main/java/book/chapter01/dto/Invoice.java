package book.chapter01.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Invoice {
  @JsonSetter("customer")
  private String customer;

  @JsonSetter("performances")
  private Performance[] performances;

  @Getter
  @ToString
  public static class Performance {
    @JsonSetter("playID")
    private String playId;

    @JsonSetter("audience")
    private int audience;
  }
}
