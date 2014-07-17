package kjerr.core.perceptron;

import kjerr.core.io.Data;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import java.util.stream.DoubleStream;

public class StandardScaler implements Transformer {
  private Parameters params = new Parameters();

  @Override
  public Transformer fit(Data x) {
    if (!x.isNumeric()) {
      throw new IllegalArgumentException();
    }

    RealMatrix m = MatrixUtils.createRealMatrix(x.getNumericValues());

    int p = m.getColumnDimension();
    int n = m.getRowDimension();

    params.mu = new double[p];
    params.sigma = new double[p];

    for (int i = 0; i < p; i++) {
      params.mu[i] = DoubleStream.of(m.getColumn(i)).sum();
      params.mu[i] = params.mu[i] / n;

      double squareSum = DoubleStream.of(m.getColumn(i)).map(v -> Math.pow(v, 2)).sum();
      params.sigma[i] = (squareSum / n) - Math.pow(params.mu[i], 2);
    }

    return this;
  }

  @Override
  public Data transform(Data x) {
    Data newX = new Data(x);
    double[][] values = x.getNumericValues();

    for (int i = 0; i < values.length; i++) {
      for (int j = 0; j < values[i].length; j++) {
        values[i][j] = (values[i][j] - params.mu[j]) / params.sigma[j];
      }
    }

    return newX;
  }

  public static class Parameters {
    private double[] mu;
    private double[] sigma;
  }

}
