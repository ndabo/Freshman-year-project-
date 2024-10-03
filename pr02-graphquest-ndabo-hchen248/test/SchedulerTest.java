package test;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;

import sol.EdgeArrayGraph;
import sol.IGraph;
import sol.NodeEdgeGraph;
import sol.Scheduler;
import src.NoScheduleException;
import src.NodeNameExistsException;

public class SchedulerTest {
    // Assumes that graph will be empty, modifies it in-place
    private void addSimpleGraphNodes(IGraph graph) throws NodeNameExistsException {
        graph.addNode("lab 1");
        graph.addNode("lab 2");
        graph.addNode("lab 3");
        graph.addNode("lab 4");
        graph.addNode("lab 5");
    }

    // Assumes that graph will have nodes from `addSimpleGraphNodes`,
    //     modifies it in-place
    private void addSimpleGraphEdges(IGraph graph) {
        graph.addUndirectedEdge("lab 1", "lab 2");
        graph.addUndirectedEdge("lab 2", "lab 3");
    }

    // Assumes that graph will be empty, modifies it in-place
    private void makeSimpleGraph(IGraph graph) throws NodeNameExistsException {
        addSimpleGraphNodes(graph);
        addSimpleGraphEdges(graph);
    }
    private void addGraphEdges2(IGraph graph) {
        graph.addUndirectedEdge("lab 1", "lab 2");
        graph.addUndirectedEdge("lab 2", "lab 3");
        graph.addUndirectedEdge("lab 2", "lab 4");
        graph.addUndirectedEdge("lab 2", "lab 5");
    }

    @Test
    public void testCheckValidityTrue() {
        try{
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);

            ArrayList<HashSet<String>> proposedSchedule = new ArrayList<>();
            HashSet<String> set1 = new HashSet<>();
            set1.add("lab 1");
            set1.add("lab 3");
            set1.add("lab 4");
            proposedSchedule.add(set1);
            HashSet<String> set2 = new HashSet<>();
            set2.add("lab 2");
            set2.add("lab 5");
            proposedSchedule.add(set2);

            Assert.assertTrue(Scheduler.checkValidity(simpleGraph, proposedSchedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        }
    }
    @Test
    public void testCheckValidityEdgeCase1() {
        try{
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);

            ArrayList<HashSet<String>> proposedSchedule = new ArrayList<>();
            HashSet<String> set1 = new HashSet<>();
            set1.add("lab 1");
            set1.add("lab 3");
            set1.add("lab 4");
            set1.add("lab 5");
            proposedSchedule.add(set1);
            HashSet<String> set2 = new HashSet<>();
            set2.add("lab 2");
            proposedSchedule.add(set2);

            Assert.assertTrue(Scheduler.checkValidity(simpleGraph, proposedSchedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        }
    }

    @Test
    public void testCheckValidityFalse() {
        try{
            IGraph myGraph = new NodeEdgeGraph("a graph");
            addSimpleGraphNodes(myGraph);
            addGraphEdges2(myGraph);

            ArrayList<HashSet<String>> proposedSchedule = new ArrayList<>();
            HashSet<String> set1 = new HashSet<>();
            set1.add("lab 3");
            set1.add("lab 4");
            proposedSchedule.add(set1);
            HashSet<String> set2 = new HashSet<>();
            set2.add("lab 1");
            set2.add("lab 2");
            proposedSchedule.add(set2);

            Assert.assertFalse(Scheduler.checkValidity(myGraph, proposedSchedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        }

    }

    @Test
    public void testFindScheduleValid(){
        try {
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);
            ArrayList<HashSet<String>> schedule = Scheduler.findSchedule(simpleGraph);
            Assert.assertTrue(Scheduler.checkValidity(simpleGraph, schedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoScheduleException e) {
            Assert.fail("findSchedule did not find a schedule");
        }
    }
    @Test
    public void testFindScheduleEmptyGraph(){
        try {
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            ArrayList<HashSet<String>> schedule = Scheduler.findSchedule(simpleGraph);
            Assert.assertTrue(Scheduler.checkValidity(simpleGraph, schedule));
        }
        catch (NoScheduleException e) {
            Assert.fail("findSchedule did not find a schedule");
        }
    }

    @Test
    public void testFindScheduleInvalid(){
        try {
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);
            simpleGraph.addUndirectedEdge("lab 3", "lab 1"); // This edge should make it impossible to schedule
            Assert.assertThrows(
                   NoScheduleException.class,
                   () -> Scheduler.findSchedule(simpleGraph));
   }
        catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }
}
