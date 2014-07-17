package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;

import java.io.IOException;

public class InternedReader {
  private TTReader reader;

  private StringInterner[] interners;

  public InternedReader(TTReader reader) {
    this.reader = reader;
    this.interners = new StringInterner[reader.getColumns()];

    for (int i = 0; i < reader.getColumns(); i++) {
      this.interners[i] = new StringInterner();
    }
  }

  public StringInterner[] getInterners() {
    return interners;
  }

  public Sequence<Integer> getSequence() throws IOException {
    Sequence<String> innerSeq = reader.getSequence();

    if(innerSeq == null)
      return null;

    Integer[][] seq = new Integer[reader.getColumns()][innerSeq.size()];

    for (int i = 0; i < reader.getColumns(); i++) {
      for (int j = 0; j < innerSeq.size(); j++) {
        seq[i][j] = interners[i].intern(innerSeq.getPoint(i, j));
      }
    }

    return new Sequence<>(seq);
  }
}
