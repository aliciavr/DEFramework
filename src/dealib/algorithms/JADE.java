package dealib.algorithms;

import dealib.components.Individual;
import dealib.components.Population;
import exceptions.IncompatibleConfigurationException;
import java.util.ArrayList;
import java.util.HashSet;
import planner.configuration.Execution;
import planner.utils.MathTools;
import static planner.utils.MathTools.RND;

/**
 * JADE is a class implementing JADE algorithm over the abstract base class
 * Algorithm. Implements the version of JADE with archive.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class JADE extends Algorithm {

    /**
     * Array of F individual values.
     */
    private double[] Fi;

    /**
     * Array of CR individual values.
     */
    private double[] CRi;

    /**
     * Set of successful F individual values by generation.
     */
    private ArrayList<Double> Sf;

    /**
     * Set of successful CR individual values by generation.
     */
    private ArrayList<Double> Scr;

    /**
     * Archive of individuals.
     */
    private ArrayList<Individual> A;

    /**
     * Mean of F by the Lehmer Mean.
     */
    private double muF;

    /**
     * Mean of CR by the Arithmetic Mean.
     */
    private double muCR;

    /**
     * Deviation for propability distributions.
     */
    private double stdev;

    /**
     * Constant for updating the means.
     */
    private double c;

    /**
     * Percentage of the best individuals to be considered in mutator
     * current-to-pbest.
     */
    private double percentage;

    /**
     * Constructor of JADE with archive algorithm.
     *
     * @param exe experiment configuration for each instance.
     */
    public JADE(Execution exe) {
        super("JADE", "", exe);
    }

    /**
     * Executes JADE wit archive algorithm over the defined experiment
     * configuration.
     *
     * @return @throws IncompatibleConfigurationException if an incompatibility
     * is detected.
     */
    @Override
    public Individual runAlgorithm() throws IncompatibleConfigurationException {

        initializer.initializePopulation(p);
        p.evaluatePopulation(exe.getEvaluator());

        while (!stopCriterion.stops()) {
            for (int i = 0; i < p.getNP(); i++) {
                X = p.getIndividual(i);

                updateF(i);
                updateCR(i);
                V = mutator.mutateIndividual(p, X, getJADEIndividuals());
                checker.checkBounds(exe.getIPR_LB(), exe.getIPR_UB(), V);

                U = crossover.crossIndividuals(p, X, V);
                checker.checkBounds(exe.getIPR_LB(), exe.getIPR_UB(), U);
                U.updateFitness();

                S = selector.selectIndividual(X, U);
                if (S.equals(U)) {
                    A.add(X);
                    Scr.add(CRi[i]);
                    Sf.add(Fi[i]);
                }
                p.replaceIndividual(i, S);

            }

            removeFromArchive();
            updateMuF();
            updateMuCR();
            p.incrPopulationG();
            Scr.clear();
            Sf.clear();
        }

        return p.getBestIndividual();
    }

    /**
     * Executed before running an execution of the Algorithm, it is the place
     * were the initialization process of the components which are going to be
     * used by the Algorithm takes place. In this case it initializes the basic
     * components of sopping criterion, initializer, mutator, crossover,
     * selector and population. Addionally, it initializes an array of Fs by
     * individuals, an array of CRs by individuals and an archive.
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

        Fi = new double[exe.getNP()];
        CRi = new double[exe.getNP()];
        Sf = new ArrayList<>();
        Scr = new ArrayList<>();
        A = new ArrayList<>();
        muCR = exe.getCR();
        muF = exe.getF();
        stdev = 0.1;
        c = 0.5;
        percentage = 0.1;
    }

    /**
     * Updates muF for adapting F parameter using the Lehmer mean over
     * successful F parameters in the last generation.
     */
    private void updateMuF() {
        double lehmerMean = 0.0;
        if (Sf.size() > 0) {
            lehmerMean = MathTools.lehmerMean(Sf);
        }
        muF = (1 - c) * muF + c * lehmerMean;
    }

    /**
     * Updates muCR for adapting CR parameter using the arithmetic mean over
     * successful CR parameters in the last generation.
     */
    private void updateMuCR() {
        double arithmeticMean = 0.0;
        if (Sf.size() > 0) {
            arithmeticMean = MathTools.arithmeticMean(Scr);
        }
        muCR = (1 - c) * muCR + c * arithmeticMean;
    }

    /**
     * Updates F parameter according to muF and stdev parameters.
     *
     * @param i integer representing the current/target individual.
     */
    private void updateF(int i) {
        double cauchyRND = MathTools.cauchyRND(muF, stdev);
        if (cauchyRND > 1.0) {
            Fi[i] = 1.0;
        } else if (cauchyRND < 0.0) {
            Fi[i] = 0.0;
        } else {
            Fi[i] = cauchyRND;
        }
        exe.setF(Fi[i]);
    }

    /**
     * Updates CR parameter according to muCR and stdev parameters.
     *
     * @param i integer representing the current/target individual.
     */
    private void updateCR(int i) {
        if (Double.isNaN(muCR)) {
            System.out.println("muCR");
        }
        CRi[i] = MathTools.normalRND(muCR, stdev);
        exe.setCR(CRi[i]);
    }

    /**
     * Randomly removes solutions from the archive, A, while the size of A is
     * strictly greater than NP.
     */
    private void removeFromArchive() {
        while (A.size() > exe.getNP()) {
            A.remove(RND.nextInt(A.size()));
        }
    }

    /**
     * Randomly choose the JADE individuals needed for the pBest mutation
     * operator.
     *
     * @return array of individuals in the following order: pBest, r1 and r2.
     */
    private Individual[] getJADEIndividuals() {
        Individual[] individuals = new Individual[3];

        // PBest Individual
        int k = RND.nextInt((int) Math.ceil(exe.getNP() * percentage));
        individuals[0] = p.getKBestIndividual(k);

        // R1 Individual
        Individual R1 = p.getNRandDistinctIndividuals(X, 1)[0];
        individuals[1] = R1;

        // R2 Individual
        HashSet<Individual> PUA = new HashSet();
        PUA.addAll(p.getAllIndividuals());
        PUA.addAll(A);
        Individual R2;
        do {
            R2 = (Individual) PUA.toArray()[RND.nextInt(PUA.size())];
        } while (R2.equals(R1) || R2.equals(X));
        individuals[2] = R2;

        return individuals;
    }
}
