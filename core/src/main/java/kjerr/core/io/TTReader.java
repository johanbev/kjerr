package kjerr.core.io;

import kjerr.core.Sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class TTReader {
  public TTReader(int columns, InputStream stream) throws IOException {
    this.columns = columns;
    this.stream = stream;

    prepareReader();
  }

  private int columns;

  BufferedReader br;

  Pattern ttTokenizer = Pattern.compile("\\s+");

  private void prepareReader() throws IOException {
    br = new BufferedReader(new InputStreamReader(stream));
  }

  public Sequence<String> getSequence() throws IOException {
    List<List<String>> buffer = new ArrayList<>(20);

    for (int i = 0; i < columns; i++) {
      buffer.add(i, new ArrayList<>(20));
    }

    for (int i = 0; i < columns; i++) {
      buffer.get(i).clear();
    }

    String s;
    while ((s = br.readLine()) != null && !s.isEmpty()) {
      int i = -1;
      String[] splits = ttTokenizer.split(s);
      for (int j = 0; j < columns; j++) {
        i++;
        buffer.get(i).add(splits[j]);
      }
    }

    String[][] seq = new String[columns][];

    for (int i = 0; i < columns; i++) {
      seq[i] = buffer.get(i).toArray(new String[0]);
    }

    return new Sequence<>(seq);
  }

  InputStream stream;

  public int getColumns() {
    return columns;
  }
}
