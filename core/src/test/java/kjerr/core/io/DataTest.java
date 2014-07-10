package kjerr.core.io;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.*;

public class DataTest {
    @Test
    public void testIsDoubleArray() {
        List<Double[]> doubleList = Arrays.asList(new Double[]{0.1, 0.2}, new Double[]{0.3, 0.4});
        List<Float[]> floatList = Arrays.asList(new Float[]{0.1f, 0.2f}, new Float[]{0.3f, 0.4f});
        List<String> stringList = Arrays.asList("ba", "foo");

        assertTrue(Data.containsDoubleArray(doubleList));
        assertFalse(Data.containsDoubleArray(floatList));
        assertFalse(Data.containsDoubleArray(stringList));
    }

    @Test
    public void testToDoubleArray() {
        List<Double[]> input = Arrays.asList(new Double[]{0.1, 0.2}, new Double[]{0.3, 0.4});
        double[][] expected = new double[][]{{0.1, 0.2}, {0.3, 0.4}};

        assertEquals(expected[0][0], input.get(0)[0]);
        assertEquals(expected[0][1], input.get(0)[1]);
        assertEquals(expected[1][0], input.get(1)[0]);
        assertEquals(expected[1][1], input.get(1)[1]);
    }
}