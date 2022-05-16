package dealib.components.stoppingcriteria;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * StoppingCriterionFactory is a class for creating new stopping criteria
 * operators from the differential evolution algorithms library (dealib) of the
 * framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class StoppingCriterionFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private StoppingCriterionFactory() {

    }

    /**
     * Creates an StoppingCriterionFactory already implemented in the
     * differential evolution algorithms library (dealib) of the framework:
     * given an stopping criterion reduced name, it searchs for the full
     * qualified name in the external resource archive
     * "componentsAlias.properties" and by reflection creates the corresponding
     * stopping criterion operator.
     *
     * @param stoppingCriterionName reduced stopping criterion name for
     * identifyng the full qualified name of a stopping criterion.
     * @param exe Execution configuration for the stopping criterion operator.
     * @return a stopping criterion operator.
     */
    public static StoppingCriterion createStoppingCriterion(String stoppingCriterionName, Execution exe) {
        String fullStoppingCriterionName = Reader.getComponentFullName(stoppingCriterionName);
        StoppingCriterion stoppingCriterion = null;
        try {
            Class<?> c = Class.forName(fullStoppingCriterionName);
            Constructor constructor = c.getConstructor(Execution.class);
            stoppingCriterion = (StoppingCriterion) constructor.newInstance(exe);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            System.err.println("Incorrect naming for this component. " + e.getMessage());
        }

        return stoppingCriterion;
    }
}
