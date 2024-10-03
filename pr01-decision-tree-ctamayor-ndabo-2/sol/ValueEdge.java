package sol;

import src.ITreeNode;
import src.Row;

import java.util.List;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class ValueEdge {
    private ITreeNode child;
    private String value;
    private Dataset dataset;
    private String targetAttribute;

    /**
     * Constructor
     * @param dataset - dataset (was trimmed by AttributeNode)
     * @param value - value of this edge
     * @param targetAttribute - target attribute of the tree
     */
    public ValueEdge(Dataset dataset, String value, String targetAttribute){
        this.value = value;
        this.dataset = dataset;
        this.targetAttribute = targetAttribute;
        this.generateTree();
    }

    /**
     * Helper method to create a DecisionLeaf as the child node
     */
    private void createLeaf(){
        String decisionLeafValue = this.dataset.getDataObjects().get(0).getAttributeValue(this.targetAttribute);
        this.child = new DecisionLeaf(decisionLeafValue);
    }

    /**
     * Helper method to create a new AttributeNode as the child node
     */
    private void createAttributeNode(){
        this.child = new AttributeNode(this.dataset, this.dataset.getAttributeToSplitOn(this.targetAttribute), this.targetAttribute);
    }

    /**
     * Generate tree method. Either creates a new leaf or a new attribute node
     */
    public void generateTree(){
        if (this.checkLeafNeedsToBeCreated()){
            this.createLeaf();
        } else {
            this.createAttributeNode();
        }
    }

    /**
     * Method checks if this edge needs to create a leaf or not by checking if the
     * rows remaining in the dataset all equal the targetValue
     * @return - boolean, true if leaf needs to be created
     */
    public boolean checkLeafNeedsToBeCreated(){
        boolean createLeaf = true;
        List<Row> dataObjects = this.dataset.getDataObjects();
        String targetValue = dataObjects.get(0).getAttributeValue(this.targetAttribute);
        for (Row row : dataObjects){
            if (!row.getAttributeValue(this.targetAttribute).equals(targetValue)){
                createLeaf = false;
            }
        }
        return createLeaf;
    }

    /**
     * Getter for the value of this edge
     * @return - string for vvalue
     */
    public String getValue(){
        return this.value;
    }

    /**
     * Gets the decision from the child
     * @param forDatum - row to get decision for
     * @return - decision
     */
    public String getDecision(Row forDatum){
        return this.child.getDecision(forDatum);
    }
}
