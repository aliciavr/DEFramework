package dealib.components.initializers;

import dealib.components.*;
import exceptions.IncompatibleConfigurationException;

/**
 * Initializer is an interface representing the basic scheme of an initializer
 * operator in differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface Initializer {

    /**
     * Executes a specific initializer scheme.
     *
     * @param p population of individuals.
     * @throws exceptions.IncompatibleConfigurationException when initializer is
     * not enabled with the defined configuration or when the Evaluator used
     * throws this exception because of an incorrect dimensionality of the
     * problem.
     */
    public abstract void initializePopulation(Population p) throws IncompatibleConfigurationException;
}
