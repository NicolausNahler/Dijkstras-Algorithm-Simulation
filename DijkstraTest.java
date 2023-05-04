package Dijkstra;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {
    @Test
    void testToStringNode() {
        Node node = new Node("A");
        node.addEdge(1, new Node("B"));
        node.addEdge(3, new Node("C"));
        node.addEdge(1, new Node("D"));
        assertEquals("A [totalDistance: 0] B:1, C:3, D:1", node.toString());
    }

    @Test
    void testToStringGraph() {
        Graph graph = new Graph();
        graph.readGraphFromAdjacencyMatrixFile(Paths.get("resources/Graph_A-H.csv"));
        assertEquals("""
                A [totalDistance: 0] B:1, C:3, D:1
                B [totalDistance: 0] A:1, E:3, F:3
                C [totalDistance: 0] A:3, D:1, G:1
                D [totalDistance: 0] A:1, C:1, E:1, G:2
                E [totalDistance: 0] B:3, D:1, F:1, H:5
                F [totalDistance: 0] B:3, E:1, H:1
                G [totalDistance: 0] C:1, D:2, H:1
                H [totalDistance: 0] E:5, F:1, G:1
                """, graph.toString());
    }

    @Test
    public void testBig() {
        Graph graph = new Graph();
        graph.readGraphFromAdjacencyMatrixFile(Paths.get("resources/big.csv"));
        graph.calcWithDijkstra("n0");
        graph.getAllPaths();
    }

    @Test
    public void testWithNames() {
        Graph graph = new Graph();
        graph.readGraphFromAdjacencyMatrixFile(Paths.get("resources/Graph_12_with_names.csv"));
        graph.calcWithDijkstra("Barthhal");
        graph.getAllPaths();
    }

    @Test
    public void testError() {
        assertThrows(IllegalArgumentException.class,
                () -> new Graph().readGraphFromAdjacencyMatrixFile(Paths.get("resources/kaputt_Graph_A-H_a.csv")));
        assertThrows(IllegalArgumentException.class,
                () -> new Graph().readGraphFromAdjacencyMatrixFile(Paths.get("resources/kaputt_Graph_A-H_b.csv")));
        assertThrows(IllegalArgumentException.class,
                () -> new Graph().readGraphFromAdjacencyMatrixFile(Paths.get("resources/kaputt_Graph_A-H_c.csv")));
        assertThrows(IllegalArgumentException.class,
                () -> new Graph().readGraphFromAdjacencyMatrixFile(Paths.get("resources/kaputt_Graph_A-H_d.csv")));
        assertThrows(IllegalArgumentException.class,
                () -> new Graph().readGraphFromAdjacencyMatrixFile(Paths.get("resources/kaputt_Graph_A-H_e.csv")));
    }

    @Test
    public void testFileNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> new Graph().readGraphFromAdjacencyMatrixFile(Paths.get("file/not/found.csv")));
    }

    @Test
    public void testStartNodeNotExistError() {
        assertThrows(NoSuchElementException.class,
                () -> {
                    Graph graph = new Graph();
                    graph.readGraphFromAdjacencyMatrixFile(Paths.get("resources/big.csv"));
                    graph.calcWithDijkstra("A");
                });
    }

    @Test
    public void testNoConnectionError() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Graph graph = new Graph();
                    graph.readGraphFromAdjacencyMatrixFile(Paths.get("resources/unzusammenhaengend_Graph_A-M.csv"));
                    graph.calcWithDijkstra("A");
                });
    }
}
