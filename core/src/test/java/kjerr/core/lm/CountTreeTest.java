package kjerr.core.lm;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;


public class CountTreeTest {

  private static final double EPSILON = 0.00001d;

  @Test
  public void testUnigram() throws Exception {
    CountTree countTree = new CountTree();
    countTree.addUnigram(1);
    countTree.addUnigram(2);
    countTree.addUnigram(3);

    assertEquals("Wrong total", countTree.getRoot().getCount(), 3);
    assertEquals("Wrong prob", (1.0d / 3), countTree.queryUnigram(1), EPSILON);

    CountTree countTree2 = new CountTree();
    countTree2.addUnigram(1);
    countTree2.addUnigram(1);
    countTree2.addUnigram(3);

    assertEquals("Wrong total", countTree2.getRoot().getCount(), 3);
    assertEquals("Wrong prob", (2.0d / 3), countTree2.queryUnigram(1), EPSILON);
  }

  @Test
  public void testBigram() throws Exception {
    CountTree countTree = new CountTree();

    countTree.addBigram(1, 1);
    countTree.addBigram(1, 1);
    countTree.addBigram(1, 3);
    countTree.addBigram(2, 3);
    countTree.addBigram(2, 4);
    countTree.addBigram(4, 5);

    countTree.addUnigram(5);
    countTree.addUnigram(6);

    assertEquals("Wrong total", countTree.getRoot().getCount(), 8);

    assertEquals("Wrong prob", (2.0d) / 3, countTree.queryBigram(1, 1));
    assertEquals("Wrong prob", (3.0d) / 8, countTree.queryUnigram(1));
  }

  @Test
  public void testTrigram() throws Exception {
    CountTree countTree = new CountTree();

    countTree.addTrigram(1, 1, 1);
    countTree.addTrigram(1, 1, 1);
    countTree.addTrigram(1, 1, 2);
    countTree.addTrigram(1, 1, 3);

    assertEquals("Wrong total", countTree.getRoot().getCount(), 4);
    assertEquals("Wrong prob", (2.0d) / 4, countTree.queryTrigram(1, 1, 1), EPSILON);

  }

  @Test
  public void testFind() {
    CountTree countTree = new CountTree();

    countTree.addTrigram(1,2,3);
    countTree.addTrigram(1,2,1);
    countTree.addTrigram(3,1,3);

    CountTree.CountTreeNode ctn = countTree.find(new int[]{1});
    assertEquals("Wrong node", ctn.getCount(), 2);

    ctn = countTree.find(new int[]{1,2});
    assertEquals("Wrong node", ctn.getCount(), 2);

    ctn = countTree.find(new int[]{1,2,3});
    assertEquals("Wrong node", ctn.getCount(), 1);
  }

  @Test
  public void testSequence() {
    CountTree countTree = new CountTree();

    countTree.addSequence(new int[]{1,2,3});
    countTree.addTrigram(1,2,3);
    countTree.addTrigram(1,2,4);

    assertEquals("Wrong prob", (2.0d) / 3, countTree.queryTrigram(1, 2, 3),  EPSILON);


    countTree = new CountTree();

    countTree.addSequence(new int[]{1,2,3,4,5});
    countTree.addSequence(new int[]{1,2,3,4,5});
    countTree.addSequence(new int[]{1,2,3,4,6});

    assertEquals("Wrong prob", (2.0d) / 3, countTree.querySequence(new int[]{1,2,3,4,5}),  EPSILON);
  }
}
