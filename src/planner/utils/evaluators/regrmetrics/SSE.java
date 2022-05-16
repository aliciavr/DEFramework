package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * SSE is a class implementing the Sum of Squared Errors over the
 * RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class SSE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public SSE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Sum of Squared Errors (SSE) given the real predicted value for
     * an instance of a dataset and the estimation computed from an objective
     * function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        int N = real.length;
        double error = 0.0;

        for (int i = 0; i < N; i++) {
            error += Math.pow(real[i] - estimation[i], 2);
        }

        return error;
    }

}
