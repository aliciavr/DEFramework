package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;

/**
 * GriewankFunction is a class implementing the Griewank's function over the
 * TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class GriewankFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public GriewankFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Griewank's function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score;
        double sum = 0.0;
        double product = 1.0;

        for (int i = 0; i < exe.getD(); i++) {
            sum += Math.pow(genotype[i], 2);
        }

        for (int i = 0; i < exe.getD(); i++) {
            product *= Math.cos(genotype[i] / Math.sqrt(i + 1));
        }

        score = (1.0 / 4000.0) * sum - product + 1;

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
        return "Griewank's Function";
    }

}
