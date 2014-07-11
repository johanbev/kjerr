package kjerr.core.io;

import kjerr.core.Sequence;

import java.io.IOException;

public interface CorpusReader<T> {
  Sequence<T> getSequence() throws IOException;

  int getColumns();
}
