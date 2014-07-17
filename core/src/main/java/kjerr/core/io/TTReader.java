package kjerr.core.io;

import kjerr.core.Sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class TTReader implements CorpusReader<String, String> {
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
  public Sequence<String, String> getSequence() throws IOException {
    List<String> words = new ArrayList<>(20);
    List<String> tags = new ArrayList<>(20);

    String s = br.readLine();

    if (s == null) { // EOF
      return null;
    }

    while (s != null && !s.isEmpty()) {
      int i = -1;
      String[] splits = ttTokenizer.split(s);

      if (splits.length != 2) {
        throw new IOException();
      }

      words.add(splits[0].trim());
      tags.add(splits[1].trim());

      s = br.readLine();
    }

    if (words.size() == 0) { // No input read, try again
      return getSequence();
    }

    String[][] seq = new String[columns][];

    return new Sequence<>(words.toArray(new String[words.size()]), tags.toArray(new String[tags.size()]));
  }
}
