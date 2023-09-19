package book.chapter01;

import book.chapter01.domain.Statement;
import book.chapter01.dto.Invoice;
import book.chapter01.dto.Play;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Chapter01Application {
  public static <T> T readJson(String filepath, Class<T> valueType) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      return objectMapper.readValue(new File(filepath), valueType);
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) throws Exception {
    Play[] plays = readJson("resource/plays.json", Play[].class);
    Invoice[] invoices = readJson("resource/invoices.json", Invoice[].class);

    Statement statement = new Statement();
    String plainText = statement.readPlainText(invoices[0], plays);

    System.out.println(plainText);
  }
}
