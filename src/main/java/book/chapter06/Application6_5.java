package book.chapter06;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Application6_5 {
  public static void main(String[] args) {
    List<Customer> someCustomers = createCustomers();

    long numberOfNewEngland =
        someCustomers.stream()
            .filter(customer -> isNewEngland(customer.getAddress().getState()))
            .count();
    System.out.println("numberOfNewEngland = " + numberOfNewEngland);
  }

  private static List<Customer> createCustomers() {
    List<Customer> result = new LinkedList<>();
    result.add(new Customer(new Address("MA")));
    result.add(new Customer(new Address("RI")));
    result.add(new Customer(new Address("ME")));
    result.add(new Customer(new Address("MA")));
    result.add(new Customer(new Address("CT")));
    result.add(new Customer(new Address("NO")));
    result.add(new Customer(new Address("NO")));
    result.add(new Customer(new Address("NO")));
    result.add(new Customer(new Address("NO")));
    result.add(new Customer(new Address("NO")));
    return result;
  }

  public static boolean isNewEngland(String stateCode) {
    return Arrays.asList(new String[] {"MA", "CT", "ME", "VT", "NH", "RI"}).contains(stateCode);
  }
}

@Data
@AllArgsConstructor
class Customer {
  private Address address;
}

@Data
@AllArgsConstructor
class Address {
  private String state;
}
