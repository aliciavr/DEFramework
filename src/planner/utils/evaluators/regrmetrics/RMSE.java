package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * RMSE is a class implementing the Root Mean Squared Error over the
 * RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RMSE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public RMSE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Root Mean Squared Error (RMSE) given the real predicted value
     * for an instance of a dataset and the estimation computed from an
     * objective function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        int N = real.length;
        double error;
        double sum = 0;

        for (int i = 0; i < N; i++) {
            sum += Math.pow(real[i] - estimation[i], 2);
        }

        error = Math.sqrt(1.0 / N * sum);

        return error;

    }

}
