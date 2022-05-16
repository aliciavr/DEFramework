package planner.utils.evaluators.testfunctions;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * PenalizedShubertFunction is a class implementing the Penalized Shubert's
 * function over the TestFunction abstract base class.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class PenalizedShubertFunction extends TestFunction {

    /**
     * a constant.
     */
    private final double a;

    /**
     * k constant.
     */
    private final double k;

    /**
     * m constant.
     */
    private final double m;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public PenalizedShubertFunction(Execution exe) {
        super(exe);
        this.a = 10;
        this.k = 100;
        this.m = 2;
    }

    /**
     * Computes the Penalized Shubert's function given a candidate solution.
     *
     * @param genotype candidate solution.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator. Thrown if the problem is not 1-Dimensional or
     * 2-Dimensional.
     * @return fitness of the candidate solution.
     */
    @Override
    public double evaluate(double[] genotype) throws IncompatibleConfigurationException {
        double score = Double.MAX_VALUE;
        double x1, x2;
        switch (exe.getD()) {
            case 1:
                x1 = genotype[0];
                score = g(x1) + u(x1);
                break;
            case 2:
                x1 = genotype[0];
                x2 = genotype[1];
                score = g(x1) * g(x2) + u(x1) + u(x2);
                break;
            default:
                throw new IncompatibleConfigurationException(this.toString()
                        + " Evaluator 1-Dimensional or 2-Dimensional.");
        }

        return score;
    }

    /**
     * Auxiliar function, g.
     *
     * @param genotype candidate solution.
     * @return g value
     */
    protected double g(double genotype) {
        double sum = 0.0;

        for (int i = 1; i <= 5; i++) {
            sum += i * Math.cos((i + 1) * genotype + i);
        }

        return sum;
    }

    /**
     * Auxiliar function, u.
     *
     * @param z real value.
     * @return u value.
     */
    protected double u(double z) {
        double y = Double.MAX_VALUE;

        if (z > a) {
            y = k * Math.pow(z - a, m);
        } else if (-a <= z && z <= a) {
            y = 0;
        } else if (z < -a) {
            y = k * Math.pow(-z - a, m);
        }

        return y;
    }

    /**
     * Gets a string representing the visualization by console of the Test
     * Function.
     *
     * @return the name of the Test Function.
     */
    @Override
    public String toString() {
        return "Penalized Shubert's Function";
    }

}
