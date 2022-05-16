package dealib.components.crossovers;

import planner.configuration.Execution;
import dealib.components.Individual;
import dealib.components.Population;
import static planner.utils.MathTools.RND;

/**
 * BinomialCrossover is a class implementing a binomial scheme for crossover
 * step over the interface Crossover.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class BinomialCrossover implements Crossover {

    private final Execution exe;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public BinomialCrossover(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes bin crossover scheme over the target/current individual, X and
     * the mutant individual V.
     *
     * @param p current population.
     * @param X the target/current individual.
     * @param V the mutant individual.
     * @param individuals some possible auxiliary individuals, not used in this
     * scheme.
     * @return the trial individual, U.
     */
    @Override
    public Individual crossIndividuals(Population p, Individual X, Individual V, Individual... individuals) {
        double D = exe.getD();
        double CR = exe.getCR();
        int jRand = RND.nextInt(exe.getD());
        Individual U = new Individual(exe, p.getCurrentG());

        double[] x = X.getGenotype();
        double[] v = V.getGenotype();

        for (int j = 0; j < D; j++) {
            if (RND.nextDouble() < CR || j == jRand) {
                U.setGene(j, v[j]);
            } else {
                U.setGene(j, x[j]);
            }
        }

        return U;
    }

    /**
     * Gets a string representing the visualization by console of the Crossover.
     *
     * @return the name of the Crossover.
     */
    @Override
    public String toString() {
        return "Binomial (bin)";
    }
}
