package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;

/**
 * RosenbrockFunction is a class implementing the Rosenbrock's function over the
 * TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RosenbrockFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public RosenbrockFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Rosenbrock's function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score = 0.0;

        for (int i = 0; i < exe.getD() - 1; i++) {
            score += 100 * Math.pow(genotype[i + 1] - Math.pow(genotype[i], 2), 2) +  Math.pow(1 - genotype[i], 2);
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
        return "Rosenbrock's Function";
    }

}
