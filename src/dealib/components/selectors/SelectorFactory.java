package dealib.components.selectors;

import planner.configuration.Execution;
import planner.utils.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * SelectorFactory is a class for creating new selectors from the differential
 * evolution algorithms library (dealib) of the framework.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class SelectorFactory {

    /**
     * Constructor that cannot be used following the Factory Pattern.
     */
    private SelectorFactory() {

    }

    /**
     * Creates a Selector already implemented in the differential evolution
     * algorithms library (dealib) of the framework: given a selector reduced
     * name, it searchs for the full qualified name in the external resource
     * archive "componentsAlias.properties" and by reflection creates the
     * corresponding selector operator.
     *
     * @param selectorName reduced selector name for identifyng the full
     * qualified name of a selector operator.
     * @param exp Execution configuration for the selector operator.
     * @return a selector operator.
     */
    public static Selector createSelector(String selectorName, Execution exp) {
        String fullSelectorName = Reader.getComponentFullName(selectorName);
        Selector selector = null;
        
        try {
            Class<?> c = Class.forName(fullSelectorName);
            Constructor constructor = c.getConstructor(Execution.class);
            selector = (Selector) constructor.newInstance(exp);
        } catch (ClassNotFoundException e) {
            System.err.println("Factory[Incorrect class name]::" + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("Factory[Incorrect constructor name]::" + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Factory[Error in Constructor]::" + e.getMessage());
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException e) {
            System.out.println("Factory[ERROR]::" + e.getMessage());
        }
        return selector;
    }
}
