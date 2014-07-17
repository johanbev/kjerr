package kjerr.core.hmm;

import kjerr.core.Distribution;

public class State {
  int stateId;

  Distribution emissions;
  Distribution transitions;


  public State() {
    emissions = new Distribution();
    transitions = new Distribution();
  }

}
