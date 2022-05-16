package dealib.components.crossovers;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * CrossoverFactory is a class for creating new crossovers from the differential
 * evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class CrossoverFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private CrossoverFactory() {

    }

    /**
     * Creates a Crossover already implemented in the differential evolution
     * algorithms library (dealib) of the framework: given a crossover reduced
     * name, it searchs for the full qualified name in the external resource
     * archive "componentsAlias.properties" and by reflection creates the
     * corresponding crossover operator.
     *
     * @param crossoverName reduced crossover name for identifyng the full
     * qualified name of a crossover operator.
     * @param config Execution configuration for the croosover operator.
     * @return a crossover operator.
     */
    public static Crossover createCrossover(String crossoverName, Execution config) {
        String fullCrossoverName = Reader.getComponentFullName(crossoverName);
        Crossover crossover = null;
        
        try {
            Class<?> c = Class.forName(fullCrossoverName);
            Constructor constructor = c.getConstructor(Execution.class);
            crossover = (Crossover) constructor.newInstance(config);
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }

        return crossover;
    }
}
