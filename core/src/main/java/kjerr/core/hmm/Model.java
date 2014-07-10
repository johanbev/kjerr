package kjerr.core.hmm;


public class Model {
  public State getStartState() {
    return null;
  }

  public State[] getStatesForObservation(int observation) {
    return new State[0];
  }
}
