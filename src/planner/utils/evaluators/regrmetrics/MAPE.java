package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * MAPE is a class implementing the Mean Absolute Percentage Error over the
 * RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class MAPE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public MAPE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Mean Absolute Percentage Error (MAPE) given the real predicted
     * value for an instance of a dataset and the estimation computed from an
     * objective function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        int N = real.length;
        double score;
        double sum = 0.0;
        double num = 0.0;
        for (int i = 0; i < N; i++) {
            if (real[i] != 0) {
                sum += Math.abs((real[i] - estimation[i]) / Math.abs(real[i]));
                num++;
            }
        }

        score = sum / num;

        return score;
    }

}
