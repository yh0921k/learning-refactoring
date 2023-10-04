package book.chapter06;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Application6_5 {
  public static void main(String[] args) {
    List<Customer> someCustomers = new LinkedList<>();
    someCustomers.add(new Customer(new Address("MA")));
    someCustomers.add(new Customer(new Address("RI")));
    someCustomers.add(new Customer(new Address("ME")));
    someCustomers.add(new Customer(new Address("MA")));
    someCustomers.add(new Customer(new Address("CT")));
    someCustomers.add(new Customer(new Address("NO")));
    someCustomers.add(new Customer(new Address("NO")));
    someCustomers.add(new Customer(new Address("NO")));
    someCustomers.add(new Customer(new Address("NO")));
    someCustomers.add(new Customer(new Address("NO")));

    long numberOfNewEngland = someCustomers.stream().filter(Application6_5::isNewEngland).count();
    System.out.println("numberOfNewEngland = " + numberOfNewEngland);
  }

  public static boolean isNewEngland(Customer customer) {
    String stateCode = customer.getAddress().getState();

    return refactoredIsNewEngland(stateCode);
  }

  public static boolean refactoredIsNewEngland(String stateCode) {
    return Arrays.stream(new String[] {"MA", "CT", "ME", "VT", "NH", "RI"})
        .anyMatch(code -> code.equals(stateCode));
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
