package planner.utils.objfunctions;

/**
 * LinearFunction is a class implementing a linear scheme for estimate a value
 * from a candidate solution and a set of input variables.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class LinearFunction implements ObjectiveFunction {
    
    /**
     * Computes an estimation for a candidate solution and a set of input
     * variables with a linear scheme, multiplying each gene by each input
     * variable. If one of the arrays is larger than the other the remaining
     * values are added.
     *
     * @param genotype candidate solution.
     * @param input input variables neede for the estimation.
     * @return the estimation.
     */
    @Override
    public double compute(double[] genotype, double[] input) {
        int N = Math.min(genotype.length, input.length);
        int M = Math.max(genotype.length, input.length);
        int i;
        double score = 0.0;

        for (i = 0; i < N; i++) {
            score += genotype[i] * input[i];
        }

        if (genotype.length < input.length) {
            for (; i < M; i++) {
                score += input[i];
            }
        } else if (genotype.length > input.length) {
            for (; i < M; i++) {
                score += genotype[i];
            }
        }

        return score;
    }

}
