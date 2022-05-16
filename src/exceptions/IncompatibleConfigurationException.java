package exceptions;

/**
 * IncompatibleConfigurationException is an Exception subclass for advert an
 * incompatile configuration in experiment configuration files.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class IncompatibleConfigurationException extends Exception {

    /**
     * Construtor.
     *
     * @param msg message with a detailed description of the problem.
     */
    public IncompatibleConfigurationException(String msg) {
        super("IncompatibleConfigurationException[" + msg + "]");
    }
}
