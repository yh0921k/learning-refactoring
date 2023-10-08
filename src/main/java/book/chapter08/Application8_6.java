package book.chapter08;

import java.util.LinkedList;
import java.util.Queue;

public class Application8_6 {
  public static void main(String[] args) {
    Queue<String> availableResources = new LinkedList<>();
    Queue<String> allocatedResources = new LinkedList<>();
    String result;

    if(availableResources.size() == 0) {
      result = "create result";
      allocatedResources.add(result);
    } else {
      result = availableResources.remove();
      allocatedResources.add(result);
    }
  }
}