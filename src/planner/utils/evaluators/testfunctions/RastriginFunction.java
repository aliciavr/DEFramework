package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;

/**
 * RastriginFunction is a class implementing the Rastrigin's function over the
 * TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RastriginFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public RastriginFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Rastrigin's function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score;
        double sum = 0.0;

        for (int i = 0; i < exe.getD(); i++) {
            sum += Math.pow(genotype[i], 2) - 10 * Math.cos(2 * Math.PI * genotype[i]);
        }

        score = exe.getD() * 10 + sum;

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
        return "Rastrigin's Function";
    }

}
