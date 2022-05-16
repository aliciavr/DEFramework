package dealib.components.mutators;

import dealib.components.*;
import exceptions.IncompatibleConfigurationException;

/**
 * Mutator is a class implementing the basic scheme of a mutator operator in 
 * differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface Mutator {

    /**
     * Executes a specific mutator scheme.
     *
     * @param p population of individuals.
     * @param X target/current individual.
     * @param individuals auxiliar individuals needed that cannot be computed
     * from population.
     * @return V, the mutated individual, without checking the bounds with the
     * IPR.
     * @throws exceptions.IncompatibleConfigurationException when the length
     * of the individuals array is incompatible with the operation which is
     * going to be performed.
     */
    public abstract Individual mutateIndividual(Population p, Individual X, Individual... individuals) throws IncompatibleConfigurationException;
}
