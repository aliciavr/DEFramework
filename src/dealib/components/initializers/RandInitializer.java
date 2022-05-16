package dealib.components.initializers;

import planner.configuration.Execution;
import dealib.components.*;
import exceptions.IncompatibleConfigurationException;
import static planner.utils.MathTools.RND;

/**
 * RandInitializer is a class implementing a random scheme for the
 * initialization step over the interface Initializer.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RandInitializer implements Initializer {

    private final Execution exe;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public RandInitializer(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes rand initializer scheme over the a population, p.
     *
     * @param p current population.
     * @throws exceptions.IncompatibleConfigurationException when initializer is
     * not enabled with the defined configuration or when the Evaluator used
     * throws this exception because of an incorrect dimensionality of the
     * problem.
     */
    @Override
    public void initializePopulation(Population p) throws IncompatibleConfigurationException {
        double[] lowerBounds = exe.getIPR_LB();
        double[] upperBounds = exe.getIPR_UB();
        Individual ind;
        for (int i = 0; i < exe.getNP(); i++) {
            ind = new Individual(exe);
            for (int gen = 0; gen < exe.getD(); gen++) {
                ind.setGene(gen, RND.nextDouble() * (upperBounds[gen] - lowerBounds[gen]) + lowerBounds[gen]);
            }
            p.addIndividual(ind);
        }
    }

    /**
     * Gets a string representing the visualization by console of the
     * Initializer.
     *
     * @return the name of the Initializer.
     */
    @Override
    public String toString() {
        return "Random";
    }

}
