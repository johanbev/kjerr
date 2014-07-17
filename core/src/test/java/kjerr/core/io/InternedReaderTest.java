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
    assertEquals((int) seq1.getPoint(0, 0), words.intern("The"));
    assertEquals((int) seq1.getPoint(0, 1), words.intern("cat"));
    assertEquals((int) seq1.getPoint(0, 2), words.intern("in"));
    assertEquals((int) seq1.getPoint(0, 3), words.intern("the"));
    assertEquals((int) seq1.getPoint(0, 4), words.intern("hat"));
    assertEquals((int) seq1.getPoint(0, 5), words.intern("."));

    assertEquals((int) seq1.getColumn(1).length, 6);
    assertEquals((int) seq1.getPoint(1, 0), tags.intern("DT"));
    assertEquals((int) seq1.getPoint(1, 1), tags.intern("NP"));
    assertEquals((int) seq1.getPoint(1, 2), tags.intern("P"));
    assertEquals((int) seq1.getPoint(1, 3), tags.intern("DT"));
    assertEquals((int) seq1.getPoint(1, 4), tags.intern("NP"));
    assertEquals((int) seq1.getPoint(1, 5), tags.intern("."));

    assertEquals((int) seq2.getColumn(0).length, 10);
    assertEquals((int) seq2.getPoint(0, 0), words.intern("The"));
    assertEquals((int) seq2.getPoint(0, 1), words.intern("quick"));
    assertEquals((int) seq2.getPoint(0, 2), words.intern("brown"));
    assertEquals((int) seq2.getPoint(0, 3), words.intern("fox"));
    assertEquals((int) seq2.getPoint(0, 4), words.intern("jumps"));
    assertEquals((int) seq2.getPoint(0, 5), words.intern("over"));
    assertEquals((int) seq2.getPoint(0, 6), words.intern("the"));
    assertEquals((int) seq2.getPoint(0, 7), words.intern("lazy"));
    assertEquals((int) seq2.getPoint(0, 8), words.intern("dog"));
    assertEquals((int) seq2.getPoint(0, 9), words.intern("."));

    assertEquals((int) seq2.getColumn(1).length, 10);
    assertEquals((int) seq2.getPoint(1, 0), tags.intern("DT"));
    assertEquals((int) seq2.getPoint(1, 1), tags.intern("AD"));
    assertEquals((int) seq2.getPoint(1, 2), tags.intern("AD"));
    assertEquals((int) seq2.getPoint(1, 3), tags.intern("NP"));
    assertEquals((int) seq2.getPoint(1, 4), tags.intern("VP"));
    assertEquals((int) seq2.getPoint(1, 5), tags.intern("P"));
    assertEquals((int) seq2.getPoint(1, 6), tags.intern("DT"));
    assertEquals((int) seq2.getPoint(1, 7), tags.intern("AD"));
    assertEquals((int) seq2.getPoint(1, 8), tags.intern("NP"));
    assertEquals((int) seq2.getPoint(1, 9), tags.intern("."));
  }
}
