package kjerr.core.lm;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;


/**
 * Language model based on n-grams.
 */
public class NGramModel {

    final private int ngramLength;
    final private int[] codeBuffer;
    private CountTree backingCountTree;

    /**
     * Constructs NGramModel with the backing datastructure
     * @param ngramLength The ngram size
     */
    public NGramModel(int ngramLength) {
        if (ngramLength > 3)
            throw new IllegalArgumentException("N must be  <= 3 (for now)");
        this.ngramLength = ngramLength;
        codeBuffer = new int[ngramLength];
        setBackingCountTree(new CountTree());
    }

    /**
     * Adds a sequence to the LM-model
     * @param sequence Sequence to be added to the LM model
     */
    public void addSequence(int[] sequence) {
        List<int[]> ngrams = generateNGrams(sequence);

        for(int[] ngram : ngrams) {
            if(ngram.length == 3)
                getBackingCountTree().addTrigram(ngram[0], ngram[1], ngram[2]);
            if(ngram.length == 2)
                getBackingCountTree().addBigram(ngram[0], ngram[1]);
            if(ngram.length == 1)
                getBackingCountTree().addUnigram(ngram[0]);
        }
    }

    /**
     * Split up a sequence into a list of n-grams
     * @param sequence Sequence to split into the n-grams
     * @return List of generated n-grams for the provided sequence
     */
    public List<int[]> generateNGrams(int[] sequence) {
        List<int[]> grams = new ArrayList<>(sequence.length);

        // first add all the grams in a sliding window
        // we do not need to add the smaller grams to the right
        // of the first word in the sequence, as these will be
        // full ngrams in the windows next position
        for(int i = 0; i < sequence.length + 1 - ngramLength; i++) {
            System.arraycopy(sequence, i, codeBuffer, 0, ngramLength);

            // do we need this clone?
            grams.add(codeBuffer.clone());
        }


        // when the sequence is shorter than the given ngram length
        // we need to add the smaller n-grams from k = n-1 all the way
        // down to k = 1
        int limit = min(ngramLength, sequence.length+1);

        for(int k = 1; k < limit; k++ ) {
            int[] buffer = new int[k];
            for(int n = 0; n < k; n++ ) {
                buffer[k-(n+1)] = sequence[sequence.length - (n + 1)];
            }
            grams.add(buffer);
        }


        return grams;
    }

    protected CountTree getBackingCountTree() {
        return backingCountTree;
    }

    protected void setBackingCountTree(CountTree backingCountTree) {
        this.backingCountTree = backingCountTree;
    }
}
