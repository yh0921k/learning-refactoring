package book.chapter01;

import book.chapter01.dto.Invoice;
import book.chapter01.dto.Invoice.Performance;
import book.chapter01.dto.Play;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

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
  }
}
