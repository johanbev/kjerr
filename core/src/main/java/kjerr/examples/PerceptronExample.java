package kjerr.examples;

import kjerr.core.io.Data;
import kjerr.core.io.DataSet;
import kjerr.core.io.DatasetReader;
import kjerr.core.perceptron.LabelBinarizer;
import kjerr.core.perceptron.Perceptron;
import kjerr.core.perceptron.StandardScaler;

import java.io.IOException;

public class PerceptronExample {
  public static void main(String[] args) throws IOException {
    DataSet iris = DatasetReader.loadIris();
    LabelBinarizer bin = new LabelBinarizer();
    StandardScaler scaler = new StandardScaler();
    Perceptron model = new Perceptron();

    Data x = scaler.fitAndTransform(iris.X());
    Data y = bin.fitAndTransform(iris.Y());

    model.fit(x, y);
  }
}
