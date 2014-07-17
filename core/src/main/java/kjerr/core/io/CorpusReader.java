package kjerr.core.io;

import kjerr.core.Sequence;

import java.io.IOException;

public interface CorpusReader<W, T> {
  Sequence<W, T> getSequence() throws IOException;
}
