package kjerr.core.perceptron;

import kjerr.core.io.Data;


public interface Transformer {
  Transformer fit(Data x);

  Data transform(Data x);

  default Data fitAndTransform(Data x) {
    this.fit(x);

    return this.transform(x);
  }
}
