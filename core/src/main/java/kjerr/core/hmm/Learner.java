package kjerr.core.hmm;

import kjerr.core.Sequence;

public class Learner {

  Model m;




  void registerSequence(Sequence<Integer> s) {
    // register start state
    m.getStartState().transitions.registerObservation(s.getPoint(0,1));

    for(int i = 0; i < s.getColumn(0).length; i++) {
      m.getState(s.getPoint(0,1));
    }

  }
}
