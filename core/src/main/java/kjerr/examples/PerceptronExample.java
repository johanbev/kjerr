package kjerr.examples;

import kjerr.core.io.DataSet;
import kjerr.core.io.DatasetReader;

import java.io.IOException;

public class PerceptronExample {
    public static void main(String[] args) throws IOException {
        DataSet iris = DatasetReader.loadIris();
    }
}
