package exceptions;

/**
 * ExperimentFormatException is an Exception subclass for advert an incorrect
 * formatting in experiment configuration files.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class ExperimentFormatException extends Exception {

    /**
     * Construtor.
     *
     * @param msg message with a detailed description of the problem.
     */
    public ExperimentFormatException(String msg) {
        super("ExperimentFormatException[" + msg + "]");
    }
}
