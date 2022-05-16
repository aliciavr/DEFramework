package dealib.algorithms;

import dealib.components.Individual;
import dealib.components.Population;
import dealib.components.mutators.CurrentToBest1Mutator;
import dealib.components.mutators.Mutator;
import dealib.components.mutators.Rand1Mutator;
import dealib.components.mutators.jDELSMutator;
import planner.configuration.Execution;
import exceptions.IncompatibleConfigurationException;
import static planner.utils.MathTools.RND;

/**
 * LSGOjDE is a class implementing the LSGOjDE algorithm over the abstract base
 * class Algorithm.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class LSGOjDE extends Algorithm {

    /**
     * Pool of strategies.
     */
    private Mutator[] mutators;

    /**
     * Array of F individual values.
     */
    private double[] Fi;

    /**
     * F lower bound for 2nd and 3rd strategies (jDEcurrent-to-best and jDE).
     */
    private double Fl;

    /**
     * F upper bound for 2nd strategy (jDEcurrent-to-best).
     */
    private double Fu2;

    /**
     * F upper bound for 3rd strategy (jDE).
     */
    private double Fu3;

    /**
     * Probability for updating F.
     */
    private double tau1;

    /**
     * Array of CR individual values.
     */
    private double[] CRi;

    /**
     * CR lower bound for all strategies.
     */
    private double CRl;

    /**
     * CR upper bound for 1st strategy (jDELS).
     */
    private double CRu1;

    /**
     * CR upper bound for 2nd strategy (jDEcurrent-to-best).
     */
    private double CRu2;

    /**
     * CR upper bound for 3rd strategy (jDE).
     */
    private double CRu3;

    /**
     * Probability for updating CR.
     */
    private double tau2;

    /**
     * Limit of minimum NP.
     */
    private int limitNP;

    /**
     * Subsize of subpopulations.
     */
    private double subSize;

    /**
     * Lower bound of a subpopulation.
     */
    private int lb;

    /**
     * Upper bound of a subpopulation.
     */
    private int ub;

    /**
     * LSR vector for jDELS represented as Individual.
     */
    private Individual lsr;

    /**
     * Tolerance in jDELS.
     */
    private static final double EPS = 10e-6;

    /**
     * TRUE if the last executed local search was successful, FALSE otherwise.
     */
    private boolean successLastLocalSearch;

    /**
     * TRUE if local search have been used, FALSE otherwise.
     */
    private boolean localSearchUsed;

    /**
     * Constructor of LSGOjDE algorithm.
     *
     * @param exe configuration of the current execution.
     */
    public LSGOjDE(Execution exe) {
        super("LSGOjDE", "", exe);
    }

    /**
     * Executes LSGOjDE algorithm.
     *
     * @return individual as the solution.
     * @return @throws IncompatibleConfigurationException if an incompatibility
     * is detected.
     */
    @Override
    public Individual runAlgorithm() throws IncompatibleConfigurationException {

        initializer.initializePopulation(p);
        p.setActiveSubPopulations(true);

        int i;
        int it = 0;
        while (!stopCriterion.stops()) {
            i = it++ % p.getNP();
            subSize = Math.min(p.getNP(), 200);

            /* Select sub-population lb <= i <= ub.*/
            lb = (int) (Math.floor(i / subSize) * subSize);
            p.setSubPopLB(lb);
            ub = (int) (Math.ceil((i + 1) / subSize) * subSize);
            p.setSubPopUB(ub);
            X = p.getIndividual(i);

            /* Perform one iteration using one of three strategies. */
            if (RND.nextDouble() < 0.1 && it > 0.2 * exe.getMaxStop()) {
                strategyJDELS(i);
            } else if (RND.nextDouble() < 0.2 && it > 0.4 * exe.getMaxStop()) {
                strategyCURRENTTOBEST(i);
            } else {
                strategyJRAND(i);
            }
            checker.checkBounds(exe.getIPR_LB(), exe.getIPR_UB(), V);

            U = crossover.crossIndividuals(p, X, V);
            checker.checkBounds(exe.getIPR_LB(), exe.getIPR_UB(), U);
            U.updateFitness();

            S = selector.selectIndividual(X, U);

            p.replaceIndividual(i, S);

            if (localSearchUsed) {
                if (U.equals(S)) {
                    successLastLocalSearch = true;
                } else {
                    successLastLocalSearch = true;
                }
                localSearchUsed = false;
            }

            if (p.getNP() > limitNP & it % exe.getMaxStop() == 0) {
                p.reducePopulation(0.5);
            }

            if (it % p.getNP() == 0) {
                p.incrPopulationG();
            }
        }
        return p.getBestIndividual();
    }

    /**
     * Executed before running an execution of the Algorithm, it is the place
     * were the initialization process of the components which are going to be
     * used by the Algorithm takes place. In this case it initializes the basic
     * components of sopping criterion, initializer, mutator, crossover,
     * selector and population. Addionally, it initializes an array of Fs by
     * individuals, an array of CRs by individuals and the pool of strategies.
     */
    @Override
    protected void initAlgorithmParams() {
        stopCriterion = exe.getStoppingCriterion();
        initializer = exe.getInitializer();
        mutator = exe.getMutator();
        crossover = exe.getCrossover();
        selector = exe.getSelector();
        p = new Population(exe);

        // Bounds attributes.
        checker = (LB, UB, INDS) -> {
            double v;
            for (int i = 0; i < UB.length; i++) {
                v = INDS[0].getGene(i);
                if (v < LB[i]) {
                    INDS[0].setGene(i, (LB[i] + X.getGene(i)) / 2.0);
                }
                if (v > UB[i]) {
                    INDS[0].setGene(i, (X.getGene(i) + UB[i]) / 2.0);
                }
            }
        };

        // Mutation attributes.
        mutators = new Mutator[3];
        mutators[0] = new jDELSMutator(exe);
        mutators[1] = new CurrentToBest1Mutator(exe);
        mutators[2] = new Rand1Mutator(exe);

        Fi = new double[exe.getNP()];
        Fu2 = 1.0;
        Fu3 = 2.0;
        tau1 = 0.5;

        // Crossover attributes.
        CRi = new double[exe.getNP()];
        CRl = 0.0;
        CRu1 = 0.25;
        CRu2 = 1.2;
        CRu3 = 1.2;
        tau2 = 0.5;

        // Population and subpopulations attributes.
        limitNP = 50;

        // JDELS strategy attributes.
        // Inicializes LSR
        lsr = new Individual(exe);
        for (int i = 0; i < exe.getD(); i++) {
            lsr.setGene(i, 0.4 * (exe.getIPR_UB()[i] - exe.getIPR_LB()[i])
                    + RND.nextDouble() * (exe.getIPR_UB()[i] - exe.getIPR_LB()[i]));
        }
        successLastLocalSearch = false;
        localSearchUsed = false;

    }

    /**
     * Updates F parameter for an individual, i, in the interval [Fl, Fu].
     *
     * @param i index of an individual.
     * @param Fl lower bound for F parameter.
     * @param Fu upper bound for F parameter.
     * @return value of F parameter for given index individual and a given
     * interval.
     */
    private double updateF(int i, double Fl, double Fu) {
        double rnd1 = RND.nextDouble();
        double rnd2 = RND.nextDouble();

        if (rnd1 < tau1) {
            Fi[i] = Fl + rnd2 * Fu;
        }

        exe.setF(Fi[i]);

        return Fi[i];
    }

    /**
     * Updates CR parameter for an individual, i, in the interval [CRl, CRu].
     *
     * @param i index of an individual.
     * @param CRl lower bound for F parameter.
     * @param CRu upper bound for F parameter.
     * @return value of F parameter for given index individual and a given
     * interval.
     */
    private double updateCR(int i, double CRl, double CRu) {
        double rnd1 = RND.nextDouble();
        double rnd2 = RND.nextDouble();

        if (rnd1 < tau2) {
            CRi[i] = CRl + rnd2 * CRu;
        }

        exe.setCR(CRi[i]);

        return CRi[i];
    }

    /**
     * Updates lsr vector for 1st mutateIndividual strategy (jDELS).
     */
    private void updateLSR() {
        if (successLastLocalSearch) {
            for (int i = 0; i < exe.getD(); i++) {
                lsr.setGene(i, lsr.getGene(i) * 1.5);
            }
        } else {
            for (int i = 0; i < exe.getD(); i++) {
                lsr.setGene(i, lsr.getGene(i) / 2.0);
            }
        }

        for (int i = 0; i < exe.getD(); i++) {
            if (Math.abs(lsr.getGene(i)) < EPS * (exe.getIPR_UB()[i] - exe.getIPR_LB()[i])
                    | Math.abs(lsr.getGene(i)) > (exe.getIPR_UB()[i] - exe.getIPR_LB()[i])) {
                lsr.setGene(i, 0.4 * (exe.getIPR_UB()[i] - exe.getIPR_LB()[i])
                        + RND.nextDouble() * (exe.getIPR_UB()[i] - exe.getIPR_LB()[i]));
            }
        }
    }

    /**
     * First out of three strategies.
     *
     * @param i index of current/target individual.
     */
    private void strategyJDELS(int i) throws IncompatibleConfigurationException {
        updateLSR();
        updateCR(i, CRl, CRu1);
        V = mutators[0].mutateIndividual(p, X, lsr);
        localSearchUsed = true;
    }

    /**
     * Second out of three strategies.
     *
     * @param i Index of current/target individual.
     */
    private void strategyCURRENTTOBEST(int i) throws IncompatibleConfigurationException {
        Fl = Math.sqrt(3.0 / subSize);
        updateF(i, Fl, Fu2);
        updateCR(i, CRl, CRu2);
        V = mutators[1].mutateIndividual(p, X);
    }

    /**
     * Third out of three strategies.
     *
     * @param i index of current/target individual.
     */
    private void strategyJRAND(int i) throws IncompatibleConfigurationException {
        Fl = Math.sqrt(3.0 / subSize);
        updateF(i, Fl, Fu3);
        updateCR(i, CRl, CRu3);
        V = mutators[2].mutateIndividual(p, X);
    }

}
