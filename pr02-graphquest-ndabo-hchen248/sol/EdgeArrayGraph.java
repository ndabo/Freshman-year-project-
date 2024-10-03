package sol;

import src.NodeNameExistsException;

import java.util.*;

/**
 * This class represents a 2D array of booleans that track the edges of each city.
 */
public class EdgeArrayGraph implements IGraph {
    String name;
    ArrayList<ArrayList<Boolean>> matrix;
    HashMap<String,Integer> idxmap;
    ArrayList<String> nodes;


    /**
     * Constructor for EdgeArrayGraph.
     * @param - EdgeArrayGraph name
     */
    public EdgeArrayGraph(String name) {
        this.matrix = new ArrayList<ArrayList<Boolean>>();
        this.idxmap = new HashMap<>();
        this.nodes = new ArrayList<>();
        this.name = name;
    }

    /**
     * Method to add a new node with the given description. An exception will
     * be thrown if the description already names a node in the graph
     *
     * @param descr the text description or label to associate with the node
     * @throws NodeNameExistsException if that description is already
     * associated with a node in the graph
     */
    public void addNode(String descr) throws NodeNameExistsException {
        if (this.nodes.contains(descr))
            throw new NodeNameExistsException(descr);
        this.nodes.add(descr);

        for(ArrayList<Boolean> row: this.matrix){
            row.add(false);
        }

        ArrayList<Boolean> newRow = new ArrayList<>();
        for (int i = 0; i < this.matrix.size() + 1; i++) {
            newRow.add(false);
        }
        this.idxmap.put(descr, this.matrix.size());
        this.matrix.add(newRow);
    }

    /**
     * Method to add a directed edge between the nodes associated with the given
     * descriptions. If descr1 and descr2 are not already
     * valid node labels in the graph, those nodes are also created.
     * If the edge already exists, no changes are made
     * (and no exceptions or warnings are raised)
     *
     * @param descr1 the source node for the edge
     * @param descr2 the target node for the edge
     */
    public void addDirectedEdge(String descr1, String descr2) {
        try {
            this.addNode(descr1);
        } catch (NodeNameExistsException ignored) {}

        try {
            this.addNode(descr2);
        } catch (NodeNameExistsException ignored) {}

        int idx1 = this.idxmap.get(descr1);
        int idx2 = this.idxmap.get(descr2);

        this.matrix.get(idx1).set(idx2, true);
    }

    /**
     * Method to add an undirected edge between the nodes associated with the given
     * descriptions. This is equivalent to adding two directed edges, one from
     * descr1 to descr2, and another from descr2 to descr1.
     * If descr1 and descr2 are not already valid node labels in the graph,
     * those nodes are also created.
     *
     * @param descr1 the source node for the edge
     * @param descr2 the target node for the edge
     */
    public void addUndirectedEdge(String descr1, String descr2) {
        this.addDirectedEdge(descr1, descr2);
        this.addDirectedEdge(descr2,descr1);
    }

    /**
     * Method to count how many nodes have edges to themselves
     *
     * @return the number of nodes that have edges to themselves
     */
    public int countSelfEdges() {
        int count = 0;
        for(String node: this.nodes){
            int idx  = this.idxmap.get(node);
            if (idx >= 0 && idx < this.matrix.size()){
                if(this.matrix.get(idx).get(idx)){
                    count++;
            }
        }
        }
        return count;
    }
    /*
    the runtime here is O(n) because we are going through the whole list.
     */

    /**
     * Method to check whether a given node has edges to every other node (with or without an edge to itself).
     * Assumes that fromNodeLabel is a valid node label in the graph.
     *
     * @param fromNodeLabel the node to check
     * @return true if fromNodeLabel has an edge to every other node, otherwise false
     */
    public boolean reachesAllOthers(String fromNodeLabel) {
        int idx = this.idxmap.get(fromNodeLabel);
        for (int i = 0; i < this.matrix.size(); i++) {
            if(i != idx && !this.matrix.get(idx).get(i)){
                return false;
            }
        }
        return true;
    }
    /*
    the runtime here is O(n) where n is the number of node in the graph
     */

    @Override
    public List<String> getNeighbors(String node) {
        List<String> connectedToNode = new ArrayList<>();
        int curIdx = this.idxmap.get(node);
        if (curIdx < 0 || curIdx >= this.matrix.size()) {
            return connectedToNode;
        }
        for (String neigh : this.nodes) {
            if (neigh.equals(node)) {
                continue;
            }
            int idx = this.idxmap.get(neigh);
            if (idx < 0 || idx >= this.matrix.size() || this.matrix.get(idx) == null || this.matrix.get(curIdx).get(idx)== null) {
                continue; // Skip if neighbor node index is invalid or matrix element is null
            }
            if (this.matrix.get(curIdx).get(idx)) {
                connectedToNode.add(neigh);
            }
        }
        return connectedToNode;
    }

    @Override
    public int getTotalLabs() {
        return this.nodes.size();
    }

    @Override
    public ArrayList<String> allNodes() {
        return this.nodes;
    }
}
