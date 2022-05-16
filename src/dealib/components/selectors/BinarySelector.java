package dealib.components.selectors;

import planner.configuration.Execution;
import dealib.components.Individual;
import exceptions.IncompatibleConfigurationException;

/**
 * BinarySelector is a class implementing a binary selection scheme over the
 * interface Selector.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class BinarySelector implements Selector {

    private final Execution exe;
    private int K;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public BinarySelector(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes a binary scheme as a selector. The candidates must be evaluated
     * as a precondition before the use of this operator.
     *
     * @param candidates a set of candidates for selecting the best of them with
     * a concrete selector operator.
     * @throws exceptions.IncompatibleConfigurationException when selector is
     * not enabled with the defined configuration or when the Evaluator used
     * throws this exception because of an incorrect dimensionality of the
     * problem.
     * @return S, the selected individual.
     */
    @Override
    public Individual selectIndividual(Individual... candidates) throws IncompatibleConfigurationException {
        K = candidates.length;
        int best = -1;
        double bestScore = Double.MAX_VALUE;
        double score;

        for (int i = 0; i < K; i++) {
            score = candidates[i].getFitness();
            if (score <= bestScore) {
                best = i;
                bestScore = score;
            }
        }

        return candidates[best];
    }

    /**
     * Gets a string representing the visualization by console of the Selector.
     *
     * @return the name of the Selector.
     */
    @Override
    public String toString() {
        return "Binary";
    }
}
