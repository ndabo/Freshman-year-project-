package test;

import org.junit.Assert;
import org.junit.Test;

import sol.EdgeArrayGraph;
import sol.GraphUtils;
import sol.IGraph;
import sol.NodeEdgeGraph;
import src.NoRouteException;
import src.NodeNameExistsException;

import java.util.LinkedList;

public class GraphUtilsTest {
    // Assumes that graph will be empty, modifies it in-place
    private void addSimpleGraphNodes(IGraph graph) throws NodeNameExistsException {
        graph.addNode("node 1");
        graph.addNode("node 2");
        graph.addNode("node 3");
        graph.addNode("node 4");
        graph.addNode("node 5");
    }



    // Assumes that graph will have nodes from `addSimpleGraphNodes`,
    //     modifies it in-place
    private void addSimpleGraphEdges(IGraph graph) {
        graph.addDirectedEdge("node 1", "node 2");
        graph.addDirectedEdge("node 2", "node 3");
    }

    // Assumes that graph will be empty, modifies it in-place
    private void makeSimpleGraph(IGraph graph) throws NodeNameExistsException {
        addSimpleGraphNodes(graph);
        addSimpleGraphEdges(graph);
    }

    private void makegraph1(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addDirectedEdge("node 2", "node 3");
        graph.addDirectedEdge("node 2", "node 1");
        graph.addDirectedEdge("node 2", "node 4");
    }

    private void makeGraph2(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addDirectedEdge("node 2", "node 3");
        graph.addDirectedEdge("node 2", "node 1");
        graph.addDirectedEdge("node 2", "node 4");
        graph.addDirectedEdge("node 2", "node 5");
        graph.addDirectedEdge("node 3", "node 4");
        graph.addDirectedEdge("node 1", "node 4");
        graph.addDirectedEdge("node 1", "node 3");
    }
    private void makeGraph3(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addDirectedEdge("node 2", "node 2");
        graph.addDirectedEdge("node 3", "node 3");
        graph.addDirectedEdge("node 3", "node 4");
        graph.addDirectedEdge("node 1", "node 4");
        graph.addDirectedEdge("node 1", "node 3");
    }
    private void makegraph4(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addUndirectedEdge("node 1", "node 3");
        graph.addUndirectedEdge("node 2", "node 1");
        graph.addUndirectedEdge("node 2", "node 4");
        graph.addUndirectedEdge("node 4", "node 5");

        graph.addNode("node 6");
        graph.addNode("node 7");
        graph.addNode("node 8");
        graph.addUndirectedEdge("node 5", "node 6");
        graph.addUndirectedEdge("node 6", "node 7");
        graph.addUndirectedEdge("node 7", "node 8");
    }

    private void makeGraph6(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addDirectedEdge("node 1", "node 2");
        graph.addDirectedEdge("node 1", "node 3");
        graph.addDirectedEdge("node 1", "node 4");
        graph.addDirectedEdge("node 1", "node 5");
        graph.addDirectedEdge("node 5", "node 1");
    }
    private void makeGraph7(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addDirectedEdge("node 2", "node 3");
        graph.addDirectedEdge("node 2", "node 2");
        graph.addDirectedEdge("node 2", "node 1");
        graph.addDirectedEdge("node 2", "node 4");
        graph.addDirectedEdge("node 2", "node 5");
        graph.addDirectedEdge("node 3", "node 4");
        graph.addDirectedEdge("node 1", "node 4");
        graph.addDirectedEdge("node 1", "node 3");
    }
    private void makeGraph8(IGraph graph) throws NodeNameExistsException{
        addSimpleGraphNodes(graph);
        graph.addDirectedEdge("node 1", "node 2");
        graph.addDirectedEdge("node 2", "node 1");
        graph.addDirectedEdge("node 2", "node 3");
        graph.addDirectedEdge("node 3", "node 4");
        graph.addDirectedEdge("node 4", "node 5");
    }


    @Test
    public void testGetRouteSimple(){
        try {
            NodeEdgeGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);

            String fromNode = "node 1";
            String toNode = "node 3";
            LinkedList<String> route = GraphUtils.getRoute(simpleGraph, fromNode, toNode);

            LinkedList<String> expectedRoute = new LinkedList<>();
            expectedRoute.add("node 1");
            expectedRoute.add("node 2");
            expectedRoute.add("node 3");

            Assert.assertEquals(expectedRoute, route);
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }

    @Test
    public void testGetRoute(){
        //no route
        try {
            EdgeArrayGraph graph = new EdgeArrayGraph("graph");
            makegraph1(graph);
            String fromNode = "node 3";
            String toNode = "node 4";

            String fromNode2 = "node 2";
            //no route
            Assert.assertThrows(
                    NoRouteException.class,
                    () -> GraphUtils.getRoute(graph, fromNode, toNode));

            //route
            LinkedList<String> expectedRoute = new LinkedList<>();
            expectedRoute.add("node 2");
            expectedRoute.add("node 4");

            Assert.assertEquals(expectedRoute, GraphUtils.getRoute(graph, fromNode2, toNode));
        }
        catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }
    @Test
    public void testGetRouteUndirected(){
        try {
            EdgeArrayGraph graph = new EdgeArrayGraph("graph");
            makegraph4(graph);
            String fromNode = "node 1";
            String toNode = "node 8";

            LinkedList<String> expectedRoute = new LinkedList<>();
            expectedRoute.add("node 1");
            expectedRoute.add("node 2");
            expectedRoute.add("node 4");
            expectedRoute.add("node 5");
            expectedRoute.add("node 6");
            expectedRoute.add("node 7");
            expectedRoute.add("node 8");

            Assert.assertEquals(expectedRoute, GraphUtils.getRoute(graph, fromNode, toNode));
        }
        catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }


    @Test
    public void testGetRouteSimpleNoRoute(){
        try {
            NodeEdgeGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);
            String fromNode = "node 1";
            String toNode = "node 4";
            Assert.assertThrows(
                    NoRouteException.class,
                    () -> GraphUtils.getRoute(simpleGraph, fromNode, toNode));
        }
        catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }

    @Test
    public void testCountSelfEdgesSimple() {
        try {
            IGraph basicGraph = new NodeEdgeGraph("a graph");
            basicGraph.addNode("node 1");
            basicGraph.addDirectedEdge("node 1", "node 1");
            Assert.assertEquals(1, basicGraph.countSelfEdges());
        } catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }

    @Test
    public void testSelfEdges(){
        try {
            IGraph simpleGraph = new EdgeArrayGraph("graph 2");
            makeSimpleGraph(simpleGraph);

            IGraph basicGraph = new EdgeArrayGraph("graph");
            makeGraph3(basicGraph);


            IGraph intermediateGraph = new EdgeArrayGraph("graph");

            makeGraph8(intermediateGraph);

            Assert.assertEquals(2, basicGraph.countSelfEdges());
            Assert.assertEquals(0, simpleGraph.countSelfEdges());
            Assert.assertEquals(0, intermediateGraph.countSelfEdges());
        } catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }

    @Test
    public void testReachesAllOthers(){
        try {
            IGraph graph = new EdgeArrayGraph("graph");
            makeGraph2(graph);

            IGraph myGraph = new NodeEdgeGraph("b graph");
            makeGraph2(myGraph);

            Assert.assertTrue(graph.reachesAllOthers("node 2"));
            Assert.assertFalse(graph.reachesAllOthers("node 4"));

            Assert.assertTrue(myGraph.reachesAllOthers("node 2"));
            Assert.assertFalse(myGraph.reachesAllOthers("node 4"));
        }catch (NodeNameExistsException e){
            Assert.fail("Could not create graph to test");
        }
    }
    @Test
    public void testReachesAllOthersSameNode(){
        try {
            IGraph graph = new EdgeArrayGraph("graph");
            makeGraph7(graph);

            Assert.assertTrue(graph.reachesAllOthers("node 2"));
            Assert.assertFalse(graph.reachesAllOthers("node 4"));
        }catch (NodeNameExistsException e){
            Assert.fail("Could not create graph to test");
        }
    }
    @Test
    public void testReachesAllOthersNoEdge(){
        try {
            IGraph graph = new EdgeArrayGraph("graph");
            graph.addNode("node 1");
            Assert.assertTrue(graph.reachesAllOthers("node 1"));

            graph.addNode("node 2");
            Assert.assertFalse(graph.reachesAllOthers("node 1"));
            Assert.assertFalse(graph.reachesAllOthers("node 2"));

            graph.addUndirectedEdge("node 1", "node 2");
            Assert.assertTrue(graph.reachesAllOthers("node 1"));
            Assert.assertTrue(graph.reachesAllOthers("node 2"));
        }catch (NodeNameExistsException e){
            Assert.fail("Could not create graph to test");
        }
    }
    @Test
    public void testReachesAllOthersNodeLater() throws NodeNameExistsException {
        IGraph myGraph = new NodeEdgeGraph("b graph");
        this.makeGraph6(myGraph);
        Assert.assertTrue(myGraph.reachesAllOthers("node 1"));
        myGraph.addNode("node 6");
        Assert.assertFalse(myGraph.reachesAllOthers("node 1"));
        myGraph.addDirectedEdge("node 1", "node 6");
        Assert.assertTrue(myGraph.reachesAllOthers("node 1"));

        myGraph.addNode("node 7");
        myGraph.addUndirectedEdge("node 1", "node 7");
        Assert.assertTrue(myGraph.reachesAllOthers("node 1"));

        IGraph graph = new NodeEdgeGraph("b graph");
        this.makeGraph6(graph);
        Assert.assertTrue(graph.reachesAllOthers("node 1"));
        graph.addNode("node 6");
        Assert.assertFalse(graph.reachesAllOthers("node 1"));
    }

    @Test
    public void testReachesAllOthersSimple(){
        IGraph basicGraph = new NodeEdgeGraph("b graph");
        basicGraph.addUndirectedEdge("node 1", "node 2");
        Assert.assertTrue(basicGraph.reachesAllOthers("node 1"));
        Assert.assertTrue(basicGraph.reachesAllOthers("node 2"));
    }
    @Test
    public void testHasRoute(){
            try {
                NodeEdgeGraph graph = new NodeEdgeGraph("b graph");
                makeGraph2(graph);
                Assert.assertFalse(GraphUtils.hasRoute(graph,"node 1", "node 2"));
                Assert.assertTrue(GraphUtils.hasRoute(graph,"node 2", "node 4"));
                Assert.assertTrue(GraphUtils.hasRoute(graph,"node 2", "node 4"));
            }catch (NodeNameExistsException e){
                Assert.fail("Could not create graph to test");
            }
    }
}
