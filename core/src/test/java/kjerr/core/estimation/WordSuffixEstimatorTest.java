package kjerr.core.estimation;

import kjerr.core.io.TTReader;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class WordSuffixEstimatorTest {

  @Test
  public void testFit() throws Exception {
    TTReader ttr = new TTReader(2,
        getClass().getResourceAsStream("/suffix.tt"));

    WordSuffixEstimator est = new WordSuffixEstimator(3, 0.9);
    est.fit(ttr);

    double[] p = est.predictProba("bbbb");

    assertEquals(0.547368, p[est.getTags().indexOf("0")], 0.001);
    assertEquals(0.452632, p[est.getTags().indexOf("1")], 0.001);

    p = est.predictProba("bb");

    assertEquals(0.547368, p[est.getTags().indexOf("0")], 0.001);
    assertEquals(0.452632, p[est.getTags().indexOf("1")], 0.001);
  }

  @Test
  public void testGetSuffix() throws Exception {
    assertEquals("ba", WordSuffixEstimator.getSuffix("aaba", 2));
  }
}
