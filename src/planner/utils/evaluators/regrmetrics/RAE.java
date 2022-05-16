package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;
import planner.utils.MathTools;

/**
 * RAE is a class implementing the Relative Absolute Error over the
 * RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RAE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public RAE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Relative Absolute Error (RAE) given the real predicted value for
     * an instance of a dataset and the estimation computed from an objective
     * function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        double score;
        int N = real.length;
        double sum1 = 0.0;
        double sum2 = 0.0;
        double mean = MathTools.arithmeticMean(real);

        for (int i = 0; i < N; i++) {
            if (real[i] != 0) {
                sum1 += Math.abs(real[i] - estimation[i]);
                sum2 += Math.abs(real[i] - mean);
            }
        }
        
        score = sum1 / sum2;
        
        return score;
    }

}
