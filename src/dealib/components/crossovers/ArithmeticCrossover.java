package dealib.components.crossovers;

import dealib.components.Individual;
import dealib.components.Population;
import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * ArithmeticCrossover is a class implementing a binomial scheme for crossover
 * step over the interface Crossover.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class ArithmeticCrossover implements Crossover {

    private final Execution exe;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public ArithmeticCrossover(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes arithmetic crossover scheme over the target/current individual,
     * X and the mutant individual V.
     *
     * @param p current population.
     * @param X the target/current individual.
     * @param V the mutant individual.
     * @param individuals position 0 represents K individual with k genotype as
     * paremeter for this crossover scheme.
     * @throws exceptions.IncompatibleConfigurationException when the length of
     * the individuals array is incompatible with the operation which is going
     * to be performed. In this case, needs an Individual representing the k
     * vector at position 0.
     * @return the trial individual, U.
     */
    @Override
    public Individual crossIndividuals(Population p, Individual X, Individual V, Individual... individuals) throws IncompatibleConfigurationException {
        if (individuals.length < 1) {
            throw new IncompatibleConfigurationException(this.toString() + " Needs k vector.");
        }

        int D = exe.getD();
        Individual U = new Individual(exe, p.getCurrentG());
        double[] x = X.getGenotype();
        double[] v = V.getGenotype();
        double[] k = individuals[0].getGenotype();

        for (int i = 0; i < D; i++) {
            U.setGene(i, x[i] + k[i] * (v[i] - x[i]));
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
        return "Arithmetic (ari)";
    }

}
