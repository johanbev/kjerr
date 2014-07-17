package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;


public class TTReaderTest {


  @Test
  public void test1Col() throws IOException {

    StringInterner si = new StringInterner();
    TTReader ttr = new TTReader(1,
        getClass().getResourceAsStream("/Test.tt"));

    Sequence<String> seq1 = ttr.getSequence();

    assertEquals(seq1.getColumn(0).length, 6);
    assertEquals(seq1.getPoint(0, 0), "The");
    assertEquals(seq1.getPoint(0, 1), "cat");
    assertEquals(seq1.getPoint(0, 2), "in");
    assertEquals(seq1.getPoint(0, 3), "the");
    assertEquals(seq1.getPoint(0, 4), "hat");
    assertEquals(seq1.getPoint(0, 5), ".");

    seq1 = ttr.getSequence();

    assertEquals(seq1.getPoint(0, 0), "The");
    assertEquals(seq1.getPoint(0, 1), "quick");
    assertEquals(seq1.getPoint(0, 2), "brown");
    assertEquals(seq1.getPoint(0, 3), "fox");
    assertEquals(seq1.getPoint(0, 4), "jumps");
    assertEquals(seq1.getPoint(0, 5), "over");
    assertEquals(seq1.getPoint(0, 6), "the");
    assertEquals(seq1.getPoint(0, 7), "lazy");
    assertEquals(seq1.getPoint(0, 8), "dog");
    assertEquals(seq1.getPoint(0, 9), ".");

    assertNull(ttr.getSequence());
  }

  @Test
  public void test2Col() throws IOException {

    StringInterner words = new StringInterner();
    StringInterner tags = new StringInterner();

    TTReader ttr = new TTReader(2,
        getClass().getResourceAsStream("/Test2col.tt"));

    Sequence<String> seq1 = ttr.getSequence();

    assertEquals(seq1.getColumn(0).length, 6);
    assertEquals(seq1.getPoint(0, 0), "The");
    assertEquals(seq1.getPoint(0, 1), "cat");
    assertEquals(seq1.getPoint(0, 2), "in");
    assertEquals(seq1.getPoint(0, 3), "the");
    assertEquals(seq1.getPoint(0, 4), "hat");
    assertEquals(seq1.getPoint(0, 5), ".");

    assertEquals(seq1.getColumn(1).length, 6);
    assertEquals(seq1.getPoint(1, 0), "DT");
    assertEquals(seq1.getPoint(1, 1), "NP");
    assertEquals(seq1.getPoint(1, 2), "P");
    assertEquals(seq1.getPoint(1, 3), "DT");
    assertEquals(seq1.getPoint(1, 4), "NP");
    assertEquals(seq1.getPoint(1, 5), ".");

    seq1 = ttr.getSequence();

    assertEquals(seq1.getColumn(0).length, 10);
    assertEquals(seq1.getPoint(0, 0), "The");
    assertEquals(seq1.getPoint(0, 1), "quick");
    assertEquals(seq1.getPoint(0, 2), "brown");
    assertEquals(seq1.getPoint(0, 3), "fox");
    assertEquals(seq1.getPoint(0, 4), "jumps");
    assertEquals(seq1.getPoint(0, 5), "over");
    assertEquals(seq1.getPoint(0, 6), "the");
    assertEquals(seq1.getPoint(0, 7), "lazy");
    assertEquals(seq1.getPoint(0, 8), "dog");
    assertEquals(seq1.getPoint(0, 9), ".");

    assertEquals(seq1.getColumn(1).length, 10);
    assertEquals(seq1.getPoint(1, 0), "DT");
    assertEquals(seq1.getPoint(1, 1), "AD");
    assertEquals(seq1.getPoint(1, 2), "AD");
    assertEquals(seq1.getPoint(1, 3), "NP");
    assertEquals(seq1.getPoint(1, 4), "VP");
    assertEquals(seq1.getPoint(1, 5), "P");
    assertEquals(seq1.getPoint(1, 6), "DT");
    assertEquals(seq1.getPoint(1, 7), "AD");
    assertEquals(seq1.getPoint(1, 8), "NP");
    assertEquals(seq1.getPoint(1, 9), ".");

    assertNull(ttr.getSequence());
  }
}
