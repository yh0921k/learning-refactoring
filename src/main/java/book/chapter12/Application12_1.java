package book.chapter12;

public class Application12_1 {
  public static void main(String[] args) {}
}

class Party {
  int monthlyCost;

  public int annualCost() {
    return monthlyCost * 12;
  }
}

class Employee extends Party {}

class Department extends Party {}
