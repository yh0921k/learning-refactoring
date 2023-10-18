package book.chapter07;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Application7_4 {
  public static void main(String[] args) {
    Order order = createSampleOrder();
    System.out.println("order.getPrice() = " + order.getPrice());
  }

  private static Order createSampleOrder() {
    return new Order(new Item(10000), 10);
  }
}

@Getter
@AllArgsConstructor
class Order {
  private Item item;
  private int quantity;

  public double getPrice() {
    int basePrice = quantity * item.getPrice();
    double discountFactor = 0.98;

    if (basePrice > 1000) {
      discountFactor -= 0.03;
    }
    return basePrice * discountFactor;
  }
}

@Getter
@AllArgsConstructor
class Item {
  private int price;
}
