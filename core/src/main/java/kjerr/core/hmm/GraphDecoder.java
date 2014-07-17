package kjerr.core.hmm;


import it.unimi.dsi.fastutil.objects.ObjectHeapPriorityQueue;
import kjerr.core.Sequence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GraphDecoder {




  ObjectHeapPriorityQueue<Node> pq =
      new ObjectHeapPriorityQueue<>(
          (o1, o2) -> (int) (o2.score - o1.score));


  private Model m;

  public List<Integer> Decode(Sequence<Integer,Integer> s) {

    pq.clear();

    Node startState = new Node(m.getStartState(), 0.0d, null, -1);
    pq.enqueue(startState);

    for (Node n = pq.dequeue(); ; n = pq.dequeue()) {
      if (n == null)
        throw new IllegalStateException("Exhausted agenda before end of sequence!");

      if (n.index == s.getWords().length) {
        return getSequenceFromNode(n);
      }

      if(n.index == s.getWords().length -1) {
        // at preterminal
        double score = n.score +
            Math.log(n.state.transitions.getProb(m.getEndState().stateId));

        Node endNode = new Node(m.getEndState(), score, n, n.index + 1);
        pq.enqueue(endNode);
      }
      else {
        createTransitionsFrom(n, s.getWord(n.index + 1));
      }
    }


  }

  public GraphDecoder(Model m) {
    this.m = m;
  }


  private List<Integer>  getSequenceFromNode(Node n) {



    LinkedList<Integer> ll = new LinkedList<>();


    while(n != null) {
      ll.add(n.state.stateId);
      n = n.parent;
    }

    Collections.reverse(ll);
    return ll;
  }

  private void createTransitionsFrom(Node n, int observation) {
    for (State s : m.getStatesForObservation(observation)) {
      double score =
          Math.log(s.emissions.getProb(observation))
              + Math.log(n.state.transitions.getProb(s.stateId))
              + n.score;

      Node next = new Node(s, score, n, n.index + 1);

      pq.enqueue(next);
    }


  }

  public class Node implements Comparable<Node> {
    State state;
    double score;
    Node parent;
    int index;

    public Node(State state, double score, Node parent, int index) {
      this.state = state;
      this.score = score;
      this.parent = parent;
      this.index = index;
    }

    @Override
    public int compareTo(Node o) {
      return (int) (score - o.score);
    }
  }
}
