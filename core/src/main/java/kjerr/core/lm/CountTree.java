package kjerr.core.lm;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Map;


/**
 * CountTree for holding counts of n-gram observations
 */
public class CountTree {

    final private CountTreeNode root = new CountTreeNode();

    /**
     * Construct CountTree with default options
     */
    public CountTree() {

        getRoot().setCount(0);
        getRoot().setChildren(new Int2ObjectArrayMap<CountTreeNode>(32));
    }

    public CountTree addSequence(int[] seq) {
        return null;
    }

    public void addUnigram(int t1) {
        getRoot().setCount(getRoot().getCount() + 1);
        CountTreeNode ctn = getRoot().getChildren().get(t1);
        if (ctn == null) {
            ctn = new CountTreeNode();
            getRoot().getChildren().put(t1, ctn);
        }
        ctn.setCount(ctn.getCount() + 1);

    }

    public void addBigram(int t1, int t2) {
        getRoot().setCount(getRoot().getCount() + 1);

        CountTreeNode first = getRoot().getChildren().get(t1);
        if (first == null) {
            first = new CountTreeNode();
            getRoot().getChildren().put(t1, first);
        }
        first.setCount(first.getCount() + 1);

        CountTreeNode second = first.getChildren().get(t2);
        if (second == null) {
            second = new CountTreeNode();
            first.getChildren().put(t2, second);
        }
        second.setCount(second.getCount() + 1);

    }

    public void addTrigram(int t1, int t2, int t3) {
        getRoot().setCount(getRoot().getCount() + 1);

        CountTreeNode first = getRoot().getChildren().get(t1);
        if (first == null) {
            first = new CountTreeNode();
            getRoot().getChildren().put(t1, first);
        }
        first.setCount(first.getCount() + 1);

        CountTreeNode second = first.getChildren().get(t2);
        if (second == null) {
            second = new CountTreeNode();
            first.getChildren().put(t2, second);
        }
        second.setCount(second.getCount() + 1);

        CountTreeNode third = second.getChildren().get(t3);
        if (third == null) {
            third = new CountTreeNode();
            second.getChildren().put(t3, third);
        }
        third.setCount(third.getCount() + 1);
    }

    public double querySequence(int[] sequence) {
        return 0.0d;
    }

    public double queryUnigram(int t1) {
        if (getRoot().getChildren().get(t1) == null)
            return 0.0d;
        return getRoot().getChildren().get(t1).getCount() / (double) getRoot().getCount();
    }

    public double queryBigram(int t1, int t2) {
        CountTreeNode first = getRoot().getChildren().get(t1);
        if (first == null)
            return 0.0d;
        CountTreeNode second = first.getChildren().get(t2);
        if (second == null)
            return 0.0d;

        return second.getCount() / (double) first.getCount();
    }

    public double queryTrigram(int t1, int t2, int t3) {
        CountTreeNode first = getRoot().getChildren().get(t1);
        if (first == null)
            return 0.0d;
        CountTreeNode second = first.getChildren().get(t2);
        if (second == null)
            return 0.0d;
        CountTreeNode third = second.getChildren().get(t3);
        if (third == null)
            return 0.0d;

        return third.getCount() / (double) second.getCount();
    }

    public CountTreeNode getRoot() {
        return root;
    }



    /**
     * A node in the CountTree
     */
    final static class CountTreeNode {
        private int count;


        private Map<Integer, CountTreeNode> children = new Int2ObjectArrayMap<>();

        public final int getCount() {
            return count;
        }

        public final void setCount(int count) {
            this.count = count;
            if (count > 64 & !(children instanceof Int2ObjectOpenHashMap)) {
                children = new Int2ObjectOpenHashMap<>(children);
            }
        }

        public final Map<Integer, CountTreeNode> getChildren() {
            return children;
        }

        public final void setChildren(Map<Integer, CountTreeNode> children) {
            this.children = children;
        }
    }

}
