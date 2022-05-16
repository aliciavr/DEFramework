package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;

/**
 * StepFunction is a class implementing the step function over the TestFunction
 * abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class StepFunction extends TestFunction {

    private final double C;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public StepFunction(Execution exe) {
        super(exe);
        this.C = 30.0;
    }

    /**
     * Computes the step function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score = C;

        for (int i = 0; i < exe.getD(); i++) {
            score += Math.floor(Math.pow(genotype[i], 2));
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
        return "Step Function";
    }

}
