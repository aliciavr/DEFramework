package dealib.components;

/**
 * BoundsChecker is an interface for checking the factibiliy of the candidate
 * solutions coded in the genotype of an Individual.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public interface BoundsChecker {

    /**
     * Enables implementing another checking scheme distinct from default one.
     *
     * @param LB lower bounds of the problem domain.
     * @param UB upper bounds of the problem domain.
     * @param individuals array of individuals that may be needed for the
     * scheme.
     */
    public abstract void checkBounds(double[] LB, double[] UB, Individual... individuals);

    /**
     * Default method for checking the factibility of the given Individual: if
     * the values are above the upper bounds, it is replaced by the maximum
     * possible value and analogously with lower bounds.
     *
     * @param LB lower bounds of the problem domain.
     * @param UB upper bounds of the problem domain.
     * @param V individual to be checked.
     */
    public static void checkBoundsDefault(double[] LB, double[] UB, Individual V) {
        double x;
        int D = V.getGenotype().length;
        for (int i = 0; i < D; i++) {
            x = V.getGenotype()[i];
            if (x < LB[i]) {
                V.setGene(i, LB[i]);
            }
            if (x > UB[i]) {
                V.setGene(i, UB[i]);
            }
        }
    }
}
