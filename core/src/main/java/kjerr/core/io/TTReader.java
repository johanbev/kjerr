package kjerr.core.io;

import kjerr.core.Sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class TTReader implements CorpusReader<String> {
  BufferedReader br;
  Pattern ttTokenizer = Pattern.compile("\\s+");
  InputStream stream;
  private int columns;

  public TTReader(int columns, InputStream stream) throws IOException {
    this.columns = columns;
    this.stream = stream;

    prepareReader();
  }

  private void prepareReader() throws IOException {
    br = new BufferedReader(new InputStreamReader(stream));
  }

  @Override
  public Sequence<String> getSequence() throws IOException {
    List<List<String>> buffer = new ArrayList<>(20);

    for (int i = 0; i < columns; i++) {
      buffer.add(i, new ArrayList<>(20));
    }

    for (int i = 0; i < columns; i++) {
      buffer.get(i).clear();
    }

    String s = br.readLine();

    if (s == null) { // EOF
      return null;
    }

    while (s != null && !s.isEmpty()) {
      int i = -1;
      String[] splits = ttTokenizer.split(s);
      for (int j = 0; j < columns; j++) {
        i++;
        buffer.get(i).add(splits[j]);
      }

      s = br.readLine();
    }

    if (buffer.get(0).size() == 0) { // No input read, try again
      return getSequence();
    }

    String[][] seq = new String[columns][];

    for (int i = 0; i < columns; i++) {
      seq[i] = buffer.get(i).toArray(new String[0]);
    }

    return new Sequence<>(seq);
  }

  @Override
  public int getColumns() {
    return columns;
  }
}
