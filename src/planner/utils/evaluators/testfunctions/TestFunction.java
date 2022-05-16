package planner.utils.evaluators.testfunctions;

import planner.configuration.Execution;
import planner.utils.evaluators.Evaluator;

/**
 * TestFunction is an abstract class for representing the functions used for
 * testing and experimental issues, which can be also considerated as evaluators
 * of the genotype of the individuals.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class TestFunction implements Evaluator {

    /**
     * Configuration of the current execution.
     */
    protected final Execution exe;

    /**
     * Constructor.
     *
     * @param exe configuration of the current execution.
     */
    public TestFunction(Execution exe) {
        this.exe = exe;
    }

}
