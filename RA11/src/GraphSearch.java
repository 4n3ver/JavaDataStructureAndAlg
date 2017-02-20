import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Graph Search Recitation 11
 * <p>
 * Also, you do not have to run CheckStyle on this assignment. But make it look
 * pretty.  Pretty please. With a cherry on top.
 * <p>
 * If you don't know what to return for something, look at the JUnits. They
 * should tell you what they expect if null is passed in, etc. If there's not a
 * test case for it, then don't worry about it! :)
 *
 * @author Yoel Ivan
 */
public class GraphSearch {

    /**
     * Searches the Graph passed in as an AdjcencyList(adjList) to find if a
     * path exists from the start node to the goal node using Breadth First
     * Search.
     * <p>
     * Assume the AdjacencyList contains adjacent nodes of each node in the
     * order they should be added to the Structure.
     *
     * @param start
     * @param adjList
     * @param goal
     * @return true if path exists, false otherwise
     */
    public static <T> boolean breadthFirstSearch(T start,
                                                 Map<T, List<T>> adjList,
                                                 T goal) {
        // TODO Implement Breadth First Search here
        // Hint: This can be accomplished in one line.
        return generalGraphSearch(start, new StructureQueue<T>(), adjList,
                goal);
    }

    /**
     * Searches the Graph passed in as an AdjcencyList(adjList) to find if a
     * path exists from the start node to the goal node using General Graph
     * Search.
     * <p>
     * Assume the AdjacencyList contains adjacent nodes of each node in the
     * order they should be added to the Structure.
     * <p>
     * The structure(struct) passed in is an empty structure may behave as a
     * Stack or Queue and the correspondingly search function should execute
     * DFS(Stack) or BFS(Queue) on the graph.
     * <p>
     * We've written the stack and queue for you.  Your mission, should you
     * choose to accept it (and you should), is to finish the graph search
     * algorithm.
     *
     * @param start
     * @param struct
     * @param adjList
     * @param goal
     * @return true if path exists false otherwise
     */
    public static <T> boolean generalGraphSearch(T start, Structure<T> struct,
                                                 Map<T, List<T>> adjList,
                                                 T goal) {
        // TODO Implement General Graph Search here
        validateArgs(start, struct, adjList, goal);
        Structure<T> compass = struct;
        Set<T> visitMark = new HashSet<>();
        compass.add(start);
        while (!compass.isEmpty()) {
            T current = compass.remove();
            if (!visitMark.contains(current)) {
                visitMark.add(current);
                if (current.equals(goal)) {
                    return true;
                } else if (adjList.get(current) != null) {
                    adjList.get(current).forEach(compass::add);
                }
            }
        }
        return false;
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
     * Searches the Graph passed in as an AdjcencyList(adjList) to find if a
     * path exists from the start node to the goal node using Depth First
     * Search.
     * <p>
     * Assume the AdjacencyList contains adjacent nodes of each node in the
     * order they should be added to the Structure.
     *
     * @param start
     * @param adjList
     * @param goal
     * @return true if path exists, false otherwise
     */
    public static <T> boolean depthFirstSearch(T start,
                                               Map<T, List<T>> adjList,
                                               T goal) {
        // TODO Implement Depth First Search here
        // Hint: This can be accomplished in one line.
        return generalGraphSearch(start, new StructureStack<T>(), adjList,
                goal);
    }

}
