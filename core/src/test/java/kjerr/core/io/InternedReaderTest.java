package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class InternedReaderTest {

  @Test
  public void testGetSequence() throws Exception {
    InternedReader t = new InternedReader(new TTReader(2, getClass().getResourceAsStream("/Test2Col.tt")));

    Sequence<Integer, Integer> seq1 = t.getSequence();
    Sequence<Integer, Integer> seq2 = t.getSequence();

    StringInterner wordInterner = t.getWordInterner();
    StringInterner tagInterner = t.getTagInterner();

    assertEquals(13, wordInterner.size());
    assertEquals(6, tagInterner.size());

    assertEquals(seq1.getWords().length, 6);
    assertEquals((int) seq1.getWord(0), wordInterner.encode("The"));
    assertEquals((int) seq1.getWord(1), wordInterner.encode("cat"));
    assertEquals((int) seq1.getWord(2), wordInterner.encode("in"));
    assertEquals((int) seq1.getWord(3), wordInterner.encode("the"));
    assertEquals((int) seq1.getWord(4), wordInterner.encode("hat"));
    assertEquals((int) seq1.getWord(5), wordInterner.encode("."));

    assertEquals((int) seq1.getTags().length, 6);
    assertEquals((int) seq1.getTag(0), tagInterner.encode("DT"));
    assertEquals((int) seq1.getTag(1), tagInterner.encode("NP"));
    assertEquals((int) seq1.getTag(2), tagInterner.encode("P"));
    assertEquals((int) seq1.getTag(3), tagInterner.encode("DT"));
    assertEquals((int) seq1.getTag(4), tagInterner.encode("NP"));
    assertEquals((int) seq1.getTag(5), tagInterner.encode("."));

    assertEquals((int) seq2.getWords().length, 10);
    assertEquals((int) seq2.getWord(0), wordInterner.encode("The"));
    assertEquals((int) seq2.getWord(1), wordInterner.encode("quick"));
    assertEquals((int) seq2.getWord(2), wordInterner.encode("brown"));
    assertEquals((int) seq2.getWord(3), wordInterner.encode("fox"));
    assertEquals((int) seq2.getWord(4), wordInterner.encode("jumps"));
    assertEquals((int) seq2.getWord(5), wordInterner.encode("over"));
    assertEquals((int) seq2.getWord(6), wordInterner.encode("the"));
    assertEquals((int) seq2.getWord(7), wordInterner.encode("lazy"));
    assertEquals((int) seq2.getWord(8), wordInterner.encode("dog"));
    assertEquals((int) seq2.getWord(9), wordInterner.encode("."));

    assertEquals((int) seq2.getTags().length, 10);
    assertEquals((int) seq2.getTag(0), tagInterner.encode("DT"));
    assertEquals((int) seq2.getTag(1), tagInterner.encode("AD"));
    assertEquals((int) seq2.getTag(2), tagInterner.encode("AD"));
    assertEquals((int) seq2.getTag(3), tagInterner.encode("NP"));
    assertEquals((int) seq2.getTag(4), tagInterner.encode("VP"));
    assertEquals((int) seq2.getTag(5), tagInterner.encode("P"));
    assertEquals((int) seq2.getTag(6), tagInterner.encode("DT"));
    assertEquals((int) seq2.getTag(7), tagInterner.encode("AD"));
    assertEquals((int) seq2.getTag(8), tagInterner.encode("NP"));
    assertEquals((int) seq2.getTag(9), tagInterner.encode("."));
  }
}
