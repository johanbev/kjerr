package kjerr.core.hmm;

import kjerr.core.Distribution;

public class State {
  int stateId;

  Distribution emissions;
  Distribution transitions;

}
