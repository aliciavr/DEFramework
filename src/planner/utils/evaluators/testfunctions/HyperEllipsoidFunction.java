package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;

/**
 * HyperEllipsoidFunction is a class implementing the Hyper-Ellipsoid function
 * over the TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class HyperEllipsoidFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public HyperEllipsoidFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Hyper-Ellipsoid function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score = 0.0;

        for (int i = 0; i < exe.getD(); i++) {
            score += Math.pow(i + 1, 2) * Math.pow(genotype[i], 2);
        }

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
        return "Hyper-Ellipsoid Function";
    }

}
