package kjerr.core;

public class Sequence {
  int rank;

  int[][] file;

  public int getPoint(int x, int y) {
    return file[x][y];
  }

  public int[] getColumn(int x) {
    return file[x];
  }

  public Sequence(int rows, int columns) {
    file = new int[rows][columns];
  }

  public Sequence(int[][] file) {
    this.file = file;
  }
}
