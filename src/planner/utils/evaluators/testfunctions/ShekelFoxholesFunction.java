package planner.utils.evaluators.testfunctions;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * ShekelFoxholesFunction is a class implementing the Shekel's Foxholes function
 * over the TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class ShekelFoxholesFunction extends TestFunction {

    private final int N;
    private final double[][] a;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public ShekelFoxholesFunction(Execution exe) {
        super(exe);
        this.N = 25;
        this.a = new double[][]{
            {-32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32},
            {-32, -32, -32, -32, -32, -16, -16, -16, -16, -16, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 32, 32, 32, 32, 32}
        };
    }

    /**
     * Computes the Shekel's Foxholes function given a candidate solution.
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
        double sum1 = 0;
        double sum2;

        if (exe.getD() != 2) {
            throw new IncompatibleConfigurationException(this.toString()
                    + " Evaluator 2-Dimensional.");
        }

        for (int k = 0; k < N; k++) {
            sum2 = 0;
            for (int i = 0; i < exe.getD(); i++) {
                double dif = genotype[i] - a[i][k];
                sum2 += Math.pow(dif, 6);
            }
            sum1 += 1.0 / (k + sum2);
        }

        score = 1.0 / (0.002 + sum1);

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
        return "Shekel Foxholes' Function";
    }

}
