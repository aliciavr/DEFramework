package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * MSPE is a class implementing the Mean Squared Percentage Error over the
 * RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class MSPE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public MSPE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Mean Squared Percentage Error (MSPE) given the real predicted
     * value for an instance of a dataset and the estimation computed from an
     * objective function.
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

        error = sum / num;

        return error;
    }

}
