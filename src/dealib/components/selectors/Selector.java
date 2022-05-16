package dealib.components.selectors;

import dealib.components.Individual;
import exceptions.IncompatibleConfigurationException;

/**
 * Selector is a class implementing the basic scheme of a selector operator in
 * differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface Selector {

    /**
     * Executes a specific selector scheme.
     *
     * @param candidates a set of candidates for selecting the best of them with
     * a concrete selector operator.
     * @return S, the selected individual.
     * @throws exceptions.IncompatibleConfigurationException when selector is
     * not enabled with the defined configuration or when the Evaluator used
     * throws this exception because of an incorrect dimensionality of the
     * problem.
     */
    public abstract Individual selectIndividual(Individual... candidates) throws IncompatibleConfigurationException;
}
