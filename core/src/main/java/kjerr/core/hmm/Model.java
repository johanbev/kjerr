package kjerr.core.hmm;


import java.util.List;
import java.util.Map;

public class Model {

  Map<Integer,List<State>> observationIndex;
  Map<Integer,State> states;
  State startState;


  public State getStartState() {
    return startState;
  }

  public List<State> getStatesForObservation(int observation) {
    return observationIndex.get(observation);
  }
}
