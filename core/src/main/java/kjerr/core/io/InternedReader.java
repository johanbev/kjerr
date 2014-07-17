package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;

import java.io.IOException;

public class InternedReader implements CorpusReader<Integer, Integer> {
  private TTReader reader;

  private StringInterner wordInterner;
  private StringInterner tagInterner;

  public InternedReader(TTReader reader) {
    this.reader = reader;

    wordInterner = new StringInterner();
    tagInterner = new StringInterner();
  }

  public StringInterner getWordInterner() {
    return wordInterner;
  }

  public StringInterner getTagInterner() {
    return tagInterner;
  }

  @Override
  public Sequence<Integer, Integer> getSequence() throws IOException {
    Sequence<String, String> innerSeq = reader.getSequence();

    if(innerSeq == null)
      return null;

    Integer[] words = new Integer[innerSeq.size()];
    Integer[] tags = new Integer[innerSeq.size()];
    for (int i = 0; i < innerSeq.size(); i++) {
      words[i] = wordInterner.intern(innerSeq.getWord(i));
      tags[i] = tagInterner.intern(innerSeq.getTag(i));
    }

    return new Sequence<>(words, tags);
  }
}
