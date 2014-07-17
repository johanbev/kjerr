package kjerr.core;

public class Sequence<T> {
  int rank;

  T[][] file;

  public Sequence(T[][] file) {
    this.file = file;
  }

  public T getPoint(int x, int y) {
    return file[x][y];
  }

  public T[] getColumn(int x) {
    return file[x];
  }

  public int size() {
    return file[0].length;
  }
}
