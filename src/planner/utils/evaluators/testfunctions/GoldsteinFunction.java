package planner.utils.evaluators.testfunctions;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * GoldsteinFunction is a class implementing the Goldstein's function over the
 * TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class GoldsteinFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public GoldsteinFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Goldstein's function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator. Thrown if the problem is not 1-Dimensional.
     */
    @Override
    public double evaluate(double[] genotype) throws IncompatibleConfigurationException {
        double score;
        if (exe.getD() != 1) {
            throw new IncompatibleConfigurationException(this.toString()
                    + " Evaluator 1-Dimemsional.");
        }
        double x1 = genotype[0];
        score = Math.pow(x1, 6) - 15 * Math.pow(x1, 4) + 27 * Math.pow(x1, 2) + 250;
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
        return "Goldstein's Function";
    }

}
