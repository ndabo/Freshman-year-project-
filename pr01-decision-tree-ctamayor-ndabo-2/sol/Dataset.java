package sol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

/**
 * A class representing a training dataset for the decision tree
 */
public class Dataset implements IDataset {
    List<String> attributeList;
    List<Row> dataObjects;
    AttributeSelection selectionType;
    /**
     * Constructor for a Dataset object
     * @param attributeList - a list of attributes
     * @param dataObjects -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        this.attributeList = attributeList;
        this.dataObjects = dataObjects;
        this.selectionType = attributeSelection;
    }

    /**
     * Gets the attribute to split on. Called every time a new AttributeNode is created
     * @param targetAttribute - the attribute to make a decision for
     * @return - attribute to split on
     */
    public String getAttributeToSplitOn(String targetAttribute) {

        this.attributeList.remove(targetAttribute);

        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);

            }
            case RANDOM -> {
                if(this.attributeList.isEmpty()){
                    throw new RuntimeException("attributeList is empty, can't remove anything");
                }
                Random random = new Random();
                int randomIndex = random.nextInt(this.attributeList.size());
                return this.attributeList.get(randomIndex);
            }
        }
        throw new RuntimeException("Non-Exhaustive Switch Case");
    }

    /**
     * getter for attributeList
     * @return - attribute list
     */
    @Override
    public List<String> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Getter for the dataObjects
     * @return - returns list of rows
     */
    @Override
    public List<Row> getDataObjects() {
        return this.dataObjects;
    }

    /**
     * getter for selectionType
     * @return - selectionType
     */
    @Override
    public AttributeSelection getSelectionType() {
        return this.selectionType;
    }

    /**
     * getter for size
     * @return - size of dataset
     */
    @Override
    public int size() {
        return this.dataObjects.size();
    }

    /**
     * Remove attribute from the attribute list
     * @param attribute - attribute to remove
     */
    public void removeAttribute(String attribute){
        this.attributeList.remove(attribute);
    }
}
