package Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nicolausnahler
 */
public class Graph implements IOfferDistance {
    /**
     * The PriorityQueue for the Graph.
     */
    private final PriorityQueue<Node> pq = new PriorityQueue<>();
    /**
     * An ArrayList of all Nodes inside the Graph.
     */
    private final ArrayList<Node> nodes = new ArrayList<>();

    /**
     * Reads the graph from an adjacency matrix file.
     *
     * @param file the matrix file
     */
    public void readGraphFromAdjacencyMatrixFile(Path file) {
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(";")) {
                    Arrays.stream(line.split(";")).filter(name -> !name.isEmpty()).map(Node::new).forEach(nodes::add);
                    continue;
                }

                if (line.split(";", -1).length != nodes.size() + 1) {
                    throw new IllegalArgumentException("Entry does not have the correct length.");
                }

                String[] split = line.split(";");
                String nodeId = split[0];
                for (int i = 1; i < split.length; i++) {
                    String entry = split[i];
                    if (!entry.isEmpty()) {
                        try {
                            Objects.requireNonNull(nodes.stream()
                                            .filter(node -> node.getId().equals(nodeId))
                                            .findFirst()
                                            .orElse(null))
                                    .addEdge(Integer.parseInt(entry), nodes.get(i - 1));
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Invalid top node for given edge. Cannot find " + nodeId + ". Entry: " + line + " | Stacktrace: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while reading file.");
        }

        nodes.stream().filter(n -> n.getEdges().size() == 0).forEach(n -> {
            throw new IllegalArgumentException("No edges for node " + n.getId());
        });

    }

    /**
     * Gets all paths to the Nodes.
     */
    public void getAllPaths() {
        nodes.forEach(node -> System.out.println(node.getPrevious() == null ? node.getId() + ": is start node" : node.getPath()));
    }

    /**
     * Calculates the path with dijkstra.
     *
     * @param startNodeId the start Node-Id
     */
    public void calcWithDijkstra(String startNodeId) {
        nodes.forEach(Node::init);

        Node startNode = nodes.stream()
                .filter(node -> node.getId().equals(startNodeId))
                .findFirst()
                .orElseThrow();
        startNode.setStartNode();
        pq.add(startNode);

        while (!pq.isEmpty()) {
            Objects.requireNonNull(pq.poll()).visit(this);
        }

        nodes.stream()
                .filter(n -> !n.isVisited())
                .findFirst()
                .ifPresent(node -> {
                    throw new IllegalArgumentException("No connection between nodes.");
                });
    }

    @Override
    public String toString() {
        return nodes.stream().map(node -> node + "\n").collect(Collectors.joining());
    }

    @Override
    public void offerDistance(String node2change, Node newPrevious, int newDistance) {
        Node n = nodes.stream().filter(node -> node.getId().equals(node2change)).findFirst().orElseThrow();
        if (pq.contains(n) && newDistance >= n.getDistance()) return;
        n.change(newDistance, newPrevious);
        pq.offer(n);
    }
}
