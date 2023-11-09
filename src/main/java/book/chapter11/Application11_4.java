package book.chapter11;

public class Application11_4 {
  public static void main(String[] args) throws Exception {
    HeatingPlan heatingPlan = new HeatingPlan(new Range(18, 22));
    Room room = new Room(new Range(19, 25));

    int low = room.daysTempRange.low;
    int high = room.daysTempRange.high;

    if (!heatingPlan.withinRange(low, high)) {
      throw new Exception("방 온도가 지정 범위를 벗어났습니다.");
    }
  }
}

class HeatingPlan {
  Range temperatureRange;

  public HeatingPlan(Range temperatureRange) {
    this.temperatureRange = temperatureRange;
  }

  public boolean withinRange(int bottom, int top) {
    return (bottom >= temperatureRange.low) && (top <= temperatureRange.high);
  }
}

class Room {
  Range daysTempRange;

  public Room(Range daysTempRange) {
    this.daysTempRange = daysTempRange;
  }
}

class Range {
  int low;
  int high;

  public Range(int low, int high) {
    this.low = low;
    this.high = high;
  }
}