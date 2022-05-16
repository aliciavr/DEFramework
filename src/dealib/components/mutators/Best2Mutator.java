package dealib.components.mutators;

import planner.configuration.Execution;
import dealib.components.Individual;
import dealib.components.Population;

/**
 * Best2Mutator is a class implementing DE/best/2 mutation scheme over the
 * interface Mutator.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Best2Mutator implements Mutator {

    private final Execution exe;
    private static final int NUM_RANDOM_IND = 4;
    private static final int R1 = 0;
    private static final int R2 = 1;
    private static final int R3 = 2;
    private static final int R4 = 3;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public Best2Mutator(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes DE/best/2 over the target/current individual, X.
     *
     * @param p current population.
     * @param X the target/current individual.
     * @param individuals some possible auxiliary individuals, not used in this
     * scheme.
     * @return mutant vector, V.
     */
    @Override
    public Individual mutateIndividual(Population p, Individual X, Individual... individuals) {
        double D = exe.getD();
        double F = exe.getF();
        Individual V = new Individual(exe);

        double[] best = p.getBestIndividual().getGenotype();

        Individual[] rndInd = p.getNRandDistinctIndividuals(X, NUM_RANDOM_IND);
        double[] r1 = rndInd[R1].getGenotype();
        double[] r2 = rndInd[R2].getGenotype();
        double[] r3 = rndInd[R3].getGenotype();
        double[] r4 = rndInd[R4].getGenotype();

        for (int k = 0; k < D; k++) {
            V.setGene(k, best[k] + F * (r1[k] - r2[k]) + F * (r3[k] - r4[k]));
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
        return "DE/best/2";
    }

}
