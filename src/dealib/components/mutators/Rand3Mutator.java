package dealib.components.mutators;

import planner.configuration.Execution;
import dealib.components.Individual;
import dealib.components.Population;

/**
 * Rand3Mutator is a class implementing DE/rand/3 mutation scheme over the
 * interface Mutator.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Rand3Mutator implements Mutator {

    private final Execution exe;
    private static final int NUM_RANDOM_IND = 7;
    private static final int R1 = 0;
    private static final int R2 = 1;
    private static final int R3 = 2;
    private static final int R4 = 3;
    private static final int R5 = 4;
    private static final int R6 = 5;
    private static final int R7 = 6;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public Rand3Mutator(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes DE/rand/3 over the target/current individual, X.
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
        Individual V = new Individual(exe, p.getCurrentG());

        Individual[] rndInd = p.getNRandDistinctIndividuals(X, NUM_RANDOM_IND);
        double[] r1 = rndInd[R1].getGenotype();
        double[] r2 = rndInd[R2].getGenotype();
        double[] r3 = rndInd[R3].getGenotype();
        double[] r4 = rndInd[R4].getGenotype();
        double[] r5 = rndInd[R5].getGenotype();
        double[] r6 = rndInd[R6].getGenotype();
        double[] r7 = rndInd[R7].getGenotype();

        for (int k = 0; k < D; k++) {
            V.setGene(k, r1[k] + F * (r2[k] - r3[k])
                    + F * (r4[k] - r5[k])
                    + F * (r6[k] - r7[k]));
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
        return "DE/rand/3";
    }
}
