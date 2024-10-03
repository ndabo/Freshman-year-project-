package sol;

import src.NoRouteException;

import java.util.*;

/**
 * This class is responsible for managing and searching through the different routes of nodes.
 */
public class GraphUtils {

    /**
     * Method to use breadth-first-search to check whether there is a path
     *     from one node to another in a graph. Assumes that both fromNodeLabel
     *     and toNodeLabel are valid node labels in theGraph.
     *
     * @param theGraph the graph to traverse
     * @param fromNodeLabel name of the node from which to start searching
     * @param toNodeLabel   name of the node we want to reach
     * @return boolean indicating whether such a route exists
     */
    public static boolean hasRoute(NodeEdgeGraph theGraph, String fromNodeLabel, String toNodeLabel) {
        NodeEdgeGraph.Node fromNode = theGraph.getNode(fromNodeLabel);
        NodeEdgeGraph.Node toNode = theGraph.getNode(toNodeLabel);
        // set up and initialize data structures
        HashSet<NodeEdgeGraph.Node> visited = new HashSet<>();
        LinkedList<NodeEdgeGraph.Node> toCheck = new LinkedList<>();
        toCheck.add(fromNode);

        // process nodes to search for toNode
        while (!toCheck.isEmpty()) {
            NodeEdgeGraph.Node checkNode = toCheck.pop();
            if (checkNode.equals(toNode)) {
                return true;
            } else if (!visited.contains(checkNode)) {
                visited.add(checkNode);
                toCheck.addAll(checkNode.nextNodes);
            }
        }
        return false;
    }

    /**
     * Method to produce a sequence of nodes that constitutes a shortest path
     *     from fromNodeLabel to toNodeLabel. Assumes that both fromNodeLabel
     *     and toNodeLabel are valid node labels in theGraph.
     * Throws a NoRouteException if no such path exists
     *
     * @param theGraph the graph to traverse
     * @param fromNodeLabel the node from which to start searching
     * @param toNodeLabel   the node we want to reach
     * @return List of nodes in order of the path
     * @throws NoRouteException if no such path exists
     */

    public static LinkedList<String> getRoute(IGraph theGraph, String fromNodeLabel, String toNodeLabel)
            throws NoRouteException {
        // set up and initialize data structures
        HashSet<String> visited = new HashSet<>();
        Queue<String> toCheck = new LinkedList<>();
        //keep track of which node a visited node is coming from
        HashMap<String,String> parent = new HashMap<>();
        //add the starting point to the list
        toCheck.add(fromNodeLabel);

        // process nodes to search for toNode
        while (!toCheck.isEmpty()) {
            String checkNode = toCheck.poll();

            if (checkNode.equals(toNodeLabel)) {
                LinkedList<String> route = new LinkedList<>();
                String curNode = toNodeLabel;
                while(!curNode.equals(fromNodeLabel)){
                    route.addFirst(curNode);
                    curNode = parent.get(curNode);
                }
                route.addFirst(fromNodeLabel);
                return route;
            }

            if (!visited.contains(checkNode)) {
                visited.add(checkNode);
                List<String> neigh = theGraph.getNeighbors(checkNode);
                for(String n: neigh){
                    if(!visited.contains(n)){
                    toCheck.add(n);
                    parent.put(n,checkNode);
                    }
                }
            }
        }
        throw  new NoRouteException();
    }
}