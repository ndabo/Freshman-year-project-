package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A class containing the tests for methods in the TreeGenerator and Dataset classes
 */
public class DecisionTreeTest {
    //TODO: Write more unit and system tests! Some basic guidelines that we will be looking for:
    // 1. Small unit tests on the Dataset class testing the IDataset methods
    // 2. Small unit tests on the TreeGenerator class that test the ITreeGenerator methods
    // 3. Tests on your own small dataset (expect 70% accuracy on testing data, 95% on training data)
    // 4. Test on the villains dataset (expect 70% accuracy on testing data, 95% on training data)
    // 5. Tests on the mushrooms dataset (expect 70% accuracy on testing data, 95% on training data)
    // Feel free to write more unit tests for your own helper methods -- more details can be found in the handout!
    String trainingPath = "data/weather.csv";
    String targetAttribute = "Playing Outdoor";
    Dataset trainingData;
    TreeGenerator testGenerator;

    @Before
    public void buildTreeForTest() {
        List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.trainingData = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        // builds a TreeGenerator object and generates a tree
        this.testGenerator = new TreeGenerator();
    }

    /**
     * Tests the getAttributeList method in the Dataset class
     */
    @Test
    public void testGetAttributeList(){
        List<String> expected = new LinkedList<>(Arrays.asList("Outlook", "Temperature", "Humidity", "Wind", "Playing Outdoor"));
        Assert.assertTrue(expected.containsAll(this.trainingData.getAttributeList()));
    }

    /**
     * Tests the getDataObjects method in the Dataset class
     */
    @Test
    public void testGetDataObjects(){
        List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        List<Row> expected = new LinkedList<>(dataObjects);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        Dataset data = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        Assert.assertEquals(data.getDataObjects().toString(),expected.toString());

    }

    /**
     * Tests the getSelectionType method in the Dataset class
     */
    @Test
    public void testGetSelectionType(){
        Assert.assertEquals(AttributeSelection.ASCENDING_ALPHABETICAL, this.trainingData.getSelectionType());
    }
    /**
     * Tests that the dataset.size() method works on the fruits.csv data
     */
    @Test
    public void testDatasetSize() {
        String fruitTrainingPath = "data/fruits.csv";
        List<Row> dataObjects = DecisionTreeCSVParser.parse(fruitTrainingPath);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        Dataset training = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        Assert.assertEquals(15, training.size());
    }

    /**
     * Tests getAttributeToSplitOn method in Dataset class returns a unique attribute every time
     */
    @Test
    public void testGetAttributeToSplitOn() {
        List<String> attributes = new LinkedList<>(List.of("Outlook", "Temperature", "Humidity", "Wind", "Playing Outdoor"));
        Dataset dataset = new Dataset(attributes, new ArrayList<>(), AttributeSelection.ASCENDING_ALPHABETICAL);
        //Test that decide which attribute to split on
        Assert.assertEquals(attributes, dataset.getAttributeList());
        // Test that getAttributeToSplit returns the correct attribute
        Assert.assertEquals("Humidity", dataset.getAttributeToSplitOn(this.targetAttribute));
    }

    /**
     * Tests the createEdges method of the AttributeNode
     */
    @Test
    public void testCreateEdges() {
        AttributeNode attributeNode = new AttributeNode(this.trainingData, "Outlook", this.targetAttribute);
        List<String> expected = new LinkedList<>();
        expected.addAll(Arrays.asList("Sunny", "Overcast", "Rainy"));

        List<ValueEdge> outgoingEdges = attributeNode.getOutgoingEdges();
        List<String> actual = new LinkedList<>();
        for (ValueEdge edge : outgoingEdges) {
            actual.add(edge.getValue());
        }
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.size(), actual.size());
    }

    /**
     * Tests the trimDataset helper method in the AttributeNode class
     */
    @Test
    public void testTrimDataset() {
        AttributeNode attributeNode = new AttributeNode(this.trainingData, "Outlook", "Playing Outdoor");

        // specify possible attribute values
        List<String> possibleValues = List.of("Sunny", "Overcast", "Rainy");

        List<Dataset> trimmedDatasets = attributeNode.trimDataset(possibleValues);

        // assert results
        Assert.assertEquals(3, trimmedDatasets.size());
    }

    /**
     * Tests the generateTree method. First, it tests on the weather dataset, and then it tests
     * on the fruits and vegetables dataset
     */
    @Test
    public void testTree(){
        // test weather dataset
        TreeGenerator tree = new TreeGenerator();
        tree.generateTree(this.trainingData,this.targetAttribute);
        String expected = "Yes";
        Row testRow = new Row("test row");
        testRow.setAttributeValue("Outlook", "Overcast");
        testRow.setAttributeValue("Temperature", "Cool");
        testRow.setAttributeValue("Humidity", "Normal");
        testRow.setAttributeValue("Wind", "Strong");

        Assert.assertEquals(expected, tree.getDecision(testRow));

        // tests on carrot on the fruits/vegetables dataset, default value functionality
        this.trainingPath = "data/fruits-and-vegetables.csv";
        String targetAttribute = "foodType";
        List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.trainingData = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        tree.generateTree(this.trainingData, targetAttribute);
        expected = "vegetable";

        Row carrot = new Row("test row");
        carrot.setAttributeValue("color", "orange");
        carrot.setAttributeValue("highProtein", "false");
        carrot.setAttributeValue("calories", "medium");
        Assert.assertEquals(expected, tree.getDecision(carrot));

        //test on simple dataset
        String targetAttributeSimple = "Outcome";
        List<Row> dataObjectsSimple = DecisionTreeCSVParser.parse("data/simple.csv");
        List<String> attributeListSimple = new ArrayList<>(dataObjectsSimple.get(0).getAttributes());
        Dataset simpleDataset = new Dataset(attributeListSimple,dataObjectsSimple,AttributeSelection.RANDOM);
        tree.generateTree(simpleDataset,targetAttributeSimple);
        expected = "yes";

        Row Blue = new Row("simple data");
        Blue.setAttributeValue("Color","Blue");
        Assert.assertEquals(expected, tree.getDecision(Blue));
    }

}
