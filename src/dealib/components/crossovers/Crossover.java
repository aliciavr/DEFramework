package dealib.components.crossovers;

import dealib.components.*;
import exceptions.IncompatibleConfigurationException;

/**
 * Crossover is an interface representing the basic scheme of a crossover
 * operator in differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface Crossover {

    /**
     * Executes a specific crossover scheme.
     *
     * @param p population of individuals.
     * @param X target/current individual.
     * @param V mutant individual.
     * @param individuals auxiliar individuals needed that cannot be computed
     * from population.
     * @return U, the trial individual, without checking the bounds with the
     * IPR.
     * @throws exceptions.IncompatibleConfigurationException when the length of
     * the individuals array is incompatible with the operation which is going
     * to be performed.
     */
    public abstract Individual crossIndividuals(Population p, Individual X, Individual V, Individual... individuals) throws IncompatibleConfigurationException;
}
