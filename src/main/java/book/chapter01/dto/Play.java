package book.chapter01.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Play {
  @JsonSetter("playID")
  private String playId;

  @JsonSetter("name")
  private String name;

  @JsonSetter("type")
  private String type;
}
