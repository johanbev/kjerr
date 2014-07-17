package kjerr.core.hmm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

  Map<Integer, List<State>> observationIndex;
  Map<Integer, State> states;
  State startState;
  State endState;

  public Model() {

    states = new HashMap<>();
    observationIndex = new HashMap<>();


    startState = new State();
    startState.stateId = -1;
    endState = new State();
    endState.stateId = -2;
  }

  public State getStartState() {
    return startState;
  }

  public State  getState(int state) {
    State s = states.getOrDefault(state,null);
    if(s == null) {
      s = new State();
      s.stateId = state;
      states.put(state,s);
    }
    return s;
  }

  public List<State> getStatesForObservation(int observation) {
    return observationIndex.get(observation);
  }

  public State getEndState() {
    return endState;
  }

  public void buildObservationIndex() {
    for(State s : states.values()){
      for(int obs : s.emissions.getBackingMap().keySet()) {
        List<State> states = observationIndex.getOrDefault(obs, new ArrayList<State>());
        if(!states.contains(s)) {
          states.add(s);
          observationIndex.put(obs,states);
        }

      }
    }
  }
}
