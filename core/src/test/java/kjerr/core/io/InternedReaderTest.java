package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class InternedReaderTest {

  @Test
  public void testGetSequence() throws Exception {
    InternedReader t = new InternedReader(new TTReader(2, getClass().getResourceAsStream("/Test2Col.tt")));

    Sequence<Integer> seq1 = t.getSequence();
    Sequence<Integer> seq2 = t.getSequence();

    StringInterner words = t.getInterners()[0];
    StringInterner tags = t.getInterners()[1];

    assertEquals(seq1.getColumn(0).length, 6);
    assertEquals((int)seq1.getPoint(0, 0), words.encode("The"));
    assertEquals((int)seq1.getPoint(0, 1), words.encode("cat"));
    assertEquals((int)seq1.getPoint(0, 2), words.encode("in"));
    assertEquals((int)seq1.getPoint(0, 3), words.encode("the"));
    assertEquals((int)seq1.getPoint(0, 4), words.encode("hat"));
    assertEquals((int)seq1.getPoint(0, 5), words.encode("."));

    assertEquals((int)seq1.getColumn(1).length, 6);
    assertEquals((int)seq1.getPoint(1, 0), tags.encode("DT"));
    assertEquals((int)seq1.getPoint(1, 1), tags.encode("NP"));
    assertEquals((int)seq1.getPoint(1, 2), tags.encode("P"));
    assertEquals((int)seq1.getPoint(1, 3), tags.encode("DT"));
    assertEquals((int)seq1.getPoint(1, 4), tags.encode("NP"));
    assertEquals((int)seq1.getPoint(1, 5), tags.encode("."));

    assertEquals((int)seq2.getColumn(0).length, 10);
    assertEquals((int)seq2.getPoint(0, 0), words.encode("The"));
    assertEquals((int)seq2.getPoint(0, 1), words.encode("quick"));
    assertEquals((int)seq2.getPoint(0, 2), words.encode("brown"));
    assertEquals((int)seq2.getPoint(0, 3), words.encode("fox"));
    assertEquals((int)seq2.getPoint(0, 4), words.encode("jumps"));
    assertEquals((int)seq2.getPoint(0, 5), words.encode("over"));
    assertEquals((int)seq2.getPoint(0, 6), words.encode("the"));
    assertEquals((int)seq2.getPoint(0, 7), words.encode("lazy"));
    assertEquals((int)seq2.getPoint(0, 8), words.encode("dog"));
    assertEquals((int)seq2.getPoint(0, 9), words.encode("."));

    assertEquals((int)seq2.getColumn(1).length, 10);
    assertEquals((int)seq2.getPoint(1, 0), tags.encode("DT"));
    assertEquals((int)seq2.getPoint(1, 1), tags.encode("AD"));
    assertEquals((int)seq2.getPoint(1, 2), tags.encode("AD"));
    assertEquals((int)seq2.getPoint(1, 3), tags.encode("NP"));
    assertEquals((int)seq2.getPoint(1, 4), tags.encode("VP"));
    assertEquals((int)seq2.getPoint(1, 5), tags.encode("P"));
    assertEquals((int)seq2.getPoint(1, 6), tags.encode("DT"));
    assertEquals((int)seq2.getPoint(1, 7), tags.encode("AD"));
    assertEquals((int)seq2.getPoint(1, 8), tags.encode("NP"));
    assertEquals((int)seq2.getPoint(1, 9), tags.encode("."));
  }
}