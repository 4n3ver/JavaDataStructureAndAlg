import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * {@link GraphAlgorithms} assortment of some basic graph algorithm.
 *
 * @author Yoel Ivan
 * @version 0.0a
 */
public class GraphAlgorithms {

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph. You should use the adjacency list
     * representation of the graph.
     * <p>
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be visited. There are no negative edge weights in the
     * graph.
     * <p>
     * If there is no path from from the start vertex to a given vertex, have
     * the distance be INF as seen in the graphs class.
     *
     * @param graph the graph to search
     * @param start the starting vertex
     * @return map of the shortest distance between start and all other
     * vertices
     * @throws IllegalArgumentException if graph or start vertex is null
     */
    public static Map<Vertex, Integer> dijkstraShortestPath(Graph graph,
                                                            Vertex start) {
        validateArgs(graph, start);

        // put every vertex to distanceMap and init dist to INF
        Map<Vertex, Integer> distanceMap = new HashMap<>();
        for (Vertex vertex : graph.getVertices()) {
            distanceMap.put(vertex, Graph.INF);
        }
        distanceMap.put(start, 0);  // set distance of starting vertex to 0

        /* create a set to mark vertex as visited */
        Set<Vertex> visited = new HashSet<>();

        PriorityQueue<VertexDistancePair> nearbyForest = new PriorityQueue<>();
        nearbyForest.add(new VertexDistancePair(start, distanceMap.get(
            start)));

        while (!nearbyForest.isEmpty()) {    // happy exploring!!
            VertexDistancePair curr = nearbyForest.remove();
            visited.add(curr.getVertex());  // mark vertex as visited

            if (graph.getAdjacencies(curr.getVertex()) != null) {
                graph.getAdjacencies(curr.getVertex()).entrySet().stream()
                    .filter(adj -> !visited.contains(adj.getKey())).forEach(
                    adj -> {
                            /* if node has not been visited */
                            int newDist = Math.min(distanceMap.get(
                                adj.getKey()), curr.getDistance()
                                + adj.getValue());

                            // update distance
                            distanceMap.put(adj.getKey(), newDist);
                            nearbyForest.add(new VertexDistancePair(
                                adj.getKey(), newDist));
                        });
            }

            /*
             * since {@link PriorityQueue) does not support update element
             * efficiently, there might be multiple copy of the same vertex
             * with varying distances, the smallest distance is guaranteed to
             * be visited, and the rest is removed (by the following block)
             */
            while (!nearbyForest.isEmpty() && visited.contains(
                nearbyForest.peek().getVertex())) {
                nearbyForest.remove();
            }
        }
        return distanceMap;
    }

    /**
     * The usual method to make sure all the args passed are not
     * <code>null</code>.
     *
     * @param args any objects to be validated
     */
    private static void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("passing in null");
            }
        }
    }

    /**
     * Run Floyd Warshall on the given graph to find the length of all of the
     * shortest paths between vertices.
     * <p>
     * You will also detect if there are negative cycles in the given graph.
     * <p>
     * You should use the adjacency matrix representation of the graph.
     * <p>
     * If there is no path from from the start vertex to a given vertex, have
     * the distance be INF as seen in the graphs class.
     *
     * @param graph the graph
     * @return the distances between each vertex. For example, given {@code
     * arr} represents the 2D array, {@code arr[i][j]} represents the distance
     * from vertex i to vertex j. Return {@code null} if there is a negative
     * cycle.
     * @throws IllegalArgumentException if graph is null
     */
    public static int[][] floydWarshall(Graph graph) {
        validateArgs(graph);
        int size = graph.getVertices().size();
        int[][] distTable = new int[size][size];
        for (int row = 0; row < distTable.length; row++) {
            for (int col = 0; col < distTable.length; col++) {
                distTable[row][col] = graph.getAdjacencyMatrix()[row][col];
            }
        }

        for (int i = 0; i < distTable.length; i++) {
            for (int j = 0; j < distTable.length; j++) {
                for (int k = 0; k < distTable.length; k++) {
                    int trans = distTable[i][j] + distTable[j][k];
                    if ((i == j && j == k || i != j && j != k) && trans < 0) {
                        return null;    // negative cycle
                    } else {
                        distTable[i][k] = Math.min(trans, distTable[i][k]);
                    }
                }
            }
        }
        return distTable;
    }

    /**
     * A topological sort is a linear ordering of vertices with the restriction
     * that for every edge uv, vertex u comes before v in the ordering.
     * <p>
     * You should use the adjacency list representation of the graph. When
     * considering which vertex to visit next while exploring the graph, choose
     * the next vertex in the adjacency list.
     * <p>
     * You should start your topological sort with the smallest vertex and if
     * you need to select another starting vertex to continue with your sort
     * (like with a disconnected graph), you should choose the next smallest
     * applicable vertex.
     *
     * @param graph a directed acyclic graph
     * @return a topological sort of the graph
     * @throws IllegalArgumentException if the graph is null
     */
    public static List<Vertex> topologicalSort(Graph graph) {
        validateArgs(graph);
        LinkedList<Vertex> res = new LinkedList<>();
        Set<Vertex> unvisited = new HashSet<>(graph.getVertices());
        while (!unvisited.isEmpty()) {
            Vertex min = null;
            for (Vertex vertex : unvisited) {
                if (min == null || min.compareTo(vertex) > 0) {
                    min = vertex;
                }
            }
            topologicalSortHelper(graph, min, unvisited, res);
        }
        return res;
    }

    /**
     * Recursive helper method for DFS based topological sort.
     *
     * @param graph     graph to be explored
     * @param current   current visited vertex
     * @param unvisited list of univisited vertex
     * @param res       list of vertex ordered topologically
     */
    private static void topologicalSortHelper(Graph graph, Vertex current,
                                              Set<Vertex> unvisited,
                                              List<Vertex> res) {
        if (current != null) {
            unvisited.remove(current);
            if (graph.getAdjacencies(current) != null) {
                graph.getAdjacencies(current).entrySet().stream().filter(
                    entry -> unvisited.contains(entry.getKey())).forEach(
                    entry -> topologicalSortHelper(graph, entry.getKey(),
                        unvisited, res));
            }
            res.add(0, current);
        }
    }

    /**
     * A class that pairs a vertex and a distance. Hint: might be helpful for
     * Dijkstra's.
     */
    private static class VertexDistancePair
        implements Comparable<VertexDistancePair> {
        private Vertex vertex;
        private int    distance;

        /**
         * Creates a vertex distance pair
         *
         * @param vertex   the vertex to store in this pair
         * @param distance the distance to store in this pair
         */
        public VertexDistancePair(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        /**
         * Gets the vertex stored in this pair
         *
         * @return the vertex stored in this pair
         */
        public Vertex getVertex() {
            return vertex;
        }

        /**
         * Sets the vertex to be stored in this pair
         *
         * @param vertex the vertex to be stored in this pair
         */
        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        @Override
        public int compareTo(VertexDistancePair v) {
            return (distance < v.getDistance() ? -1
                : distance > v.getDistance() ? 1 : 0);
        }

        /**
         * Gets the distance stored in this pair
         *
         * @return the distance stored in this pair
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Sets the distance to be stored in this pair
         *
         * @param distance the distance to be stored in this pair
         */
        public void setDistance(int distance) {
            this.distance = distance;
        }
    }
}