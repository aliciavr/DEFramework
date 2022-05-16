package planner.utils;

import java.util.ArrayList;
import planner.utils.objfunctions.ObjectiveFunction;

/**
 * CrossValidation is a class which integrates the data and mechanisms needed
 * for running a basic k-fold cross validation.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class CrossValidation {

    private CV_STATE cvState;
    private final int k;
    private final ArrayList<double[]> instancesSet;
    private final ArrayList<double[]>[] folds;
    private int activeIndexTestPartition;
    private ArrayList<double[]> activeTestPartition;
    private ArrayList<double[]> activeTrainingPartition;

    /**
     * Constructor of the mechanisms need for running a k-fold cross validation:
     * given a dataset and a value for k, shuffles and splits the set of
     * instances stored in the dataset.
     *
     * @param d dataset of the problem.
     * @param k number of division in the k-fold cross validation process.
     */
    public CrossValidation(Dataset d, int k) {
        this.k = k;
        instancesSet = new ArrayList<>();
        folds = new ArrayList[k];
        for (int i = 0; i < k; i++) {
            folds[i] = new ArrayList<>();
        }
        shuffle(d);
        split(k);
        cvState = CV_STATE.TRAINING;
        updatePartitions();
    }

    /**
     * Randomizes the initial order of the instances stored in the dataset and
     * stores the new order of instances in a private variable of this object.
     *
     * @param d the dataset to be shuffled.
     */
    private void shuffle(Dataset d) {
        int[] indices = MathTools.uniformDistinctRND(d.getNumInstances(), d.getNumInstances());
        for (int i = 0; i < indices.length; i++) {
            instancesSet.add(d.getInstance(indices[i]));
        }
    }

    /**
     * Splits the internal shuffled order of the instances stored by the dataset
     * processed in the constructor.
     *
     * @param k number of folds.
     */
    private void split(int k) {
        int s = 0;
        for (int i = 0; i < instancesSet.size(); i++) {
            folds[s].add(instancesSet.get(i));
            s = (s + 1) % k;
        }

        for (int f = 0; f < k; f++) {
            //System.out.println("FOLD: " + f);
            for (int e = 0; e < folds[f].size(); e++) {
                //System.out.println(PrintingTools.toString(folds[f].get(e)));

            }
        }
    }

    public boolean isTraining() {
        return cvState == CV_STATE.TRAINING;
    }

    public boolean isTesting() {
        return cvState == CV_STATE.TESTING;
    }

    public void setTrainingState() {
        cvState = CV_STATE.TRAINING;
    }

    public void setTestingState() {
        cvState = CV_STATE.TESTING;
    }

    /**
     * Gets the number of folds, k, in k-CV
     *
     * @return number of folds.
     */
    public int getK() {
        return k;
    }

    /**
     * Gets the current active partition for the cross validation. Can be
     * changed to be the test or training partition by setting testing or
     * training mode.
     *
     * @return the active partition.
     */
    public ArrayList<double[]> getActivePartition() {
        ArrayList<double[]> activePartition = new ArrayList<>();

        if (cvState == CV_STATE.TRAINING) {
            activePartition = activeTrainingPartition;
        } else if (cvState == CV_STATE.TESTING) {
            activePartition = activeTestPartition;
        }

        return activePartition;
    }

    /**
     * Gets the current active test partition for the cross validation.
     *
     * @return the active test partition.
     */
    public ArrayList<double[]> getActiveTestPartition() {
        return activeTestPartition;
    }

    /**
     * Gets the current active training partition for the cross validation.
     *
     * @return the active training partition.
     */
    public ArrayList<double[]> getActiveTrainingPartition() {
        return activeTrainingPartition;
    }

    /**
     * Gets the real prediction values stored for each instance of the active
     * partition.
     *
     * @return an array of real prediction values.
     */
    public double[] getRealValPartition() {
        double[] real = new double[instancesSet.size()];
        ArrayList<double[]> partition = new ArrayList<>();

        if (cvState == CV_STATE.TRAINING) {
            real = new double[activeTrainingPartition.size()];
            partition = activeTrainingPartition;
        } else if (cvState == CV_STATE.TESTING) {
            real = new double[activeTestPartition.size()];
            partition = activeTestPartition;
        }

        int i = 0;

        for (double[] instance : partition) {
            real[i] = instance[instance.length - 1];
            i++;
        }

        return real;
    }

    /**
     * Gets the real prediction values stored for each instance of the active
     * training partition.
     *
     * @return an array of real prediction values.
     */
    public double[] getRealValTrainingPartition() {
        double[] real = new double[activeTrainingPartition.size()];
        int i = 0;

        for (double[] instance : activeTrainingPartition) {
            real[i] = instance[instance.length - 1];
            i++;
        }

        return real;
    }

    /**
     * Updates the current active training partition and the current active test
     * partition for running the cross validation updating the set of instances
     * used for training those used for testing.
     */
    public void updatePartitions() {
        ArrayList<double[]> trainingPartition = new ArrayList<>();
        ArrayList<double[]> testPartition = new ArrayList<>();

        for (int fold = 0; fold < k; fold++) {
            if (activeIndexTestPartition != fold) {
                trainingPartition.addAll(folds[fold]);
            } else {
                testPartition.addAll(folds[fold]);
            }
        }

        activeTrainingPartition = trainingPartition;
        activeTestPartition = testPartition;
        activeIndexTestPartition = (activeIndexTestPartition + 1) % k;
    }

    public double validate(ObjectiveFunction of) {
        ArrayList<double[]> fold;
        double score;
        for (int test = 0; test < k; test++) {
            for (int train = 0; train < k; train++) {
                if (test != train) {
                    fold = folds[train];
                    //   score = compute(d.getPredictionValues(), of.getEstimations(genotype, d));
                }
            }
        }
        return 0.0;
    }
}

enum CV_STATE {
    TESTING, TRAINING
}
