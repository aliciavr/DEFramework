package planner.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Dataset {

    private final String path;
    private final String name;
    private final String description;
    private final String format;
    private final String[] attributes;
    private final ArrayList<double[]> instances;

    public Dataset(String datasetPath, String datasetName, String datasetFormat,
            String datasetDescription, String[] datasetAttributes, double[][] data) {
        path = datasetPath;
        name = datasetName;
        format = datasetFormat;
        description = datasetDescription;
        attributes = datasetAttributes;
        instances  = new ArrayList<>();
        instances.addAll(Arrays.asList(data));
    }

    public String getPath() {
        return path;
    }

    public int getNumInstances() {
        return instances.size();
    }

    public int getNumAttributes() {
        return attributes.length;
    }

    public ArrayList<double[]> getInstances() {
        return instances;
    }

    public double[] getInstance(int i) {
        return instances.get(i);
    }

    public String[] getAttributes() {
        return attributes;
    }

    public double[] getPredictionValues() {
        int N = instances.size();
        double[] predictionValues = new double[N];
        for (int i = 0; i < N; i++) {
            predictionValues[i] = getPredictionValue(i);
        }
        return predictionValues;
    }

    public double getPredictionValue(int instance) {
        return instances.get(instance)[attributes.length - 1];
    }

    public double getData(int instance, int attribute) {
        return instances.get(instance)[attribute];
    }

    public double[] getDataFromInstance(int instance) {
        return instances.get(instance);
    }
    
    public double[] getDataInput(int instance) {
        double[] input = new double[getNumAttributes() - 1];
        System.arraycopy(instances.get(instance), 0, input, 0, input.length);
        return input;
    }

    public double[] getDataFromAttribute(int attribute) {
        int numInstances = instances.size();
        double[] attributeData = new double[numInstances];
        for (int i = 0; i < numInstances; i++) {
            attributeData[i] = instances.get(i)[attribute];
        }
        return attributeData;
    }

    public String datasetInfo() {
        String info = "==================DATASET INFO==================\n";
        info += "Name: " + name + "\n";
        info += "Format: " + format + "\n";
        info += "Path: " + path + "\n";
        info += "Description:\n" + description + "\n";
        info += "Number of attributes: " + attributes.length + ".\n";
        info += "Number of instances: " + instances.size() + ".\n";
        info += "Attributes loaded:\n" + PrintingTools.toString(attributes) + "\n";
        info += "================================================\n";
        return info;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDescription: " + description;
    }

}
