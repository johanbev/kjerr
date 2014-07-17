package kjerr.core.perceptron;

import kjerr.core.io.Data;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class PerceptronTest {

  private List<Double[]> input;
  private List<Integer> target;

  @BeforeMethod
  public void setUp() throws Exception {
    input = Arrays.asList(
        new Double[]{0.0, 0.0},
        new Double[]{0.0, 1.0},
        new Double[]{1.0, 0.0},
        new Double[]{1.0, 1.0});
    target = Arrays.asList(1, 1, 1, 0);
  }

  @Test
  public void testFit() throws Exception {

    Perceptron model = new Perceptron();
    model.fit(new Data(input), new Data(target));

    assertEquals(0.3, model.getBias(), 0.0001);
    assertEquals(0.1, model.getWeights()[0], 0.0001);
    assertEquals(0.1, model.getWeights()[1], 0.0001);
  }

  @Test
  public void testPredict() throws Exception {
    Perceptron.Parameters params = new Perceptron.Parameters();
    params.w = new double[][]{{0.1, 0.3, 0.3}};
    params.p = 2;
    params.c = 1;

    Data pred = Perceptron.predict(new Data(input), params);

    assertEquals(4, pred.size());
    assertEquals(0, (int) pred.get(0));
    assertEquals(1, (int) pred.get(1));
    assertEquals(0, (int) pred.get(2));
    assertEquals(1, (int) pred.get(3));
  }
}
