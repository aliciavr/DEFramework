package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;

/**
 * AckleyFunction is a class implementing the Ackley's function over the
 * TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class AckleyFunction extends TestFunction {

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public AckleyFunction(Execution exe) {
        super(exe);
    }

    /**
     * Computes the Ackley's function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) {
        double score;
        double sum1 = 0.0;
        double sum2 = 0.0;

        for (int i = 0; i < exe.getD(); i++) {
            sum1 += Math.pow(genotype[i], 2);
            sum2 += Math.cos(2 * Math.PI * genotype[i]);
        }

        score = -20 * Math.exp(-0.02 * Math.sqrt((1.0 / exe.getD()) * sum1)) - Math.exp((1.0 / exe.getD()) * sum2) + 20 + Math.exp(1);

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
        return "Ackley's Function";
    }

}
