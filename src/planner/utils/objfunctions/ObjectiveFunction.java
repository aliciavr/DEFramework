package planner.utils.objfunctions;

import java.util.ArrayList;
import planner.utils.Dataset;

/**
 * ObjectiveFunction is a class implementing the basic scheme of a function with
 * the objective of making an estimation out of a set input variables and the
 * genotype.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface ObjectiveFunction {

    /**
     * Computes an estimation out of a set of input variables and a genotype.
     *
     * @param genotype candidate solution.
     * @param input input variables needed for the estimation.
     * @return the estimation.
     */
    public abstract double compute(double[] genotype, double[] input);
    
    public default double[] getInstancesEstimations(double[] genotype, ArrayList<double[]> instances) {
        int numInstances = instances.size();
        double[] estimations = new double[numInstances];
        for (int i = 0; i < numInstances; i++) {
            estimations[i] = compute(genotype, instances.get(i));
        }
        
        return estimations;
    }
}
