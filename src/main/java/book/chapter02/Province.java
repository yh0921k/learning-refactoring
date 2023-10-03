package book.chapter02;

import java.util.Comparator;
import java.util.List;

public class Province {
  private String name;
  private int totalProduction;
  private int demand;
  private int price;
  private List<Producer> producers;

  public Province(String name, int demand, int price) {
    this.name = name;
    this.totalProduction = 0;
    this.demand = demand;
    this.price = price;
  }

  public Province(String name, int demand, int price, List<Producer> producers) {
    this.name = name;
    this.totalProduction = 0;
    this.demand = demand;
    this.price = price;
    this.producers = producers;

    producers.forEach(producer -> {
      producer.setProvince(this);
      totalProduction += producer.getProduction();
    });
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTotalProduction() {
    return totalProduction;
  }

  public void setTotalProduction(int totalProduction) {
    this.totalProduction = totalProduction;
  }

  public int getDemand() {
    return demand;
  }

  public void setDemand(int demand) {
    this.demand = demand;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public List<Producer> getProducers() {
    return producers;
  }

  public void setProducers(List<Producer> producers) {
    this.producers = producers;
  }

  // 생산 부족분을 계산
  public int shortFall() {
    return this.demand - this.totalProduction;
  }

  // 수익을 계산하는 코드
  public int profit() {
    return demandValue() - demandCost();
  }

  public int demandValue() {
    return satisfiedDemand() * price;
  }

  public int satisfiedDemand() {
    return Math.min(demand, totalProduction);
  }

  public int demandCost() {
    int remainingDemand = demand;
    int result = 0;
    producers.sort(Comparator.comparingInt(producer -> producer.getCost()));

    for (Producer producer : producers) {
      int contribution = Math.min(remainingDemand, producer.getProduction());
      remainingDemand -= contribution;
      result += contribution * producer.getCost();
    }
    return result;
  }
}
