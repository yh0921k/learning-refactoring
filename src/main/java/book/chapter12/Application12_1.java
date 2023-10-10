package book.chapter12;

public class Application12_1 {
  public static void main(String[] args) {}
}

class Party {}

class Employee extends Party {
  int monthlyCost;

  public int annualCost() {
    return monthlyCost * 12;
  }
}

class Department extends Party {
  int monthlyCost;

  public int annualCost() {
    return monthlyCost * 12;
  }
}
