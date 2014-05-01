package kjerr.core.lm;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertArrayEquals;

public class NGramModelTest {
  @Test
  public void testGenerateNGrams() throws Exception {
    NGramModel ngm = new NGramModel(3);

    List<int[]> res = ngm.generateNGrams(new int[]{1, 2, 3, 4, 5, 6});

    // 1 2 3 4 5 6

    // 1 2 3
    // 2 3 4
    // 3 4 5
    // 4 5 6
    // 5 6
    // 6

    assertArrayEquals(res.get(0), new int[]{1, 2, 3});
    assertArrayEquals(res.get(1), new int[]{2, 3, 4});
    assertArrayEquals(res.get(2), new int[]{3, 4, 5});
    assertArrayEquals(res.get(3), new int[]{4, 5, 6});
    assertArrayEquals(res.get(4), new int[]{5, 6});
    assertArrayEquals(res.get(5), new int[]{6});
  }

  @Test
  public void testGenerateNGramsSmallSentence() throws Exception {
    NGramModel ngm = new NGramModel(3);

    List<int[]> res = ngm.generateNGrams(new int[]{1, 2});

    // 1 2

    // 1 2
    // 2

    assertArrayEquals(res.get(0), new int[]{1, 2});
    assertArrayEquals(res.get(1), new int[]{2});
  }

  @Test
  public void testGenerateNGramsSingleWord() throws Exception {
    NGramModel ngm = new NGramModel(3);

    List<int[]> res = ngm.generateNGrams(new int[]{1});

    // 1

    // 1

    assertArrayEquals(res.get(0), new int[]{1});
  }
}
