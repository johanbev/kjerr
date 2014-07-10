package kjerr.core.perceptron;

import kjerr.core.io.Data;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class LabelBinarizerTest {

    @Test
    public void testTransform() throws Exception {
        LabelBinarizer bin = new LabelBinarizer();

        Data x = new Data(Arrays.asList("a", "b", "a", "a", "c"));

        bin.fit(x);
        List classes = bin.getClasses();

        assertEquals(3, classes.size());
        assertTrue(classes.contains("a"));
        assertTrue(classes.contains("b"));
        assertTrue(classes.contains("c"));

        int aPos = classes.indexOf("a");
        int bPos = classes.indexOf("b");
        int cPos = classes.indexOf("c");

        Data newX = bin.transform(x);

        assertEquals(1, IntStream.of((int[])newX.get(0)).sum());
        assertEquals(1, ((int[])newX.get(0))[aPos]);
        assertEquals(1, IntStream.of((int[])newX.get(1)).sum());
        assertEquals(1, ((int[])newX.get(1))[bPos]);
        assertEquals(1, IntStream.of((int[])newX.get(2)).sum());
        assertEquals(1, ((int[])newX.get(2))[aPos]);
        assertEquals(1, IntStream.of((int[])newX.get(3)).sum());
        assertEquals(1, ((int[])newX.get(3))[aPos]);
        assertEquals(1, IntStream.of((int[])newX.get(4)).sum());
        assertEquals(1, ((int[])newX.get(4))[cPos]);
    }
}