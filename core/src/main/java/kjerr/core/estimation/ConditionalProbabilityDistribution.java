package kjerr.core.estimation;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConditionalProbabilityDistribution<O, C> {
  private Map<Pair<O, C>, Integer> counts = new HashMap<>();
  private Map<Pair<O, C>, Pair<Integer, Integer>> outcomeToProb = new HashMap<>();
  private List<O> probToOutcome = new ArrayList<O>();
  private List<C> probToCondition = new ArrayList<C>();
  private double[][] probabilites;
  private double[][] logProbabilities;

  boolean countsAdded = false;

  public void addOutcome(O outcome, C condition) {
    ImmutablePair<O, C> key = new ImmutablePair<>(outcome, condition);
    counts.put(key, counts.getOrDefault(key, 0) + 1);

    countsAdded = true;
  }

  public void addOutcomes(O[] outcomes, C[] conditions) {
    if (outcomes.length != conditions.length) {
      throw new IllegalArgumentException();
    }

    for (int i = 0; i < outcomes.length; i++) {
      addOutcome(outcomes[i], conditions[i]);
    }
  }

  private void recalculateDistribution() {
    if (!countsAdded && (probabilites == null)) {
      throw new IllegalStateException();
    }

    if (!countsAdded) {
      return;
    }

    List<O> outcomes = counts.keySet().stream().map(Pair::getLeft).collect(Collectors.toList());
    List<C> conditions = counts.keySet().stream().map(Pair::getRight).collect(Collectors.toList());

    probToOutcome = new ArrayList<>(outcomes);
    probToCondition = new ArrayList<>(conditions);

    outcomeToProb.clear();

    long[] total = new long[probToCondition.size()];

    for (Map.Entry<Pair<O, C>, Integer> e : counts.entrySet()) {
      int index = probToCondition.indexOf(e.getKey().getRight());
      total[index] += e.getValue();
    }

    probabilites = new double[probToOutcome.size()][probToCondition.size()];
    logProbabilities = new double[probToOutcome.size()][probToCondition.size()];

    for (int i = 0; i < probToOutcome.size(); i++) {
      for (int j = 0; i < probToCondition.size(); i++) {
        outcomeToProb.put(
                new ImmutablePair<O, C>(probToOutcome.get(i), probToCondition.get(j)),
                new ImmutablePair<>(i, j));

        probabilites[i][j] =
                counts.get(new ImmutablePair<>(probToOutcome.get(i), probToCondition.get(j))) / (double)total[j];
        logProbabilities[i][j] = Math.log(probabilites[i][j]);
      }
    }
  }

  public double[][] density() {
    recalculateDistribution();

    return probabilites;
  }

  public double probability(O outcome, C condition) {
    double[][] density = density(); // make sure density is calculated
    Pair<Integer, Integer> index = outcomeToProb.get(new ImmutablePair<>(outcome, condition));

    if (index == null) {
      throw new IndexOutOfBoundsException();
    }

    return density[index.getLeft()][index.getRight()];
  }

  public double[][] logDensity() {
    recalculateDistribution();

    return logProbabilities;
  }

  public double logProbability(O outcome, C condition) {
    double[][] logDensity = logDensity(); // make sure density is calculated
    Pair<Integer, Integer> index = outcomeToProb.get(new ImmutablePair<>(outcome, condition));

    if (index == null) {
      throw new IndexOutOfBoundsException();
    }

    return logDensity[index.getLeft()][index.getRight()];
  }

  public Pair<Integer, Integer> outcomeIndex(O outcome, C condition) {
    return outcomeToProb.get(new ImmutablePair<>(outcome, condition));
  }
}
