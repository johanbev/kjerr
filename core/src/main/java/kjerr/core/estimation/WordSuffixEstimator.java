package kjerr.core.estimation;

import kjerr.core.Sequence;
import kjerr.core.io.TTReader;

import java.io.IOException;
import java.util.*;

public class WordSuffixEstimator {
  private Parameters parameters;
  private List<String> tags;
  private List<Map<String, Map<String, Double>>> probMaps;
  private List<Map<String, Map<String, Integer>>> countMaps;

  static class Parameters {
    public int suffixLength;
    public double decay;

  }

  public WordSuffixEstimator(int suffixLength, double decay) {
    parameters = new Parameters();
    parameters.suffixLength = suffixLength;
    parameters.decay = decay;

    this.countMaps = new ArrayList<>(suffixLength);
    this.probMaps = new ArrayList<>(suffixLength);

    for (int i = 0; i < suffixLength; i++) {
      this.countMaps.add(i, new HashMap<>());
      this.probMaps.add(i, new HashMap<>());
    }
  }

  public List<String> getTags() {
    return tags;
  }

  public WordSuffixEstimator fit(TTReader corpus) throws IOException {
    Sequence<String> s;

    Set<String> tags = new HashSet<>();

    while ((s = corpus.getSequence()) != null) {
      for (int i = 0; i < s.size(); i++) {
        String token = s.getPoint(0, i);
        String tag = s.getPoint(1, i);

        tags.add(tag);

        for (int j = 0; j < parameters.suffixLength; j ++) {
          if (token.length() < (j + 1)) {
            break;
          }

          Map<String, Map<String, Integer>> countMap = this.countMaps.get(j);
          String suffix = getSuffix(token, j + 1);

          if (!countMap.containsKey(suffix)) {
            countMap.put(suffix, new HashMap<>());
          }

          Map<String, Integer> tagMap = countMap.get(suffix);
          tagMap.put(tag, tagMap.getOrDefault(tag, 0) + 1);
        }
      }
    }

    this.tags = new ArrayList<String>();
    this.tags.addAll(tags);

    for (int i = 0; i < parameters.suffixLength; i++) {
      Map<String, Map<String, Integer>> countMap = this.countMaps.get(i);
      Map<String, Map<String, Double>> probMap = this.probMaps.get(i);

      for (Map.Entry<String, Map<String, Integer>> e : countMap.entrySet()) {
        String suffix = e.getKey();
        Map<String, Integer> tagCountMap = e.getValue();

        Map<String, Double> tagProbMap = new HashMap<>();
        probMap.put(suffix, tagProbMap);

        int total = tagCountMap.values().stream().mapToInt((x)->x).sum();

        for (Map.Entry<String, Integer> tagEntry : tagCountMap.entrySet()) {
          String tag = tagEntry.getKey();
          int count = tagEntry.getValue();
          tagProbMap.put(tag, count / (double)total);
        }
      }
    }

    return this;
  }

  public double[] predictProba(String token) {
    double[] prob = new double[this.tags.size()];

    for (int i = parameters.suffixLength; i > 0; i--) {
      Map<String, Double> tagMap = this.probMaps.get(i-1).get(getSuffix(token, i));

      if (tagMap == null) {
        continue;
      }

      for (int j = 0; j < this.tags.size(); j++) {
        double p = tagMap.getOrDefault(this.tags.get(j), 0d);

        if (prob[j] == 0d) {
          prob[j] = p;
        }
        else {
          prob[j] = (prob[j] + (parameters.decay * p)) / (1 + parameters.decay);
        }
      }
    }

    return prob;
  }

  public static String getSuffix(String str, int len) {
    return str.substring(str.length() - len);
  }
}
