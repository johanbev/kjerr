package kjerr.core.estimation;


import kjerr.core.Sequence;
import kjerr.core.io.CorpusReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProbabilityDistribution<T> {
  private Map<T, Integer> counts = new HashMap<>();
  private Map<T, Integer> outcomeToProb = new HashMap<>();
  private List<T> probToOutcome = new ArrayList<T>();
  private double[] probabilites;
  private double[] logProbabilities;

  boolean countsAdded = false;

  public void addOutcome(T outcome) {
    counts.put(outcome, counts.getOrDefault(outcome, 0) + 1);

    countsAdded = true;
  }

  public void addOutcomes(T[] outcomes) {
    for (T outcome : outcomes) {
      addOutcome(outcome);
    }
  }

  public void addOutcomes(Sequence<T> seq, int col) {
    addOutcomes(seq.getColumn(col));
  }

  public void addOutcomes(CorpusReader<T> reader, int column) throws IOException {
    Sequence<T> seq;

    while ((seq = reader.getSequence()) != null) {
      addOutcomes(seq, column);
    }
  }

  private void recalculateDistribution() {
    if (!countsAdded && (probabilites == null)) {
      throw new IllegalStateException();
    }

    if (!countsAdded) {
      return;
    }

    long total = counts.values().stream().mapToInt((x)->x).sum();

    probToOutcome = new ArrayList<>(counts.keySet());

    outcomeToProb.clear();
    probabilites = new double[probToOutcome.size()];
    logProbabilities = new double[probToOutcome.size()];

    for (int i = 0; i < probToOutcome.size(); i++) {
      outcomeToProb.put(probToOutcome.get(i), i);

      probabilites[i] = counts.get(probToOutcome.get(i)) / (double)total;
      logProbabilities[i] = Math.log(probabilites[i]);
    }
  }

  public double[] density() {
    recalculateDistribution();

    return probabilites;
  }

  public double probability(T outcome) {
    double[] density = density(); // make sure density is calculated
    Integer index = outcomeToProb.get(outcome);

    if (index == null) {
      throw new IllegalStateException();
    }

    return density[index];
  }

  public double[] logDensity() {
    recalculateDistribution();

    return logProbabilities;
  }

  public double logProbability(T outcome) {
    double[] logDensity = logDensity(); // make sure density is calculated
    Integer index = outcomeToProb.get(outcome);

    if (index == null) {
      throw new IllegalStateException();
    }

    return logDensity[index];
  }

  public int outcomeIndex(T outcome) {
    return outcomeToProb.get(outcome);
  }
}
