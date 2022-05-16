package planner.utils.evaluators;

import exceptions.IncompatibleConfigurationException;

/**
 * Evaluator is an interface for representing an object that can give an
 * evaluation of the fitness of a candidate solution coded in the genotype.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface Evaluator {

    /**
     * Evaluates a candidate solution.
     *
     * @param genotype candidate solution.
     * @return fitness of the candidate solution.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator.
     */
    public abstract double evaluate(double[] genotype) throws IncompatibleConfigurationException;
}
