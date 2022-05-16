package dealib.components.mutators;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * MutatorFactory is a class for creating new mutators from the differential
 * evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class MutatorFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private MutatorFactory() {

    }

    /**
     * Creates a Mutator already implemented in the differential evolution
     * algorithms library (dealib) of the framework: given a mutator reduced
     * name, it searchs for the full qualified name in the external resource
     * archive "componentsAlias.properties" and by reflection creates the
     * corresponding mutator operator.
     *
     * @param mutatorName reduced mutator name for identifyng the full qualified
     * name of a mutator operator.
     * @param config Execution configuration for the mutator operator.
     * @return a mutator operator.
     */
    public static Mutator createMutator(String mutatorName, Execution config) {
        String fullMutatorName = Reader.getComponentFullName(mutatorName);
        Mutator m = null;

        try {
            Class<?> c = Class.forName(fullMutatorName);
            Constructor constructor = c.getConstructor(Execution.class);
            m = (Mutator) constructor.newInstance(config);
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }

        return m;
    }
}
