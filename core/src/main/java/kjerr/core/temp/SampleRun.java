package kjerr.core.temp;

import kjerr.core.io.Data;
import kjerr.core.io.DataSet;
import kjerr.core.io.DatasetReader;
import kjerr.core.perceptron.StandardScaler;

import java.io.IOException;

public class SampleRun {
    public void main(String[] args) throws IOException {
        DataSet iris = DatasetReader.loadIris();
        Data x = iris.X();

        StandardScaler scaler = new StandardScaler();
        scaler.fit(x);
        Data scaledX = scaler.transform(x);

        System.out.println("Finito!");
    }
}
