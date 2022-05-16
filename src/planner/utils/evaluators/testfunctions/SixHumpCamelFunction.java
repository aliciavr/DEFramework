package planner.utils.evaluators.testfunctions;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * SixHumpCamelFunction is a class implementing the Six-Hump Camel function over
 * the TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class SixHumpCamelFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public SixHumpCamelFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Six-Hump Camel function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator. Thrown if the problem is not 2-Dimensional.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) throws IncompatibleConfigurationException {
        double score;

        if (exe.getD() != 2) {
            throw new IncompatibleConfigurationException(this.toString()
                    + " Evaluator 2-Dimemsional.");
        }

        double x1 = genotype[0];
        double x2 = genotype[1];
        score = (4 - 2.1 * Math.pow(x1, 2) + (1.0 / 3.0) * Math.pow(x1, 4))
                * Math.pow(x1, 2) + x1 * x2
                + (-4 + 4 * Math.pow(x2, 2)) * Math.pow(x2, 2);

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
        return "Six-hump Camel's Function";
    }

}
