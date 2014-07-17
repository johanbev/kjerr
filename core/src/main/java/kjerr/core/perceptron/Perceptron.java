package kjerr.core.perceptron;

import kjerr.core.io.Data;

import java.util.ArrayList;
import java.util.List;

public class Perceptron {
  Parameters params = new Parameters();

  private static Parameters doIteration(Data x, Data y, Parameters params) {
    for (int i = 0; i < x.size(); i++) {
      double[] input = (double[]) x.get(i);

      if (params.c == 1) {
        int target = (int) y.get(i);

        int pred = predict(input, params)[0];

        if (pred != target) {
          params.w[0] = updateWeights(params, params.w[0], input, target, pred);
        }
      } else {
        int[] target = (int[]) y.get(i);

        int[] pred = predict(input, params);

        for (int j = 0; j < params.c; j++) {
          params.w[i] = updateWeights(params, params.w[i], input, target[i], pred[i]);
        }
      }
    }

    return params;
  }

  protected static double[] updateWeights(Parameters params, double[] weights, double[] input, int target, int pred) {
    int sign = target - pred;

    for (int j = 0; j < params.p; j++) {
      weights[j] += sign * params.alpha * input[j];
    }

    weights[params.p] += sign * params.alpha;

    return weights;
  }

  protected static Data predict(Data x, Parameters params) {
    if (!x.isNumeric()) {
      throw new IllegalArgumentException();
    }

    List<Integer> pred = new ArrayList<>();

    for (double[] input : x.getNumericValues()) {
      pred.add(predict(input, params)[0]);
    }

    return new Data(pred);
  }

  private static int[] predict(double[] x, Parameters params) {
    if (params.c == 1) {
      return predictClass(x, params.w[0]);
    } else {
      double[] a = new double[params.c];
      double maxA = Double.NEGATIVE_INFINITY;
      int maxClass = -1;

      for (int i = 0; i < params.c; i++) {
        a[i] = activation(x, params.w[i]);

        if (a[i] > maxA) {
          maxA = a[i];
          maxClass = i;
        }
      }

      int[] pred = new int[params.c];
      pred[maxClass] = 1;

      return pred;
    }
  }

  private static int[] predictClass(double[] x, double[] weights) {
    double a = activation(x, weights);

    return new int[]{a > 0.5 ? 1 : 0};
  }

  protected static double activation(double[] x, double[] weights) {
    double a = weights[weights.length - 1];

    for (int i = 0; i < weights.length - 1; i++) {
      a += weights[i] * x[i];
    }
    return a;
  }

  protected static int numClasses(Object[] elt) {
    return elt.length;
  }

  protected static int numClasses(Object elt) {
    return 1;
  }

  public Perceptron fit(Data x, Data y) {
    if (!x.isNumeric()) {
      throw new IllegalArgumentException();
    }

    params.p = ((double[]) x.get(0)).length;
    params.c = numClasses(y.get(0));
    params.w = new double[params.c][params.p + 1];

    if (x.size() != y.size()) {
      throw new IllegalArgumentException();
    }

    params = doIteration(x, y, params);

    return this;
  }

  public Data predict(Data x) {
    return predict(x, params);
  }

  public double getBias() {
    return params.w[0][params.p];
  }

  public double[] getWeights() {
    return params.w[0];
  }

  public static class Parameters {
    protected int p;
    protected double[][] w;
    protected double alpha = 0.1;
    protected int c;
  }
}
