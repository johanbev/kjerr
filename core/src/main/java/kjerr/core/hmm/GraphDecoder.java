package kjerr.core.hmm;


import it.unimi.dsi.fastutil.objects.ObjectHeapPriorityQueue;
import kjerr.core.Sequence;

public class GraphDecoder {


  ObjectHeapPriorityQueue<Node> pq =
      new ObjectHeapPriorityQueue<>(
          (o1, o2) -> (int)(o1.score - o2.score));




  private Model m;

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
      return (int)(score - o.score);
    }
  }

  public int[] Decode(Sequence s) {

    Node startState = new Node(m.getStartState(), 0.0d, null,0);
    pq.enqueue(startState);

    for(Node n = pq.dequeue();;n=pq.dequeue()) {
      if(n == null)
        throw new IllegalStateException("Exhausted agenda before end of sequence!");

      if(n.index == s.getColumn(0).length) {
        return getSequenceFromNode(n);
      }

      createTransitionsFrom(n,s.getPoint(n.index++, 1));

    }


  }

  private int[] getSequenceFromNode(Node n) {
    return null;
  }

  private void createTransitionsFrom(Node n, int observation) {

    for(State s : m.getStatesForObservation(observation)) {
      double score =
          Math.log(s.emissions.getProb(observation))
        + Math.log(n.state.transitions.getProb(s.stateId))
        + n.score;

      Node next = new Node(s,score,n,n.index++);

      pq.enqueue(next);
    }



  }
}
