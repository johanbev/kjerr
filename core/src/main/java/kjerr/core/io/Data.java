package kjerr.core.io;

import org.apache.commons.collections.iterators.ArrayIterator;

import java.util.Iterator;
import java.util.List;

import static kjerr.core.io.Data.Type.*;

public class Data implements Iterable {
    enum Type { NUMERIC, INTEGER, OBJECT }

    private double[][] numericValues;
    private int[][] integerValues;
    private Object[] objectValues;
    private Type type;

    public Data(Data x) {
        this(x.getNumericValues().clone());
    }

    public Data(List values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException();
        }
        else if (Data.containsDoubleArray(values)) {
            numericValues = Data.toDoubleArray(values);
            type = NUMERIC;
        }
        else {
            objectValues = values.toArray(new Object[values.size()]);
            type = OBJECT;
        }
    }

    public Data(int[][] values) {
        integerValues = values;
        type = INTEGER;
    }

    public Data(double[][] values) {
        numericValues = values;
        type = NUMERIC;
    }

    public double[][] getNumericValues() {
        return numericValues;
    }

    public boolean isNumeric() {
        return type == NUMERIC;
    }

    @Override
    public Iterator iterator() {
        switch (type) {
            case NUMERIC:
                return new ArrayIterator(numericValues);
            case INTEGER:
                return new ArrayIterator(integerValues);
            case OBJECT:
                return new ArrayIterator(objectValues);
        }

        return null;
    }

    public int size() {
        switch (type) {
            case NUMERIC:
                return numericValues.length;
            case INTEGER:
                return integerValues.length;
            case OBJECT:
                return objectValues.length;
        }

        throw new RuntimeException();
    }

    public Object get(int i) {
        switch (type) {
            case NUMERIC:
                return numericValues[i];
            case INTEGER:
                return integerValues[i];
            case OBJECT:
                return objectValues[i];
        }

        throw new RuntimeException();
    }

    public double get(int i, int j) {
        switch (type) {
            case NUMERIC:
                return numericValues[i][j];
            case INTEGER:
                return integerValues[i][j];
        }

        throw new RuntimeException();
    }

    private static double[][] toDoubleArray(List values) {
        if (values.isEmpty()) {
            return new double[0][];
        }

        int width = ((Double[])values.get(0)).length;

        double[][] rVal = new double[values.size()][width];

        for (int i = 0; i < values.size(); i++) {
            Double[] val = (Double[])values.get(i);

            for (int j = 0; j < val.length; j++) {
                rVal[i][j] = val[j];
            }
        }

        return rVal;
    }

    protected static boolean containsDoubleArray(List values) {
        return !values.isEmpty() &&
                values.get(0).getClass().isArray() &&
                (values.get(0).getClass().getComponentType() == Double.class);

    }
}
