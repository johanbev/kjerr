package kjerr.core.io;

import kjerr.core.Sequence;
import kjerr.core.StringInterner;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;



public class TTReaderTest {


    @Test
    public void test1Col() throws IOException {

        StringInterner si = new StringInterner();
        TTReader ttr = new TTReader(new StringInterner[]{si}, 1,
                getClass().getResourceAsStream("/Test.tt"));

        Sequence seq1 = ttr.getSequence();

        assertEquals(seq1.getColumn(0).length, 6);
        assertEquals(seq1.getPoint(0,0), si.encode("The"));
        assertEquals(seq1.getPoint(0,1), si.encode("cat"));
        assertEquals(seq1.getPoint(0,2), si.encode("in"));
        assertEquals(seq1.getPoint(0,3), si.encode("the"));
        assertEquals(seq1.getPoint(0,4), si.encode("hat"));
        assertEquals(seq1.getPoint(0,5), si.encode("."));

        seq1 = ttr.getSequence();

        assertEquals(seq1.getPoint(0,0), si.encode("The"));
        assertEquals(seq1.getPoint(0,1), si.encode("quick"));
        assertEquals(seq1.getPoint(0,2), si.encode("brown"));
        assertEquals(seq1.getPoint(0,3), si.encode("fox"));
        assertEquals(seq1.getPoint(0,4), si.encode("jumps"));
        assertEquals(seq1.getPoint(0,5), si.encode("over"));
        assertEquals(seq1.getPoint(0,6), si.encode("the"));
        assertEquals(seq1.getPoint(0,7), si.encode("lazy"));
        assertEquals(seq1.getPoint(0,8), si.encode("dog"));
        assertEquals(seq1.getPoint(0,9), si.encode("."));
    }

    @Test
    public void test2Col() throws IOException {

        StringInterner words = new StringInterner();
        StringInterner tags = new StringInterner();

        TTReader ttr = new TTReader(new StringInterner[]{words,tags }, 2,
                getClass().getResourceAsStream("/Test2Col.tt"));

        Sequence seq1 = ttr.getSequence();

        assertEquals(seq1.getColumn(0).length, 6);
        assertEquals(seq1.getPoint(0, 0), words.encode("The"));
        assertEquals(seq1.getPoint(0, 1), words.encode("cat"));
        assertEquals(seq1.getPoint(0, 2), words.encode("in"));
        assertEquals(seq1.getPoint(0, 3), words.encode("the"));
        assertEquals(seq1.getPoint(0, 4), words.encode("hat"));
        assertEquals(seq1.getPoint(0, 5), words.encode("."));

        assertEquals(seq1.getColumn(1).length, 6);
        assertEquals(seq1.getPoint(1, 0), tags.encode("DT"));
        assertEquals(seq1.getPoint(1, 1), tags.encode("NP"));
        assertEquals(seq1.getPoint(1, 2), tags.encode("P"));
        assertEquals(seq1.getPoint(1, 3), tags.encode("DT"));
        assertEquals(seq1.getPoint(1, 4), tags.encode("NP"));
        assertEquals(seq1.getPoint(1, 5), tags.encode("."));

        seq1 = ttr.getSequence();

        assertEquals(seq1.getColumn(0).length, 10);
        assertEquals(seq1.getPoint(0,0), words.encode("The"));
        assertEquals(seq1.getPoint(0,1), words.encode("quick"));
        assertEquals(seq1.getPoint(0,2), words.encode("brown"));
        assertEquals(seq1.getPoint(0,3), words.encode("fox"));
        assertEquals(seq1.getPoint(0,4), words.encode("jumps"));
        assertEquals(seq1.getPoint(0,5), words.encode("over"));
        assertEquals(seq1.getPoint(0,6), words.encode("the"));
        assertEquals(seq1.getPoint(0,7), words.encode("lazy"));
        assertEquals(seq1.getPoint(0,8), words.encode("dog"));
        assertEquals(seq1.getPoint(0,9), words.encode("."));

        assertEquals(seq1.getColumn(1).length, 10);
        assertEquals(seq1.getPoint(1,0), tags.encode("DT"));
        assertEquals(seq1.getPoint(1,1), tags.encode("AD"));
        assertEquals(seq1.getPoint(1,2), tags.encode("AD"));
        assertEquals(seq1.getPoint(1,3), tags.encode("NP"));
        assertEquals(seq1.getPoint(1,4), tags.encode("VP"));
        assertEquals(seq1.getPoint(1,5), tags.encode("P"));
        assertEquals(seq1.getPoint(1,6), tags.encode("DT"));
        assertEquals(seq1.getPoint(1,7), tags.encode("AD"));
        assertEquals(seq1.getPoint(1,8), tags.encode("NP"));
        assertEquals(seq1.getPoint(1,9), tags.encode("."));
    }
}
