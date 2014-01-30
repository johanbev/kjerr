package kjerr.io;

import kjerr.core.StringInterner;
import kjerr.core.lm.NGramModel;

import java.io.File;

public class TTReaderTest {
    public static void main(String[] args) throws Exception {
        TTReader tt = new TTReader(new StringInterner(), 0,
                new File("G:\\scratch\\corp\\corp\\6M.wikiwords.corp"));

        NGramModel ng = new NGramModel(3);

        int mb = 1024*1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();


        //Print used memory


        int i = 0;
        long time = System.currentTimeMillis();
        System.out.println("Begin populating LM");
        for(;;) {
            i++;
            ng.addSequence(tt.getSequence());
            if(i == 500000) {
                long spentTime = (System.currentTimeMillis() - time);
                System.out.println(i + " sequences in " + (System.currentTimeMillis() - time) + "ms");
                System.out.println("\tDistinct word count is " + tt.getStringInterner().getCount());

                System.out.println("\tUsed Memory: "
                        + (runtime.totalMemory() - runtime.freeMemory()) / mb + "MB");

                System.out.println("Sequences sec: " + 500000  /   ((double)spentTime/1000));

                time = System.currentTimeMillis();
                i = 0;
            }
        }
    }
}
