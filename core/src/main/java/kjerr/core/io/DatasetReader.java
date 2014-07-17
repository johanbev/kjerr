package kjerr.core.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatasetReader {
  public static DataSet loadIris() throws IOException {
    InputStream is = DatasetReader.class.getResourceAsStream("/iris.data");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    List<Double[]> data = new ArrayList<>();
    List<String> target = new ArrayList<>();

    String line;

    int width = -1;

    while ((line = br.readLine()) != null) {
      String[] tokens = line.trim().split(",");

      if (width == -1) {
        width = tokens.length;
      } else if (width != tokens.length) {
        throw new IOException();
      }

      Double[] x = new Double[tokens.length - 1];
      String y = tokens[tokens.length - 1];

      for (int i = 0; i < tokens.length - 1; i++) {
        x[i] = Double.parseDouble(tokens[i]);
      }

      data.add(x);
      target.add(y);
    }

    if (width == -1) {
      throw new IOException();
    }

    br.close();

    return new DataSet(new Data(data), new Data(target), new HashMap<>());
  }

  public enum DatasetKey {
    DATA, TARGET
  }
}
