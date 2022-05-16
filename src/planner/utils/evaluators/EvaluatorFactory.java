package planner.utils.evaluators;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * EvaluatorFactory is a class for creating new evaluators from the differential
 * evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class EvaluatorFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private EvaluatorFactory() {

    }

    /**
     * Creates an Evaluator already implemented in the differential evolution
     * algorithms library (dealib) of the framework: given an evaluator reduced
     * name, it searchs for the full qualified name in the external resource
     * archive "componentsAlias.properties" and by reflection creates the
     * corresponding evaluator.
     *
     * @param evaluatorName reduced evaluator name for identifyng the full
     * qualified name of an evaluator.
     * @param config Execution configuration for the evaluator operator.
     * @return an evaluator operator.
     */
    public static Evaluator createEvaluator(String evaluatorName, Execution config) {
        String fullEvaluatorName = Reader.getComponentFullName(evaluatorName);
        Evaluator evaluator = null;
        
        try {
            Class<?> c = Class.forName(fullEvaluatorName);
            Constructor constructor = c.getConstructor(Execution.class);
            evaluator = (Evaluator) constructor.newInstance(config);
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }

        return evaluator;
    }
}
