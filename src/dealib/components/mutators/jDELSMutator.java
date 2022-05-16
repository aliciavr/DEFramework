package dealib.components.mutators;

import dealib.components.Individual;
import dealib.components.Population;
import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;
import static planner.utils.MathTools.RND;

/**
 * jDELSMutator is a class implementing DE/local-search mutation scheme over the
 * interface Mutator.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class jDELSMutator implements Mutator {

    private final Execution exp;
    private static final int LSR = 0;

    /**
     * Constructor.
     *
     * @param exp Configurarion of the current execution.
     */
    public jDELSMutator(Execution exp) {
        this.exp = exp;
    }

    /**
     * Executes DE/local-search over the target/current individual, X.
     *
     * @param p current population.
     * @param X the target/current individual.
     * @param individuals position 0 represents an individual coding the LSR
     * vector as the search range.
     * @throws exceptions.IncompatibleConfigurationException when the length of
     * the individuals array is incompatible with the operation which is going
     * to be performed. In this case, needs an Individual representing LSR
     * vector.
     * @return mutant vector, V.
     */
    @Override
    public Individual mutateIndividual(Population p, Individual X, Individual... individuals) throws IncompatibleConfigurationException {
        if (individuals.length < 1) {
            throw new IncompatibleConfigurationException(this.toString() + " Needs vector lsr.");
        }

        double D = exp.getD();
        Individual V = new Individual(exp);

        double[] x = X.getGenotype();
        double[] lsr = individuals[LSR].getGenotype();

        if (RND.nextDouble() < 0.5) {
            for (int k = 0; k < D; k++) {
                V.setGene(k, x[k] - lsr[k]);
            }
        } else {
            for (int k = 0; k < D; k++) {
                V.setGene(k, x[k] + lsr[k]);
            }
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
        return "DE/jDELS";
    }
}
