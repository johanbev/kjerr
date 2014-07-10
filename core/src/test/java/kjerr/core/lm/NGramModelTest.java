package kjerr.core.lm;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertArrayEquals;

public class NGramModelTest {
  @Test
  public void testGenerateNGrams() throws Exception {
    NGramModel ngm = new NGramModel(3);

    List<Integer[]> res = ngm.generateNGrams(new Integer[]{1, 2, 3, 4, 5, 6});

    // 1 2 3 4 5 6

    // 1 2 3
    // 2 3 4
    // 3 4 5
    // 4 5 6
    // 5 6
    // 6

    assertArrayEquals(res.get(0), new Integer[]{1, 2, 3});
    assertArrayEquals(res.get(1), new Integer[]{2, 3, 4});
    assertArrayEquals(res.get(2), new Integer[]{3, 4, 5});
    assertArrayEquals(res.get(3), new Integer[]{4, 5, 6});
    assertArrayEquals(res.get(4), new Integer[]{5, 6});
    assertArrayEquals(res.get(5), new Integer[]{6});
  }

  @Test
  public void testGenerateNGramsSmallSentence() throws Exception {
    NGramModel ngm = new NGramModel(3);

    List<Integer[]> res = ngm.generateNGrams(new Integer[]{1, 2});

    // 1 2

    // 1 2
    // 2

    assertArrayEquals(res.get(0), new Integer[]{1, 2});
    assertArrayEquals(res.get(1), new Integer[]{2});
  }

  @Test
  public void testGenerateNGramsSingleWord() throws Exception {
    NGramModel ngm = new NGramModel(3);

    List<Integer[]> res = ngm.generateNGrams(new Integer[]{1});

    // 1

    // 1

    assertArrayEquals(res.get(0), new Integer[]{1});
  }
}
