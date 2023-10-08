package book.chapter08;

public class Application8_6 {
  public static void main(String[] args) {
    PricingPlan pricingPlan = retrievePricingPlan();
    Order order = retrieveOrder();
    int baseCharge = pricingPlan.base;
    int charge;
    int chargePerUnit = pricingPlan.unit;
    int units = order.units;
    int discount;
    charge = baseCharge + units * chargePerUnit;
    int discountableUnits = Math.max(units - pricingPlan.discountThreshold, 0);
    discount = (int) (discountableUnits * pricingPlan.discountFactor);
    if (order.isRepeat) discount += 20;
    charge = charge - discount;
    chargeOrder(charge);
  }

  private static void chargeOrder(int charge) {
    System.out.println("Application8_6.chargeOrder");
  }

  private static Order retrieveOrder() {
    System.out.println("Application8_6.retrieveOrder");
    return new Order();
  }

  private static PricingPlan retrievePricingPlan() {
    System.out.println("Application8_6.retrievePricingPlan");
    return new PricingPlan();
  }
}

class Order {
  int units;
  boolean isRepeat;
}

class PricingPlan {
  int unit;
  double discountFactor;
  int base;
  int discountThreshold;
}
