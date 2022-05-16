package dealib.components.crossovers;

import planner.configuration.Execution;
import dealib.components.Individual;
import dealib.components.Population;
import static planner.utils.MathTools.RND;

/**
 * ExponentialCrossover is a class implementing a exponential scheme for
 * crossover step over the interface Crossover.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class ExponentialCrossover implements Crossover {

    private final Execution exe;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public ExponentialCrossover(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes exp crossover scheme over the target/current individual, X and
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
        int D = exe.getD();
        double CR = exe.getCR();
        Individual U = new Individual(exe, p.getCurrentG());
        U.copyGenotype(X.getGenotype());
        double[] v = V.getGenotype();

        int n = RND.nextInt(D);
        int L = 0;

        do {
            L++;
        } while (RND.nextDouble() < CR && L < D);

        for (int k = 0; k < L; k++) {
            U.setGene((n + k) % D, v[(n + k) % D]);
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
        return "Exponential (exp)";
    }

}
