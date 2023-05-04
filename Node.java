package Dijkstra;

import java.util.TreeSet;

/**
 * @author nicolausnahler
 */
public class Node implements Comparable<Node> {
    /**
     * The id of the Node.
     */
    private final String id;
    /**
     * A TreeSet of all edges of the Node.
     */
    private final TreeSet<Edge> edges = new TreeSet<>();
    /**
     * The distance from the start Node.
     */
    private int distance;
    /**
     * The previous Node in the path.
     */
    private Node previous;
    /**
     * Defining whether the Node has been visited.
     */
    private boolean isVisited;

    /**
     * Constructor for the Node class
     *
     * @param id id of the Node
     */
    public Node(String id) {
        this.id = id;
    }

    /**
     * Getter for the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the edges.
     *
     * @return the edges
     */
    public TreeSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Getter for the distance.
     *
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }


    public Node getPrevious() {
        return previous;
    }

    /**
     * Getter for whether the Node has been visited.
     *
     * @return whether the Node has been visited
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Adds an edge to the Node.
     *
     * @param distance  the distance of the Edge
     * @param neighbour the neighbour to the Node
     */
    public void addEdge(int distance, Node neighbour) {
        edges.add(new Edge(distance, neighbour));
    }

    /**
     * Changes the distance from the start Node and the previous Node.
     *
     * @param distance the distance from the start Node
     * @param previous the previous Node
     */
    public void change(int distance, Node previous) {
        this.distance = distance;
        this.previous = previous;
    }

    /**
     * Gets the path for the Node.
     *
     * @return a String pointing out the path
     */
    public String getPath() {
        StringBuilder path = new StringBuilder();
        Node prev = this;
        while (prev.distance != 0) {
            path.insert(0, prev.previous.id + " --(" + prev.getDistance() + ")-> ");
            prev = prev.previous;
        }
        return path + id;
    }

    /**
     * Initializes the Node.
     */
    public void init() {
        distance = -1;
        previous = null;
        isVisited = false;
    }

    /**
     * Sets the start Node.
     */
    public void setStartNode() {
        distance = 0;
        previous = null;
    }

    /**
     * Visits a Node.
     *
     * @param graph the graph
     */
    public void visit(IOfferDistance graph) {
        edges.stream()
                .filter(edge -> !edge.neighbour().isVisited)
                .forEach(edge -> graph.offerDistance(edge.neighbour().getId(), this, distance + edge.distance()));
        isVisited = true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(id + " [totalDistance: " + distance + "] ");
        edges.forEach(edge -> s.append(edge.neighbour().getId()).append(":").append(edge.distance()).append(", "));
        return s.substring(0, s.length() - 2);
    }

    @Override
    public int compareTo(Node o) {
        int res = Integer.compareUnsigned(distance, o.distance);
        return res == 0 ? id.compareToIgnoreCase(o.id) : res;
    }
}
