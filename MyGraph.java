// Xiaodan Hu
// 02/13/17
// CSE373
// TA: Trung Ly 
// Assignment #4

import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // you will need some private fields to represent the graph
    // you are also likely to want some private helper methods
    private Map<Vertex, Set<Edge>> result;

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
       result = new HashMap<Vertex, Set<Edge>>();
       for (Edge ed: e) {
          for (Edge ed2: e) {
              if (!ed.equals(ed2) && ed.getSource().equals(ed2.getSource()) 
                   && ed.getDestination().equals(ed2.getDestination())) {
                   throw new IllegalArgumentException();
               }
          }
          if (!v.contains(ed.getSource()) || !v.contains(ed.getDestination()) 
               || ed.getWeight() < 0) {
              throw new IllegalArgumentException();
          }
          if (!result.containsKey(ed.getSource())) {
             this.result.put(new Vertex(ed.getSource().getLabel()), 
                             new HashSet<Edge>());
          }
          result.get(new Vertex(ed.getSource().getLabel())).add(new 
               Edge(new Vertex(ed.getSource().getLabel()), 
               new Vertex(ed.getDestination().getLabel()), 
               ed.getWeight()));
       }
      for (Vertex ver: v) {
         if (!this.result.containsKey(ver)) {
            this.result.put(new Vertex(ver.getLabel()), new HashSet<Edge>());
         }
      }
     }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
        List<Vertex> copyVertex = new ArrayList<Vertex>();
        for (Vertex vex: this.result.keySet()) {
           copyVertex.add(new Vertex(vex.getLabel()));
        }
        return copyVertex;
	 }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
        List<Edge> copyEdge = new ArrayList<Edge>();
        for (Vertex vex: this.result.keySet()) {   
           if (!this.result.keySet().isEmpty()) {
              Set<Edge> temp = this.result.get(vex);
              for (Edge ed: temp) {
                 copyEdge.add(new Edge(new Vertex(vex.getLabel()), 
                              new Vertex(ed.getDestination().getLabel()),
                              ed.getWeight()));
              } 
           }
        }
        return copyEdge;
	 }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
        if (v == null ||!this.result.containsKey(v)) {
           throw new IllegalArgumentException();
        }
	     List<Vertex> adjacent = new ArrayList<Vertex>();
        if (!this.result.get(v).isEmpty()) {
          for (Edge ed: this.result.get(v)) {
             adjacent.add(new Vertex(ed.getDestination().getLabel()));
          }
        }
        return adjacent;
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
       if (a == null || b == null || !this.result.containsKey(a) 
            || !this.result.containsKey(b)) {
           throw new IllegalArgumentException();
       }
       for (Edge ed: this.result.get(a)) {
          if (ed.getDestination().equals(b)) {
             return ed.getWeight();
          }
       }
         return -1;
    }
}