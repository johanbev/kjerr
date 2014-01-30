package kjerr.core.lm;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class NGramModelTest {
    @Test
    public void testGenerateNGrams() throws Exception {
        NGramModel ngm = new NGramModel(3);

        List<int[]> res = ngm.generateNGrams(new int[] {1,2,3,4,5,6});

        // 1 2 3 4 5 6
        // 1 2 3
        // 2 3 4
        // 3 4 5
        // 4 5 6
        // 5 6
        // 6

        assertEquals(res.get(1), new int[]{1,2,3});

    }
}
