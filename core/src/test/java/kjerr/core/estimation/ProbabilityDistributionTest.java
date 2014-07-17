package kjerr.core.estimation;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class ProbabilityDistributionTest {

  @Test
  public void testAddOutcomes() throws Exception {
    ProbabilityDistribution<String> dist = new ProbabilityDistribution<>();
    dist.addOutcomes(new String[]{"x", "y", "y", "x", "z", "x"});

    assertEquals(dist.probability("x"), 3.0/6.0);
    assertEquals(dist.density()[dist.outcomeIndex("x")], 3.0/6.0);
    assertEquals(dist.logProbability("x"), Math.log(3.0/6.0));
    assertEquals(dist.logDensity()[dist.outcomeIndex("x")], Math.log(3.0 / 6.0));

    assertEquals(dist.probability("y"), 2.0/6.0);
    assertEquals(dist.density()[dist.outcomeIndex("y")], 2.0/6.0);
    assertEquals(dist.logProbability("y"), Math.log(2.0/6.0));
    assertEquals(dist.logDensity()[dist.outcomeIndex("y")], Math.log(2.0 / 6.0));

    dist.addOutcome("y");

    assertEquals(dist.probability("y"), 3.0 / 7.0);
    assertEquals(dist.density()[dist.outcomeIndex("y")], 3.0/7.0);
    assertEquals(dist.logProbability("y"), Math.log(3.0/7.0));
    assertEquals(dist.logDensity()[dist.outcomeIndex("y")], Math.log(3.0 / 7.0));


    assertEquals(dist.probability("z"), 1.0/7.0);
    assertEquals(dist.density()[dist.outcomeIndex("z")], 1.0/7.0);
    assertEquals(dist.logProbability("z"), Math.log(1.0/7.0));
    assertEquals(dist.logDensity()[dist.outcomeIndex("z")], Math.log(1.0/7.0));
  }

  @Test
  public void testEmptyDist() {
    ProbabilityDistribution<String> dist = new ProbabilityDistribution<>();

    try {
      dist.density();
      fail();
    }
    catch (IllegalStateException e) {

    }
  }

  @Test
  public void testIllegalOutcome() {
    ProbabilityDistribution<String> dist = new ProbabilityDistribution<>();

    dist.addOutcome("y");

    try {
      dist.probability("x");
      fail();
    }
    catch (IllegalStateException e) {

    }
  }
}