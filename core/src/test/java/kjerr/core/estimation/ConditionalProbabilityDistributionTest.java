package kjerr.core.estimation;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ConditionalProbabilityDistributionTest {

  @Test
  public void testAddOutcomes() throws Exception {
    ConditionalProbabilityDistribution<String> dist = new ConditionalProbabilityDistribution<>();
    dist.addOutcomes(
            new String[]{"x", "y", "y", "x", "z", "x", "y", "x"},
            new String[]{"1", "2", "1", "2", "1", "1", "1", "1"});

    assertEquals(dist.probability("x", "1"), 3.0/6.0);
    assertEquals(dist.logProbability("x", "1"), Math.log(3.0/6.0));

    assertEquals(dist.probability("y", "1"), 2.0/6.0);
    assertEquals(dist.logProbability("y", "1"), Math.log(2.0/6.0));

    dist.addOutcome("y", "1");

    assertEquals(dist.probability("y", "1"), 3.0 / 7.0);
    assertEquals(dist.logProbability("y", "1"), Math.log(3.0/7.0));


    assertEquals(dist.probability("z", "1"), 1.0/7.0);
    assertEquals(dist.logProbability("z", "1"), Math.log(1.0/7.0));
  }
}