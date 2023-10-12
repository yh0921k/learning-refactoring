package example._01_mysterious_name;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderManagement {

  private final Set<String> usernames = new HashSet<>();
  private final Set<String> orderNumbers = new HashSet<>();

  /**
   * 주어진 사용자 주문 리스트에 대해 각 사용자 이름과 주문번호를 읽어온다.
   */
  private void loadOrders() {
    List<UserOrder> userOrders = getUserOrderFromAPI();
    for (UserOrder userOrder : userOrders) {
      usernames.add(userOrder.getUsername());
      orderNumbers.add(userOrder.getOrderNumber());
    }
  }

  public Set<String> getUsernames() {
    return usernames;
  }

  public Set<String> getOrderNumbers() {
    return orderNumbers;
  }

  public static void main(String[] args) {
    OrderManagement orderManagement = new OrderManagement();
    orderManagement.loadOrders();
    orderManagement.getUsernames().forEach(n -> System.out.println(n));
    orderManagement.getOrderNumbers().forEach(on -> System.out.println(on));
  }

  private static List<UserOrder> getUserOrderFromAPI() {
    ArrayList<UserOrder> userOrders = new ArrayList<>();
    userOrders.add(new UserOrder("A", "A001"));
    userOrders.add(new UserOrder("B", "B001"));
    userOrders.add(new UserOrder("C", "C001"));
    return userOrders;
  }
}

@Getter
@NoArgsConstructor
@AllArgsConstructor
class UserOrder {
  private String username;
  private String orderNumber;
}
