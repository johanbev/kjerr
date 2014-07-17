package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;


public class TTReaderTest {

  @Test
  public void test2Col() throws IOException {

    StringInterner words = new StringInterner();
    StringInterner tags = new StringInterner();

    TTReader ttr = new TTReader(2,
        getClass().getResourceAsStream("/Test2col.tt"));

    Sequence<String, String> seq1 = ttr.getSequence();

    assertEquals(seq1.getWords().length, 6);
    assertEquals(seq1.getWord(0), "The");
    assertEquals(seq1.getWord(1), "cat");
    assertEquals(seq1.getWord(2), "in");
    assertEquals(seq1.getWord(3), "the");
    assertEquals(seq1.getWord(4), "hat");
    assertEquals(seq1.getWord(5), ".");

    assertEquals(seq1.getTags().length, 6);
    assertEquals(seq1.getTag(0), "DT");
    assertEquals(seq1.getTag(1), "NP");
    assertEquals(seq1.getTag(2), "P");
    assertEquals(seq1.getTag(3), "DT");
    assertEquals(seq1.getTag(4), "NP");
    assertEquals(seq1.getTag(5), ".");

    seq1 = ttr.getSequence();

    assertEquals(seq1.getTags().length, 10);
    assertEquals(seq1.getWord(0), "The");
    assertEquals(seq1.getWord(1), "quick");
    assertEquals(seq1.getWord(2), "brown");
    assertEquals(seq1.getWord(3), "fox");
    assertEquals(seq1.getWord(4), "jumps");
    assertEquals(seq1.getWord(5), "over");
    assertEquals(seq1.getWord(6), "the");
    assertEquals(seq1.getWord(7), "lazy");
    assertEquals(seq1.getWord(8), "dog");
    assertEquals(seq1.getWord(9), ".");

    assertEquals(seq1.getTags().length, 10);
    assertEquals(seq1.getTag(0), "DT");
    assertEquals(seq1.getTag(1), "AD");
    assertEquals(seq1.getTag(2), "AD");
    assertEquals(seq1.getTag(3), "NP");
    assertEquals(seq1.getTag(4), "VP");
    assertEquals(seq1.getTag(5), "P");
    assertEquals(seq1.getTag(6), "DT");
    assertEquals(seq1.getTag(7), "AD");
    assertEquals(seq1.getTag(8), "NP");
    assertEquals(seq1.getTag(9), ".");

    assertNull(ttr.getSequence());
  }
}
