package assign07;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void depthFirstSearchVertexObjectAndClasses() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("B", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");
        graph.addEdge("F", "H");
        graph.addEdge("H", "I");

        List<String> actual = graph.depthFirstSearch("A", "I");
        List<String> expected = List.of("A", "B", "E", "F", "H", "I");

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void DFSdisconnectedGraph() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "E");
        graph.addEdge("B", "F");
        graph.addEdge("E", "Z");
        graph.addEdge("C", "D");

        List<String> emptyPath = graph.depthFirstSearch("A", "D");
        assertTrue(emptyPath.isEmpty());

        List<String> actualPath = graph.depthFirstSearch("A", "Z");
        List<String> expected = List.of("A", "B", "E", "Z");

        assertEquals(expected.size(), actualPath.size());
        assertEquals(expected, actualPath);
        assertFalse(actualPath.isEmpty());
    }

    @Test
    void DFSBFSimproperInputs() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "E");
        graph.addEdge("B", "D");

        assertThrows(IllegalArgumentException.class, () -> { graph.depthFirstSearch("A", "Z"); });
        assertThrows(IllegalArgumentException.class, () -> { graph.breadthFirstSearch("A", "Z"); });
    }

    @Test
    void DFSCycles() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("C", "D");

        List<String> actual = graph.depthFirstSearch("A", "D");
        List<String> expected = List.of("A", "B", "C", "D");

        assertEquals(expected, actual);
    }

    @Test
    void testDFSSingleVertex() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "A");

        List<String> actual = graph.depthFirstSearch("A", "A");
        List<String> expected = List.of("A");

        assertEquals(expected, actual);
    }

    @Test
    void BreadthFirstSearchRegular() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("B", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");
        graph.addEdge("F", "H");
        graph.addEdge("H", "I");

        List<String> actual = graph.breadthFirstSearch("A", "I");
        List<String> expected = List.of("A", "B", "E", "F", "H", "I");

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }
    @Test
    void BreadthFirstSearchRegularInts() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 8);
        graph.addEdge(8, 9);

        List<Integer> actual = graph.breadthFirstSearch(1, 9);
        List<Integer> expected = List.of(1, 2, 5, 6, 8, 9);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void BFSEnsureShortestPath() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("B", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");
        graph.addEdge("F", "H");
        graph.addEdge("H", "I");
        graph.addEdge("D", "I");

        List<String> actual = graph.breadthFirstSearch("A", "I");
        List<String> expected = List.of("A", "C", "D", "I");

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void BFSEnsureShortestPathCycleAndTwoShortestPaths() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("B", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");
        graph.addEdge("F", "H");
        graph.addEdge("H", "I");
        graph.addEdge("D", "I");
        graph.addEdge("B", "D");

        List<String> actual = graph.breadthFirstSearch("A", "I");
        List<List<String>> expectedOrders = List.of(
                List.of("A", "C", "D", "I"),
                List.of("A", "B", "D", "I")
        );

        assertEquals(4, actual.size());
        assertTrue(expectedOrders.contains(actual));
    }

    @Test
    void topologicalSort() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> actual = graph.topologicalSort();
        List<String> expected = List.of("A", "B", "C", "D");

        assertEquals(expected, actual);
    }

    @Test
    void topologicalSortMultipleOrders() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        List<String> actual = graph.topologicalSort();
        List<List<String>> expectedOrders = List.of(
                List.of("A", "B", "C", "D"),
                List.of("A", "C", "B", "D")
        );

        assertTrue(expectedOrders.contains(actual));
    }

    @Test
    void topologicalSortWithCycle() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertThrows(IllegalArgumentException.class, graph::topologicalSort);
    }
    @Test
    void testEmptyGraph() {
        Graph<String> graph = new Graph<>();
        assertThrows(IllegalArgumentException.class, () -> {
            graph.depthFirstSearch("A", "B");
        });
        assertTrue(graph.topologicalSort().isEmpty());
    }

    @Test
    void testSingleNodeGraph() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "A");

        List<String> path = graph.depthFirstSearch("A", "A");
        List<String> expectedPath = List.of("A");
        assertEquals(expectedPath, path);

        List<String> sorted = graph.topologicalSort();
        List<String> expectedSorted = List.of("A");
        assertEquals(expectedSorted, sorted);
    }

    @Test
    void testGraphWithParallelEdges() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "B"); // Parallel edge
        graph.addEdge("B", "C");

        List<String> path = graph.depthFirstSearch("A", "C");
        List<String> expectedPath = List.of("A", "B", "C");
        assertEquals(expectedPath, path);

        List<String> sorted = graph.topologicalSort();
        List<String> expectedSorted = List.of("A", "B", "C");
        assertEquals(expectedSorted, sorted);
    }
    @Test
    void testGraphWithNoEdges() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B");
        List<String> path = graph.depthFirstSearch("A", "B");
        List<String> expectedPath = List.of("A", "B");
        assertEquals(expectedPath, path);

        List<String> sorted = graph.topologicalSort();
        List<String> expectedSorted = List.of("A", "B");
        assertEquals(expectedSorted, sorted);
    }
}