package sol;

import src.NodeNameExistsException;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the interface a graph. Implemented by EdgeArrayGraph and NodeEdgeGraph, which are both different types of
 * graphs.
 */
public interface IGraph {
    /**
     * This method adds a node to the graph and gives it a name.
     * @param descr - node name
     * @throws NodeNameExistsException
     */
    void addNode(String descr) throws NodeNameExistsException;

    /**
     * This method adds a one-direction connection between two nodes.
     * @param descr1 - start node
     * @param descr2 - end node
     */
    void addDirectedEdge(String descr1, String descr2);

    /**
     * This method adds a two-direction connection between two nodes.
     * @param descr1 - start node
     * @param descr2 - end node
     */
    void addUndirectedEdge (String descr1, String descr2);

    /**
     * This method returns the number of nodes that have an edge pointing to itself.
     * @return int number of nodes in graph with self edges
     */
    int countSelfEdges();

    /**
     * This method returns true if the parameter node can directly reach all other nodes in the graph. If there
     * are no other nodes, then it returns true.
     * @param fromNodeLabel - starting node
     * @return true if starting node can reach every other node
     */
    boolean reachesAllOthers(String fromNodeLabel);

    /**
     * This method gets and returns a list of neighbors from a given node.
     * @param node - name of node
     * @return list of neighbor node names
     */
    List<String> getNeighbors(String node);

    /**
     * This method gets the total number of labs that need to be assigned.
     * @return int number of total labs in the graph
     */
    int getTotalLabs();

    /**
     * This method gets a list of all the node names in a graph.
     * @return arrayList of names of all existing nodes
     */
    ArrayList<String> allNodes();

}
