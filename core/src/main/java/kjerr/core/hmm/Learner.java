package kjerr.core.hmm;

import kjerr.core.Sequence;
import kjerr.core.io.InternedReader;

import java.io.IOException;

public class Learner {

  Model m;


  public void registerSequence(Sequence<Integer> s) {
    // register start state


    State prevState = m.getStartState();

    for (int i = 0; i < s.getColumn(0).length; i++) {
      int state = s.getPoint(1,i);
      int emission = s.getPoint(0, i);
      prevState.transitions.registerObservation(state);
      m.getState(state).emissions.registerObservation(emission);

      prevState = m.getState(state);
    }

    State endState = m.getEndState();
    prevState.transitions.registerObservation(endState.stateId);

  }


  public Model getModel() {
    return m;
  }

  public Model train(InternedReader ir) throws IOException {
    m = new Model();
    Sequence<Integer> seq;

    while((seq = ir.getSequence()) != null) {
      registerSequence(seq);
    }


    m.buildObservationIndex();
    return m;
  }
}
