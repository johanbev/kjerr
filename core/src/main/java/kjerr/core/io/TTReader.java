package kjerr.core.io;

import com.google.common.primitives.Ints;
import kjerr.core.Sequence;
import kjerr.core.StringInterner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class TTReader {



    public TTReader(StringInterner[] stringInterners, int columns, InputStream stream) throws IOException {
        this.stringInterners = stringInterners;
        this.columns = columns;
        this.stream = stream;

        prepareReader();
    }



    int columns;
    private StringInterner[] stringInterners;
    InputStream stream;
    BufferedReader br;

    List<List<Integer>> buffer = new ArrayList<>(20);

    Pattern ttTokenizer = Pattern.compile("\\s+");

    private void prepareReader() throws IOException {
        br = new BufferedReader(new InputStreamReader(stream));
        for(int i = 0; i < columns; i++) {
            buffer.add(i,  new ArrayList<Integer>(20));
        }
    }

    public Sequence getSequence() throws IOException {

        for(int i = 0; i < columns; i++) {
            buffer.get(i).clear();
        }

        String s ;
        while((s = br.readLine()) != null && !s.isEmpty()) {
            int i = -1;
            String[] splits = ttTokenizer.split(s);
            for(int j  =0; j < columns; j++) {
                i++;
                buffer.get(i).add(stringInterners[i].intern(splits[j]));
            }
        }

        int[][] seq = new int [columns][];

        for(int i = 0; i < columns; i++) {
            seq[i] = Ints.toArray(buffer.get(i));
        }

        return new Sequence(seq);

    }
}
