package sol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
public class AttributeNode implements ITreeNode {
    private List<ValueEdge> outgoingEdges;
    private String attribute;
    private Dataset dataset;
    private String targetAttribute;

    /**
     * Constructor for this class
     * @param dataset - dataset with only rows that have made it this far in the tree
     * @param attribute - attribute this node is splitting on
     */
    public AttributeNode(Dataset dataset, String attribute, String targetAttribute){
        this.dataset = dataset;
        this.attribute = attribute;
        this.outgoingEdges = new LinkedList<>();
        this.targetAttribute = targetAttribute;
        this.generateTree();
    }

    /**
     * Creates edges to this node when constructing the tree
     */
    public void createEdges(){
        if (this.dataset.getAttributeList().isEmpty()) {
            return;
        }
        this.dataset.removeAttribute(this.attribute);
        // list of possible values
        List<String> possibleValues = new LinkedList<>();
        List<Row> dataObjects = this.dataset.getDataObjects();
        // loop through every row
        for (Row row: dataObjects) {
            // check if that row's value is in possible values. If it is not, add to possibleValues
            if (!possibleValues.contains(row.getAttributeValue(this.attribute))){
                possibleValues.add(row.getAttributeValue(this.attribute));
            }
        }
        if (!possibleValues.isEmpty()) {
            List<Dataset> trimmedDatasets = this.trimDataset(possibleValues);
            // For each possible value, create an edge
            for (int i = 0; i < possibleValues.size(); i++) {
                String value = possibleValues.get(i);
                Dataset edgeDataset = trimmedDatasets.get(i);

                // Create edge of this possible value only if that edge can split on an attribute
                // or if all the rows have the same target attribute
                List<String> targetAttributes = new LinkedList<>();
                for (Row row : edgeDataset.getDataObjects()){
                    if (!targetAttributes.contains(row.getAttributeValue(this.targetAttribute))){
                        targetAttributes.add(row.getAttributeValue(this.targetAttribute));
                    }
                }
                boolean rowsHaveSameTarget = targetAttributes.size()==1;
                boolean edgeCanSplit = !edgeDataset.getAttributeList().isEmpty();
                if (edgeCanSplit || rowsHaveSameTarget) {
                    ValueEdge newEdge = new ValueEdge(edgeDataset, value, this.targetAttribute);
                    // Add edge to outgoing edges list
                    this.outgoingEdges.add(newEdge);
                }
            }
        }
    }

    /**
     * Getter method for outgoingEdges
     * @return - outgoingEdges
     */
    public List<ValueEdge> getOutgoingEdges(){
        return this.outgoingEdges;
    }

    /**
     * Trims the initial dataset into smaller datasets, each with rows with the same value
     * of the attribute to split on.
     * @param possibleValues - possible values of the attribute to split on
     * @return - list of datasets, one for each possible value
     */
    public List<Dataset> trimDataset(List<String> possibleValues){
        List<Dataset> datasetList = new ArrayList<>();
        // Loop through each possible value and create a trimmed dataset
        for (String value : possibleValues) {
            // Create new, filtered dataset
            List<Row> newDataObject = this.dataset.getDataObjects().stream()
                    .filter(row -> row.getAttributeValue(this.attribute).equals(value))
                    .toList();
            // Create a new attribute list
            List<String> newAttributeList = new ArrayList<>(this.dataset.getAttributeList());
            // Create a new dataset
            Dataset newDataset = new Dataset(newAttributeList, newDataObject, this.dataset.getSelectionType());
            // Add the new dataset to the list
            datasetList.add(newDataset);
        }
        return datasetList;
    }

    /**
     * Recursive method to generate the tree. For this node, this generates the edges
     * which will each keep generating the tree
     */
    public void generateTree(){
        if (this.outgoingEdges.isEmpty()) {
            this.createEdges();
        }
    }

    /**
     * Recursive method to get the decision for the given row. First, this figures out which
     * edge to traverse the down based on the row, then it makes a recursive call. If there is
     * no corresponding edge, this method returns the default value.
     * @param forDatum - the datum to lookup a decision for
     * @return - decision string
     */
    @Override
    public String getDecision(Row forDatum) {
        // go to correct value edge
        ValueEdge correctEdge = null;
        for (ValueEdge edge : this.outgoingEdges){
            if (edge.getValue().equals(forDatum.getAttributeValue(this.attribute))){
                correctEdge = edge;
                break;
            }
        }
        if (correctEdge != null){
            // return getDecision of value edge
            return correctEdge.getDecision(forDatum);
        } else {
            return this.defaultDecision();
        }
    }

    /**
     * Helper method called by getDecision. Calculates the default value for this AttributeNode
     * by looping through each possible decision, counting the number of rows with that decision,
     * and returning the possible decision with the highest row frequency.
     * @return - decision string
     */
    private String defaultDecision(){
        // find all the possible decisions
        List<String> possibleDecisions = new ArrayList<>();
        for (Row row: this.dataset.getDataObjects()){
            if (!possibleDecisions.contains(row.getAttributeValue(this.targetAttribute))){
                possibleDecisions.add(row.getAttributeValue(this.targetAttribute));
            }
        }

        String decision = possibleDecisions.get(0);
        int currentDecisionCount = 0;
        for (String possibleDecision : possibleDecisions){
            int count = 0;
            for(Row row: this.dataset.getDataObjects()){
                if(row.getAttributeValue(this.targetAttribute).equals(possibleDecision)){
                    count++;
                }
            }
            if (count > currentDecisionCount){
                decision = possibleDecision;
                currentDecisionCount = count;
            }
        }
        return decision;
    }
}
