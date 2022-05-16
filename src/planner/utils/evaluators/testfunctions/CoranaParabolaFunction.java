package planner.utils.evaluators.testfunctions;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * CoranaParabolaFunction is a class implementing the Corana's parabola function
 * over the TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class CoranaParabolaFunction extends TestFunction {

    /**
     * Array of constants for Corana's parabola function.
     */
    private final double[] d;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public CoranaParabolaFunction(Execution exe) {
        super(exe);
        this.d = new double[]{1, 1000, 10, 100};
    }

    /**
     * Computes the Corana's parabola function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator. Thrown if the problem is not 4-Dimensional.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) throws IncompatibleConfigurationException {
        double score = 0;
        double z;

        if (exe.getD() != 4) {
            throw new IncompatibleConfigurationException(this.toString()
                    + " Evaluator 4-Dimemsional.");
        }

        for (int i = 0; i < exe.getD(); i++) {
            z = z(genotype, i);
            if (Math.abs(genotype[i] - z) < 0.05) {
                score += 0.15 * Math.pow(z - 0.05 * Math.signum(z), 2) * d[i];
            } else {
                score += d[i] + Math.pow(genotype[i], 2);
            }
        }

        return score;
    }

    /**
     * Auxiliar function, z.
     *
     * @param x real value.
     * @param i index.
     * @return z value.
     */
    private double z(double[] x, int i) {
        return Math.floor(Math.abs(x[i] / 0.2) + 0.49999) * Math.signum(x[i]) * 0.2;
    }

    /**
     * Gets a string representing the visualization by console of the Test
     * Function.
     *
     * @return the name of the Test Function.
     */
    @Override
    public String toString() {
        return "Corana's Parabola Function";
    }

}
