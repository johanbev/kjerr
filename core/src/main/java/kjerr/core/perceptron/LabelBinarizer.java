package kjerr.core.perceptron;

import kjerr.core.io.Data;

import java.util.*;

public class LabelBinarizer implements Transformer {
  private Map<Object, Integer> labelMap;

  @Override
  public LabelBinarizer fit(Data x) {
    int nextIndex = 0;
    labelMap = new HashMap<>();

    for (Object elt : x) {
      if (!labelMap.containsKey(elt)) {
        labelMap.put(elt, nextIndex);
        nextIndex += 1;
      }
    }

    return this;
  }

  @Override
  public Data transform(Data x) {
    int[][] newX = new int[x.size()][labelMap.size()];

    for (int i = 0; i < x.size(); i++) {
      Object elt = x.get(i);

      if (labelMap.containsKey(elt)) {
        newX[i][labelMap.get(elt)] = 1;
      }
    }

    return new Data(newX);
  }

  public List getClasses() {
    List classes = new ArrayList<>();

    ArrayList<Map.Entry<Object, Integer>> entries = new ArrayList<>();
    entries.addAll(labelMap.entrySet());

    Collections.sort(entries,
        (Map.Entry<Object, Integer> o1, Map.Entry<Object, Integer> o2) -> o1.getValue().compareTo(o2.getValue()));

    for (Map.Entry<Object, Integer> entry : entries) {
      classes.add(entry.getKey());
    }

    return classes;
  }
}
