package kjerr.core.estimation;

import kjerr.core.Sequence;
import kjerr.core.io.CorpusReader;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConditionalProbabilityDistribution<T> {
  private Map<Pair<T, T>, Integer> counts = new HashMap<>();
  private Map<Pair<T, T>, Pair<Integer, Integer>> outcomeToProb = new HashMap<>();
  private List<T> probToOutcome = new ArrayList<T>();
  private List<T> probToCondition = new ArrayList<T>();
  private double[][] probabilites;
  private double[][] logProbabilities;

  boolean countsAdded = false;

  public void addOutcome(T outcome, T condition) {
    ImmutablePair<T, T> key = new ImmutablePair<>(outcome, condition);
    counts.put(key, counts.getOrDefault(key, 0) + 1);

    countsAdded = true;
  }

  public void addOutcomes(T[] outcomes, T[] conditions) {
    if (outcomes.length != conditions.length) {
      throw new IllegalArgumentException();
    }

    for (int i = 0; i < outcomes.length; i++) {
      addOutcome(outcomes[i], conditions[i]);
    }
  }

  public void addOutcomes(Sequence<T> seq, int outcomeCol, int conditionCol) {
    addOutcomes(seq.getColumn(outcomeCol), seq.getColumn(conditionCol));
  }

  public void addOutcomes(CorpusReader<T> reader, int outcomeCol, int conditionCol) throws IOException {
    Sequence<T> seq;

    while ((seq = reader.getSequence()) != null) {
      addOutcomes(seq, outcomeCol, conditionCol);
    }
  }

  private void recalculateDistribution() {
    if (!countsAdded && (probabilites == null)) {
      throw new IllegalStateException();
    }

    if (!countsAdded) {
      return;
    }

    List<T> outcomes = counts.keySet().stream().map(Pair::getLeft).collect(Collectors.toList());
    List<T> conditions = counts.keySet().stream().map(Pair::getRight).collect(Collectors.toList());

    probToOutcome = new ArrayList<>(outcomes);
    probToCondition = new ArrayList<>(conditions);

    outcomeToProb.clear();

    long[] total = new long[probToCondition.size()];

    for (Map.Entry<Pair<T, T>, Integer> e : counts.entrySet()) {
      int index = probToCondition.indexOf(e.getKey().getRight());
      total[index] += e.getValue();
    }

    probabilites = new double[probToOutcome.size()][probToCondition.size()];
    logProbabilities = new double[probToOutcome.size()][probToCondition.size()];

    for (int i = 0; i < probToOutcome.size(); i++) {
      for (int j = 0; i < probToCondition.size(); i++) {
        outcomeToProb.put(
                new ImmutablePair<T, T>(probToOutcome.get(i), probToCondition.get(j)),
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

  public double probability(T outcome, T condition) {
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

  public double logProbability(T outcome, T condition) {
    double[][] logDensity = logDensity(); // make sure density is calculated
    Pair<Integer, Integer> index = outcomeToProb.get(new ImmutablePair<>(outcome, condition));

    if (index == null) {
      throw new IndexOutOfBoundsException();
    }

    return logDensity[index.getLeft()][index.getRight()];
  }

  public Pair<Integer, Integer> outcomeIndex(T outcome, T condition) {
    return outcomeToProb.get(new ImmutablePair<>(outcome, condition));
  }
}
