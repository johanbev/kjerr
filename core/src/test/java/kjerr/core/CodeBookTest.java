package kjerr.core;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotSame;

public class CodeBookTest {
    @Test
    public void testCodeBook() throws Exception {
        StringInterner stringInterner = new StringInterner();

        int hello = stringInterner.intern("hello");
        int where = stringInterner.intern("where");
        int is = stringInterner.intern("is");

        assertEquals(hello, stringInterner.encode("hello"));
        assertEquals(where, stringInterner.encode("where"));
        assertEquals(is, stringInterner.encode("is"));

        assertEquals(hello, stringInterner.intern("hello"));
        assertEquals(where, stringInterner.intern("where"));
        assertEquals(is, stringInterner.intern("is"));

        assertNotSame(hello, stringInterner.intern("not hello"));
        assertNotSame(where, stringInterner.intern("not where"));
        assertNotSame(is, stringInterner.intern("not is"));
    }
}
