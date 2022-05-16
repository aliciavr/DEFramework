package dealib.components.mutators;

import dealib.components.Individual;
import dealib.components.Population;
import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;

/**
 * CurrentToPBestMutator is a class implementing DE/current-to-pbest mutation
 * scheme over the interface Mutator.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class CurrentToPBestMutator implements Mutator {

    private final Execution exe;
    private static final int PBEST = 0;
    private static final int R1 = 1;
    private static final int PUA = 2;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public CurrentToPBestMutator(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes DE/current-to-pbest over the target/current individual, X.
     *
     * @param p current population.
     * @param X the target/current individual.
     * @param individuals provides auxiliary individuals needed for its
     * execution: position 0 needs PBEST individual, position 1 needs a random
     * individual and position 2 individual from the population, P, and the
     * archive, A.
     * @throws exceptions.IncompatibleConfigurationException when the length of
     * the individuals array is incompatible with the operation which is going
     * to be performed. In this case, needs the PBEST individual, a random
     * individual and the archive one.
     * @return mutant vector, V.
     */
    @Override
    public Individual mutateIndividual(Population p, Individual X, Individual... individuals) throws IncompatibleConfigurationException {
        if (individuals.length < 3) {
            throw new IncompatibleConfigurationException(this.toString()
                    + " Needs the pBest individual, the random individual and"
                    + " the archive individual.");
        }

        double D = exe.getD();
        double F = exe.getF();
        Individual V = new Individual(exe, p.getCurrentG());

        double[] x = X.getGenotype();
        double[] pBest = individuals[PBEST].getGenotype(); // pBest individual
        double[] r1 = individuals[R1].getGenotype(); // Random individual
        double[] r2 = individuals[PUA].getGenotype(); // PUA individual

        for (int k = 0; k < D; k++) {
            V.setGene(k, x[k] + F * (pBest[k] - x[k]) + F * (r1[k] - r2[k]));
        }

        return V;
    }

    /**
     * Gets a string representing the visualization by console of the Mutator.
     *
     * @return the name of the Mutator.
     */
    @Override
    public String toString() {
        return "DE/current-to-pbest/1 with archive";
    }
}
