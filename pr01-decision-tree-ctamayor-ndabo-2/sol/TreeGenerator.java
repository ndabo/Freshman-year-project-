package sol;

import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    private ITreeNode root;

    /**
     * Generates the tree starting from root
     * @param trainingData    the dataset to train on
     * @param targetAttribute the attribute to predict
     */
    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        List<String> copyAttributeList= new ArrayList<>(trainingData.attributeList);
        List<Row> copyDataObject  = new ArrayList<>(trainingData.getDataObjects());

        Dataset newDataset = new Dataset(copyAttributeList,copyDataObject,trainingData.selectionType);
        this.root = new AttributeNode(newDataset, newDataset.getAttributeToSplitOn(targetAttribute), targetAttribute);
    }

    /**
     * Gets a decision from the tree
     * @param datum the datum to lookup a decision for
     * @return - decision
     */
    @Override
    public String getDecision(Row datum) {
        return this.root.getDecision(datum);
    }


}
