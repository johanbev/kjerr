package kjerr.io;

import kjerr.core.StringInterner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/** Does not actually work
 *
 */
public class TTReader {



    public TTReader(StringInterner stringInterner, int columns, File file) throws IOException {
        this.setStringInterner(stringInterner);
        this.columns = columns;
        this.file = file;

        prepareReader();
    }

    int columns;
    private StringInterner stringInterner;
    File file;
    BufferedReader br;
    List<String> bufferLines = new ArrayList<>();

    private void prepareReader() throws IOException {
        br = new BufferedReader(new FileReader(file));
    }

    public int[] getSequence() throws IOException {
        bufferLines.clear();
        String s;

        while(!(s = br.readLine()).equals("")) {
            bufferLines.add(s);
        }

        int[] ret = new int[bufferLines.size()];
        for(int i = 0; i < ret.length; i++) {
            ret[i] = getStringInterner().intern(bufferLines.get(i));
        }

        return ret;
    }

    public StringInterner getStringInterner() {
        return stringInterner;
    }

    public void setStringInterner(StringInterner stringInterner) {
        this.stringInterner = stringInterner;
    }
}
