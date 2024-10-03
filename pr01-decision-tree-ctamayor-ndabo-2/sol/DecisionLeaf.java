package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */
public class DecisionLeaf implements ITreeNode {
    String leafLabel;

    /**
     * Constructor for the class
     * @param leafLabel - the value/decision of this DecisionLeaf
     */
    public DecisionLeaf(String leafLabel){
        this.leafLabel = leafLabel; // decision
    }

    /**
     * DecisionLeaf is the base case so this should return the leafLabel
     * @param forDatum - the datum to find a decision for
     * @return - decision
     */
    @Override
    public String getDecision(Row forDatum) {
        return this.leafLabel;
    }
}
