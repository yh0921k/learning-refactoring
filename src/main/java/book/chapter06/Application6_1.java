package book.chapter06;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Application6_1 {
  public static void main(String[] args){
    FinancialObligation financialObligation = new FinancialObligation();
    financialObligation.printOwing(createInvoice());
  }

  public static Invoice createInvoice() {
    Invoice invoice = new Invoice();
    invoice.customer = "yh0921k";
    invoice.orders.add(new Order(1000));
    invoice.orders.add(new Order(2000));
    invoice.orders.add(new Order(3000));
    invoice.orders.add(new Order(4000));
    invoice.orders.add(new Order(5000));

    return invoice;
  }
}

class FinancialObligation {
  public void printOwing(Invoice invoice) {

    printBanner();

    // 미해결 채무 (outstanding) 을 계산한다.
    int outstanding = 0;
    for (Order o : invoice.getOrders()) {
      outstanding += o.amount;
    }

    recordDueDate(invoice);
    printDetails(invoice, outstanding);
  }

  private int calculateOutstanding(Invoice invoice) {
    // 미해결 채무 (outstanding) 을 계산한다.
    int outstanding = 0;
    for (Order o : invoice.getOrders()) {
      outstanding += o.amount;
    }
    return outstanding;
  }

  private void recordDueDate(Invoice invoice) {
    // 마감일(dueDate) 을 기록한다.
    LocalDateTime today = Clock.today();
    invoice.dueDate = today.plusDays(30);
  }

  private void printDetails(Invoice invoice, int outstanding) {
    // 세부 사항을 출력한다.
    System.out.printf("고객명: %s%n", invoice.customer);
    System.out.printf("채무액: %d%n", outstanding);
    System.out.printf("마감일: %s%n", invoice.dueDate);
  }

  private void printBanner() {
    System.out.println("*****************");
    System.out.println("**** 고객 채무 ****");
    System.out.println("*****************");
  }
}

class Invoice {
  protected List<Order> orders = new ArrayList<>();
  protected LocalDateTime dueDate;
  protected String customer;

  public List<Order> getOrders() {
    return orders;
  }
}

class Order {
  protected int amount;

  public Order(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }
}

class Clock {
  public static LocalDateTime today() {
    return LocalDateTime.now();
  }
}