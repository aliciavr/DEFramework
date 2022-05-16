package dealib.components.stoppingcriteria;

/**
 * StoppingCriterion is an interface representing the basic scheme of stopping
 * criterion operator in differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface StoppingCriterion {

    /**
     * Executes a specific stopping criterion scheme.
     *
     * @return TRUE if the stopping criterion is satisfied, FALSE otherwise.
     */
    public abstract boolean stops();
}
