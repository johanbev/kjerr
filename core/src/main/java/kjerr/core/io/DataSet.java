package kjerr.core.io;

import java.util.Map;

public class DataSet {
    public enum Key {
        DESCRIPTION
    }

    private Data x;
    private Data y;
    private Map<Key, Object> properties;

    public DataSet(Data x, Data y, Map<Key, Object> properties) {
        this.x = x;
        this.y = y;
        this.properties = properties;
    }

    public Data X() {
        return x;
    }

    public Data Y() {
        return y;
    }

    public Object get(Key k) {
        return properties.get(k);
    }
}
