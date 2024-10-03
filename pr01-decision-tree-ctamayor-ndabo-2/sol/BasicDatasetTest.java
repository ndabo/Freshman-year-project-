package sol;

import org.junit.Assert;
import org.junit.Test;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

/**
 * A class to test basic decision tree functionality on a basic training dataset
 */
public class BasicDatasetTest {
    String trainingPath = "data/weather.csv";
    String targetAttribute = "Playing Outdoor";
    TreeGenerator testGenerator;
    Dataset trainingData;


    /**
     * Constructs the decision tree for testing based on the input file and the target attribute.
     */
    @Before
    public void buildTreeForTest() {
        List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
        List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
        this.trainingData = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
        // builds a TreeGenerator object and generates a tree
        this.testGenerator = new TreeGenerator();

        this.testGenerator.generateTree(this.trainingData, this.targetAttribute);
    }

    /**
     * Tests the expected classification of the "tangerine" row is a fruit
     */
    @Test
    public void testClassification() {
        Row tangerine = new Row("test row (tangerine)");
        tangerine.setAttributeValue("Outlook", "Sunny");
        tangerine.setAttributeValue("Temperature", "Hot");
        tangerine.setAttributeValue("Humidity", "High");
        tangerine.setAttributeValue("Wind", "Weak");

        Assert.assertEquals("No", this.testGenerator.getDecision(tangerine));
    }

}
