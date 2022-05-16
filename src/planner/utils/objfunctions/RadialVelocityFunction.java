package planner.utils.objfunctions;

/**
 * RadialVelocityFunction is a class implementing the estimation of the radial
 * velocity of a binary stellar system from a candidate solution and a set of
 * input variables.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class RadialVelocityFunction implements ObjectiveFunction {

    /**
     * Index for GAMMA variable in the candidate solution.
     */
    private static final int GAMMA = 0;

    /**
     * Index for K variable in the candidate solution.
     */
    private static final int K = 1;

    /**
     * Index for OMEGA variable in the candidate solution.
     */
    private static final int OMEGA = 2;

    /**
     * Index for EXCENTRICITY variable in the candidate solution.
     */
    private static final int EXCENTRICITY = 3;

    /**
     * Index for PERIASTER_PHASE variable in the candidate solution.
     */
    private static final int PERIASTER_PHASE = 4;

    /**
     * Index for PHASE input variable.
     */
    private static final int PHASE_INDEX = 0;

    /**
     * True anomaly.
     */
    private double V; //< True anomaly

    /**
     * Phase for the radial velocity.
     */
    private double PHASE;

    /**
     * Tolerance required for computing true anomaly.
     */
    private final double TOLERANCE = 10E-8;

    /**
     * Computes the radial velocity estimation for a binary stellar system given
     * a candidate solution and a set of input variables.
     *
     * @param genotype candidate solution.
     * @param input input variables neede for the estimation.
     * @return the estimation.
     */
    @Override
    public double compute(double[] genotype, double[] input) {
        double radialVelocity;
        PHASE = input[PHASE_INDEX];
        computeTrueAnomaly(genotype);
        radialVelocity = genotype[GAMMA] + genotype[K] * Math.cos(genotype[OMEGA] + V) + genotype[EXCENTRICITY] * Math.cos(genotype[OMEGA]);

        return radialVelocity;
    }

    /**
     * Auxiliar function for computing true anomaly.
     *
     * @param genotype candidate solution.
     */
    private void computeTrueAnomaly(double[] genotype) {
        double M; // Mean anomaly
        double E; // Excentric anomaly
        double Eaux; // Auxiliar excentric anomaly
        M = 2 * Math.PI * (PHASE - PERIASTER_PHASE);
        E = M;
        Eaux = 0.0;
        while (Math.abs(E - Eaux) > TOLERANCE) {
            Eaux = E;
            E = M + genotype[EXCENTRICITY] * Math.sin(Eaux);
        }
        V = 2.0 * Math.atan(Math.sqrt((1 + genotype[EXCENTRICITY]) / (1 - genotype[EXCENTRICITY])) * Math.tan(E / 2.0));
    }
}
