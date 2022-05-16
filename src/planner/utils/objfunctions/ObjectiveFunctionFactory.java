package planner.utils.objfunctions;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * ObjectiveFunctionFactory is a class for creating new evaluators from the
 * differential evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class ObjectiveFunctionFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private ObjectiveFunctionFactory() {

    }

    /**
     * Creates an ObjectiveFunctionFactory already implemented in the
     * differential evolution algorithms library (dealib) of the framework:
     * given an objective function reduced name, it searchs for the full
     * qualified name in the external resource archive
     * "componentsAlias.properties" and by reflection creates the corresponding
     * objective function.
     *
     * @param objFunctionName reduced objective function name for identifyng the
     * full qualified name of an objective function.
     * @return an objective function operator.
     */
    public static ObjectiveFunction createObjectiveFunction(String objFunctionName) {
        String fullObjFunctionName = Reader.getComponentFullName(objFunctionName);
        ObjectiveFunction objFunction = null;
        try {
            Class<?> c = Class.forName(fullObjFunctionName);
            Constructor constructor = c.getConstructor();
            objFunction = (ObjectiveFunction) constructor.newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }

        return objFunction;
    }
}
