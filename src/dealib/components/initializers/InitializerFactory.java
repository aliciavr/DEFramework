package dealib.components.initializers;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * InitializerFactory is a class for creating new initializers from the
 * differential evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class InitializerFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private InitializerFactory() {

    }

    /**
     * Creates an Initializer already implemented in the differential evolution
     * algorithms library (dealib) of the framework: given an initializer
     * reduced name, it searchs for the full qualified name in the external
     * resource archive "componentsAlias.properties" and by reflection creates
     * the corresponding initializer operator.
     *
     * @param initializerName reduced initializer name for identifyng the full
     * qualified name of an initializer operator.
     * @param config Execution configuration for the initializer operator.
     * @return an initializer operator.
     */
    public static Initializer createInitializer(String initializerName, Execution config) {
        String fullInitializerName = Reader.getComponentFullName(initializerName);
        Initializer ini = null;
        
        try {
            Class<?> c = Class.forName(fullInitializerName);
            Constructor constructor = c.getConstructor(Execution.class);
            ini = (Initializer) constructor.newInstance(config);
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }

        return ini;
    }
}
