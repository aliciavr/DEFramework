package dealib.algorithms;

import planner.configuration.Execution;
import dealib.components.*;
import dealib.components.initializers.*;
import dealib.components.mutators.*;
import dealib.components.crossovers.*;
import dealib.components.selectors.*;
import dealib.components.stoppingcriteria.StoppingCriterion;
import exceptions.IncompatibleConfigurationException;

/**
 * Algorithm is an abstract class representing the basic scheme of an algorithm
 * of differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class Algorithm {

    /**
     * Name of the algorithm.
     */
    protected String name;

    /**
     * Publication reference of the algorithm.
     */
    protected String reference;

    /**
     * Configuration of the current execution.
     */
    protected Execution exe;

    /**
     * Current/target individual.
     */
    protected Individual X;

    /**
     * Mutant/donor individual.
     */
    protected Individual V;

    /**
     * Trial individual.
     */
    protected Individual U;

    /**
     * Selected individual.
     */
    protected Individual S;

    /**
     * Population of individuals.
     */
    protected Population p;

    /**
     * Configured stopping criterion.
     */
    protected StoppingCriterion stopCriterion;

    /**
     * Configured initializer.
     */
    protected Initializer initializer;

    /**
     * Configured mutator if the algorithm does not use a pool of
     * strategies/mutators.
     */
    protected Mutator mutator;

    /**
     * Configured crossover.
     */
    protected Crossover crossover;

    /**
     * Configured selector.
     */
    protected Selector selector;

    /**
     * Bounds checker operator.
     */
    protected BoundsChecker checker;

    /**
     * Constructor of an Algorithm given the name, the publication reference and
     * the current configuration of the execution.
     *
     * @param name name of the Algorithm.
     * @param reference reference of the publication of the Algorithm.
     * @param exe configuration of the current execution.
     */
    public Algorithm(String name, String reference, Execution exe) {
        this.name = name;
        this.reference = reference;
        this.exe = exe;
    }

    /**
     * Runs an execution of the algorithm after an initialization process of its
     * components.
     *
     * @return Individual representing the solution of the optimization process.
     * @throws IncompatibleConfigurationException if an incompatibility is
     * detected.
     */
    public Individual run() throws IncompatibleConfigurationException {
        initAlgorithmParams();
        return runAlgorithm();
    }

    /**
     * Runs the algorithm over the defined configuration.
     *
     * @return Individual representing the solution of the optimization process.
     * @throws IncompatibleConfigurationException if an incompatibility is
     * detected.
     */
    protected abstract Individual runAlgorithm() throws IncompatibleConfigurationException;

    /**
     * Executed before running an execution of the Algorithm, it is the place
     * were the initialization process of the components which are going to be
     * used by the Algorithm takes place.
     */
    protected abstract void initAlgorithmParams();

    /**
     * Gets the name of the Algorithm.
     *
     * @return the name of the Algorithm.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the publication reference of the Algorithm.
     *
     * @return the publication reference of the Algorothm.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Gets a string representing the visualization by console of the Algorithm.
     *
     * @return the name of the Algorithm.
     */
    @Override
    public String toString() {
        return name;
    }

}
