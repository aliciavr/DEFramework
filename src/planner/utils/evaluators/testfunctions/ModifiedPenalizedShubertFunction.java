package planner.utils.evaluators.testfunctions;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * ModifiedPenalizedShubertFunction is a class implementing the Modified
 * Penalized Shubert's function over the TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class ModifiedPenalizedShubertFunction extends PenalizedShubertFunction {

    /**
     * Constant for the function.
     */
    private final double beta;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public ModifiedPenalizedShubertFunction(Execution exe) {
        super(exe);
        beta = 1.0;
    }

    /**
     * Computes the Modified Penalized Shubert's function given a candidate
     * solution.
     *
     * @param genotype candidate solution.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator. Thrown if the problem is not 2-Dimensional.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) throws IncompatibleConfigurationException {
        if (exe.getD() != 2) {
            throw new IncompatibleConfigurationException(this.toString()
                    + " Evaluator 2-Dimensional.");
        }

        double score;
        double x1 = genotype[0];
        double x2 = genotype[1];

        score = super.evaluate(genotype) + beta * (Math.pow(x1 + 1.42513, 2) + Math.pow(x2 + 0.80032, 2));

        return score;
    }

    /**
     * Gets a string representing the visualization by console of the Test
     * Function.
     *
     * @return the name of the Test Function.
     */
    @Override
    public String toString() {
        return "Modified Penalized Shubert's Function";
    }

}
