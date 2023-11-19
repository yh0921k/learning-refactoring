package example._03_long_function;

import lombok.ToString;

@ToString
public class Item {
  private final String name;

  public Item(String name) {
    this.name = name;
  }
}
