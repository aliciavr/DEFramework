package planner.utils.evaluators.regrmetrics;

import planner.configuration.Execution;

/**
 * RMSLE is a class implementing the Root Mean Squared Logarithmic Error over
 * the RegressionMetric abstract super class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RMSLE extends RegressionMetric {

    /**
     * Constructor.
     *
     * @param exp configuration of the current execution.
     */
    public RMSLE(Execution exp) {
        super(exp);
    }

    /**
     * Computes Root Mean Squared Logarithmic Error (RMSLE) given the real
     * predicted value for an instance of a dataset and the estimation computed
     * from an objective function.
     *
     * @param real real value for a given instance of a dataset.
     * @param estimation prediction of the objective function used.
     * @return error of the estimation.
     */
    @Override
    public double compute(double[] real, double[] estimation) {
        double score;
        double sum = 0.0;
        int N = real.length;

        for (int i = 0; i < N; i++) {
            sum += Math.pow(Math.log(estimation[i] + 1) - Math.log(real[i] + 1), 2);
        }

        score = Math.sqrt(sum / N);

        return score;
    }

}
