package dealib.algorithms;

import dealib.components.BoundsChecker;
import planner.configuration.Execution;
import dealib.components.Individual;
import dealib.components.Population;
import exceptions.IncompatibleConfigurationException;

/**
 * DE is a class implementing a DE algorithm over the abstract base class
 * Algorithm.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class DE extends Algorithm {

    /**
     * Constructor of DE Algorithm.
     *
     * @param exe configuration of the current execution.
     */
    public DE(Execution exe) {
        super("Differential Evolution (DE)", "", exe);
    }

    /**
     * Runs the Differential Evolution (DE) classic algorithm.
     *
     * @return @throws IncompatibleConfigurationException if an incompatibility
     * is detected.
     */
    @Override
    public Individual runAlgorithm() throws IncompatibleConfigurationException {

        initializer.initializePopulation(p);
        p.evaluatePopulation(exe.getEvaluator());

        while (!stopCriterion.stops()) {
            for (int i = 0; i < p.getNP(); i++) {
                X = p.getIndividual(i);

                V = mutator.mutateIndividual(p, X);
                BoundsChecker.checkBoundsDefault(exe.getIPR_LB(), exe.getIPR_UB(), V);

                U = crossover.crossIndividuals(p, X, V);
                BoundsChecker.checkBoundsDefault(exe.getIPR_LB(), exe.getIPR_UB(), U);
                U.updateFitness();

                S = selector.selectIndividual(X, U);
                p.replaceIndividual(i, S);
            }
            p.incrPopulationG();
        }
        return p.getBestIndividual();
    }

    /**
     * Executed before running an execution of the Algorithm, it is the place
     * were the initialization process of the components which are going to be
     * used by the Algorithm takes place. In this case it initializes the basic
     * components of sopping criterion, initializer, mutator, crossover,
     * selector and population.
     */
    @Override
    protected void initAlgorithmParams() {
        stopCriterion = exe.getStoppingCriterion();
        initializer = exe.getInitializer();
        mutator = exe.getMutator();
        crossover = exe.getCrossover();
        selector = exe.getSelector();
        p = new Population(exe);
    }
}
