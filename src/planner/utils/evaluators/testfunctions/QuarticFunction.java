package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;
import static planner.utils.MathTools.RND;

/**
 * QuarticFunction is a class implementing the quartic function over the
 * TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class QuarticFunction extends TestFunction {

    private final double n;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public QuarticFunction(Execution exe) {
        super(exe);
        this.n = RND.nextDouble();
    }

    /**
     * Computes the quartic function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score = 0.0;

        for (int i = 0; i < exe.getD(); i++) {
            score += (i + 1) * Math.pow(genotype[i], 4) + n;
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
        return "Quartic Function";
    }

}
