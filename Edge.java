package Dijkstra;

/**
 * @author nicolausnahler
 */
public record Edge(int distance, Node neighbour) implements Comparable<Edge> {
    @Override
    public int compareTo(Edge o) {
        return neighbour.getId().compareTo(o.neighbour.getId());
    }
}