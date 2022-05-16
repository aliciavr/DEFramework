package planner.utils.evaluators.regrmetrics;

import java.util.ArrayList;
import planner.configuration.Execution;
import planner.utils.CrossValidation;
import planner.utils.Dataset;
import planner.utils.evaluators.Evaluator;
import planner.utils.objfunctions.ObjectiveFunction;

/**
 * RegressionMetric is an abstract class for representing the regression metrics
 * used when training with datasets, can be also considerated as evaluators of
 * the genotype of the individuals.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class RegressionMetric implements Evaluator {

    /**
     * Configuration of the current execution.
     */
    protected final Execution exe;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public RegressionMetric(Execution exe) {
        this.exe = exe;
    }

    /**
     * Computes the error between the real value (stored in dataset instances)
     * and the estimation made by the executed algorithm.
     *
     * @param real real value for a given instance of the dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    public abstract double compute(double[] real, double[] estimation);

    /**
     * Evaluates the genotype given with a concrete configured RegressionMetric
     * and the configured dataset for the current execution.
     *
     * @param genotype candidate solution.
     * @return fitness of the given genotype to the problem.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score;
        Dataset d = exe.getDataset();
        ObjectiveFunction of = exe.getObjectiveFunction();
        if (exe.getCV() != null) {
            CrossValidation CV = exe.getCV();
            ArrayList<double[]> instances = CV.getActivePartition();
            double[] realValues = CV.getRealValPartition();
            score = compute(realValues, of.getInstancesEstimations(genotype, instances));
        } else {
            // Executes an independent execution.
            score = compute(d.getPredictionValues(), of.getInstancesEstimations(genotype, d.getInstances()));
        }
        return score;
    }
}
