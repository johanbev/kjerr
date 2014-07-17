package kjerr.core;

import java.util.HashMap;
import java.util.Map;

public class Distribution {
  int totalCount;

  Map<Integer, Integer> fmap = new HashMap<>();


  public void registerObservation(int observation) {
    fmap.put(observation, fmap.getOrDefault(observation, 0) + 1);
  }

  public double getProb(int observation) {
    return ((double) (fmap.get(observation)) / totalCount);
  }
}
