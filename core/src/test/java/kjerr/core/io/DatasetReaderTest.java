package kjerr.core.io;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class DatasetReaderTest {

    @Test
    public void testLoadIris() throws Exception {
        DataSet irisDataset = DatasetReader.loadIris();

        Data x = irisDataset.X();
        assertEquals(150, x.size());
        assertEquals(4, ((double[])x.get(0)).length);
        assertEquals(5.1, ((double[])x.get(0))[0], 0.001);
        assertEquals(3.5, ((double[])x.get(0))[1], 0.001);
        assertEquals(1.4, x.get(0, 2), 0.001);
        assertEquals(0.2, x.get(0, 3), 0.001);

        Data y = irisDataset.Y();
        assertEquals(150, y.size());
        assertEquals("Iris-setosa", y.get(0));
    }
}