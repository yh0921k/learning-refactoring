package book.chapter01.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PlayType {
  @JsonProperty("tragedy")
  TRAGEDY,

  @JsonProperty("comedy")
  COMEDY
}
