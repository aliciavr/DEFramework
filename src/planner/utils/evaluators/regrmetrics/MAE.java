package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * MAE is a class implementing the Mean Absolute Error over the RegressionMetric
 * abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class MAE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public MAE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Mean Absolute Error (MAE) given the real predicted value for an
     * instance of a dataset and the estimation computed from an objective
     * function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        int N = real.length;
        double sum = 0.0;
        double error;

        for (int i = 0; i < N; i++) {
            sum += Math.abs(real[i] - estimation[i]);
        }

        error = 1.0 * N * sum;

        return error;
    }

}
