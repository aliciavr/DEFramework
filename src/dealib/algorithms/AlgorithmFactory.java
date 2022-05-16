package dealib.algorithms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import planner.configuration.Execution;
import planner.utils.Reader;

/**
 * AlgorithmFactory is a class for creating new algorithms from the differential
 * evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class AlgorithmFactory {
    
    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private AlgorithmFactory() {
        
    }
    
    /**
     * Creates an Algorithm already implemented in the differential evolution
     * algorithms library (dealib) of the framework: given an algorithm reduced 
     * name, it searchs for the full qualified name in the external resource 
     * archive "componentsAlias.properties" and by reflection creates the 
     * corresponding algorithm.
     *
     * @param algorithmName reduced algorithm name for identifyng the full
     * qualified name of an algorithm.
     * @param config Execution configuration for the algorithm.
     * @return an algorithm.
     */
    public static Algorithm createAlgorithm(String algorithmName, Execution config) {
        String fullAlgorithmName = Reader.getComponentFullName(algorithmName);
        Algorithm algorithm = null;
        
        try {
            Class<?> c = Class.forName(fullAlgorithmName);
            Constructor<?> constructor = c.getConstructor(Execution.class);
            constructor.newInstance(config);
            algorithm = (Algorithm) constructor.newInstance(config);
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }
        
        return algorithm;
    }           
}
