package kjerr.core;

public class Sequence<W, T> {
  W[] words;
  T[] tags;

  public Sequence(W[] words, T[] tags) {
    if (words.length != tags.length) {
      throw new IllegalArgumentException();
    }

    this.words = words;
    this.tags = tags;

//    this.words = (W[])(new Object[words.length]);
//    System.arraycopy(words, 0, this.words, 0, words.length);
//    this.tags = (T[])(new Object[tags.length]);
//    System.arraycopy(tags, 0, this.tags, 0, tags.length);
  }

  public W[] getWords() {
    return words;
  }

  public W getWord(int i) {
    return words[i];
  }

  public T[] getTags() {
    return tags;
  }

  public T getTag(int i) {
    return tags[i];
  }

  public int size() {
    return words.length;
  }
}
