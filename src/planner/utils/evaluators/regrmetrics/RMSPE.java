package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * RMSPE is a class implementing the Root Mean Squared Percentage Error over the
 * RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RMSPE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public RMSPE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Root Mean Squared Percentage Error (RMSPE) given the real
     * predicted value for an instance of a dataset and the estimation computed
     * from an objective function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        double error;
        double sum = 0.0;
        int N = real.length;
        double num = 0.0;
        for (int i = 0; i < N; i++) {
            if (real[i] != 0) {
                sum += Math.pow((real[i] - estimation[i]) / real[i], 2);
                num++;
            }
        }

        error = Math.sqrt(sum / num);

        return error;
    }

}
