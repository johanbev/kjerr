package kjerr.core.hmm;

import kjerr.core.io.InternedReader;
import kjerr.core.io.TTReader;
import org.testng.annotations.Test;

import java.io.FileInputStream;

public class LearnerTest {

  @Test
  public void testTrain() throws Exception {
    InternedReader ir =
        new InternedReader(new TTReader(2, getClass().getResourceAsStream("/Test2Col.tt")));

    Learner l = new Learner();

    Model m = l.train(ir);

    GraphDecoder gd = new GraphDecoder(m);

    InternedReader ir2 =
        new InternedReader(new TTReader(2, new FileInputStream("F:\\src\\mulm\\eval\\wsj\\wsj.tt")));

    m = l.train(ir2);






  }
}
