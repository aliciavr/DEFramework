package dealib.components.stoppingcriteria;

import planner.configuration.Execution;

/**
 * MaxGenerationsStop is a class implementing a stopping criterion based on the
 * number of generations performed over the interface Selector.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class MaxGenerationsStop implements StoppingCriterion {

    private final Execution exe;

    /**
     * Constructor.
     *
     * @param exe Configurarion of the current execution.
     */
    public MaxGenerationsStop(Execution exe) {
        this.exe = exe;
    }

    /**
     * Executes a stopping criterion based on the number of generations
     * performed.
     *
     * @return TRUE if the stopping criterion is satisfied, FALSE otherwise.
     */
    @Override
    public boolean stops() {
        int value = exe.getResults().getNumGenerations();
        boolean stops = false;
        if (exe.getMaxStop() <= value) {
            stops = true;
        }
        return stops;
    }

    /**
     * Gets a string representing the visualization by console of the Stopping
     * Criterion.
     *
     * @return the condition of the Stopping Criterion.
     */
    @Override
    public String toString() {
        return "SC: Stops when " + exe.getMaxStop() + " generations have been"
                + " executed.";
    }

}
