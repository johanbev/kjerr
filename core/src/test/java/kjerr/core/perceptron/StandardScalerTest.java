package kjerr.core.perceptron;

import kjerr.core.io.Data;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class StandardScalerTest {

    @Test
    public void testFit() throws Exception {
        Data x = new Data(new double[][]{{1, 2}, {2, 3}, {3, 4}});
        StandardScaler scaler = new StandardScaler();
        Data newX = scaler.fitAndTransform(x);

        assertEquals(-1.5, newX.get(0, 0), 0.01);
        assertEquals(-1.5, newX.get(0, 1), 0.01);
        assertEquals(0, newX.get(1, 0), 0.01);
        assertEquals(0, newX.get(1, 1), 0.01);
        assertEquals(1.5, newX.get(2, 0), 0.01);
        assertEquals(1.5, newX.get(2, 1), 0.01);
    }
}