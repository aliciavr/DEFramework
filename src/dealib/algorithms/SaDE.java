package dealib.algorithms;

import dealib.components.Individual;
import dealib.components.Population;
import dealib.components.mutators.*;
import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;
import planner.utils.MathTools;
import static planner.utils.MathTools.RND;

/**
 * SaDE is a class implementing the SaDE algorithm over the abstract base class
 * Algorithm.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class SaDE extends Algorithm {

    /**
     * Pool of strategies.
     */
    private Mutator[] mutators;

    /**
     * Tolerance.
     */
    private static final double EPSILON = 10E-2;

    /**
     * Number of strategies.
     */
    private int K;

    /**
     * Learning period.
     */
    private int LP;

    /**
     * Strategies probabilities.
     */
    private double[] pK;

    /**
     * Mean CR by strategy.
     */
    private double[] CRmk;

    /**
     * Stdev of CR.
     */
    private double CRstdev;

    /**
     * Mean of F.
     */
    private double Fm;

    /**
     * Stdev of F.
     */
    private double Fstdev;

    // Memories
    /**
     * Success memory.
     */
    private double[][] successMemory;

    /**
     * Failure memory.
     */
    private double[][] failureMemory;

    /**
     * CR memory.
     */
    private double[][] CRMemory;

    /**
     * Additional pointer for updating memories.
     */
    private int memoryPointer;

    /**
     * Constructor of SaDE algorithm.
     *
     * @param exp
     */
    public SaDE(Execution exp) {
        super("Self-adaptative Differential Evolution (SaDE)", "", exp);
    }

    /**
     * Executes SaDE algorithm over the defined experiment configuration.
     *
     * @return @throws IncompatibleConfigurationException if an incompatibility
     * is detected.
     */
    @Override
    public Individual runAlgorithm() throws IncompatibleConfigurationException {

        initializer.initializePopulation(p);
        p.evaluatePopulation(exe.getEvaluator());
        
        while (!stopCriterion.stops()) {
            /**
             * Step 3.1. Calculate strategy probability p_k,G and update the the
             * Success and Failure Memory.
             */
            if (p.getCurrentG() > LP) {
                updateProbabilites();
                /* Elder values of sucess and failure memories are overwritten
                *  later updating memories. */
            }

            /**
             * Step 3.2. Assign trial vector generation strategy and parameter
             * to each target vector X_i,G.
             */
            int[] chosenStrategy = new int[p.getNP()];
            double[] chosenCR = new double[p.getNP()];
            double[] chosenF = new double[p.getNP()];
            for (int i = 0; i < p.getNP(); i++) {

                /* Assign trial vector strategy */
                chosenStrategy[i] = selectStrategy();

                /* Assign control parameter F */
                chosenF[i] = MathTools.normalRND(Fm, Fstdev);
            }

            /* Assign control parameter CR */
            if (p.getCurrentG() >= LP) {
                for (int k = 0; k < K; k++) {
                    CRmk[k] = MathTools.arithmeticMean(CRMemory[k]);
                }
            }

            for (int i = 0; i < p.getNP(); i++) {
                do {
                    chosenCR[i] = MathTools.normalRND(CRmk[chosenStrategy[i]], CRstdev);
                } while (chosenCR[i] < 0.0 || chosenCR[i] > 1.0);
            }
            /**
             * Step 3.3. Generate a new population where each trial vector
             * U_k_i,G is generated according to associated trial generation
             * strategy k and parameters F_i and CR_k,i in Step 3.2.
             */
            /**
             * Step 3.4. Randomly reinitialize the trial vector U_k_i,G within
             * the search space if any variable is outside its boundaries.
             */
            for (int i = 0; i < p.getNP(); i++) {
                X = p.getIndividual(i);
                applyStrategy(chosenStrategy[i], chosenCR[i], chosenF[i]);
                U.updateFitness();
                /**
                 * Step 3.5. Selection:
                 */
                S = selector.selectIndividual(X, U);
                p.replaceIndividual(i, S);
                updateMemories(chosenStrategy[i], chosenCR[i]);
            }
            p.incrPopulationG();
        }
        return p.getBestIndividual();
    }

    /**
     * Executed before running an execution of the Algorithm, it is the place
     * were the initialization process of the components which are going to be
     * used by the Algorithm takes place. In this case it initializes the basic
     * components of sopping criterion, initializer, mutator, crossover,
     * selector and population. Addionally, it initializes an array of Fs by
     * individuals, an array of CRs by individuals and the necessary memories.
     */
    @Override
    protected void initAlgorithmParams() {
        stopCriterion = exe.getStoppingCriterion();
        initializer = exe.getInitializer();
        crossover = exe.getCrossover();
        selector = exe.getSelector();
        p = new Population(exe);

        K = 4;
        LP = 50;
        Fm = exe.getF();
        Fstdev = 0.3;
        CRstdev = 0.1;
        CRmk = new double[K];
        for (int k = 0; k < K; k++) {
            CRmk[k] = exe.getCR();
        }
        successMemory = new double[LP][K];
        failureMemory = new double[LP][K];
        CRMemory = new double[K][LP];
        for (int k = 0; k < K; k++) {
            for (int i = 0; i < LP; i++) {
                CRMemory[k][i] = exe.getCR();
            }
        }
        memoryPointer = 0;
        pK = new double[K];
        for (int k = 0; k < K; k++) {
            pK[k] = 1.0 / K;
        }

        mutators = new Mutator[K];
        mutators[0] = new Rand1Mutator(exe);
        mutators[1] = new Rand2Mutator(exe);
        mutators[2] = new CurrentToRand1Mutator(exe);
        mutators[3] = new CurrentToBest2Mutator(exe);
    }

    /**
     * Updates probabilities associated to each generation strategy.
     */
    private void updateProbabilites() {
        double sum = 0.0;
        double[] s = new double[K];
        for (int k = 0; k < K; k++) {
            s[k] = S(k);
            sum += s[k];
        }
        for (int k = 0; k < K; k++) {
            pK[k] = s[k] / sum;
        }
    }

    /**
     * Auxiliary function for updating probabilites. Computes the rate of sucess
     * of each generation strategy.
     *
     * @param k Integer identifying a generation strategy.
     * @return Real representing the rate of success of the k generation
     * strategy.
     */
    private double S(int k) {
        double sumNS = 0.0;
        double sumNF = 0.0;
        int G = p.getCurrentG();
        for (int g = G - LP, i = 0; g < G; g++, i++) {
            sumNS += successMemory[i][k];
            sumNF += failureMemory[i][k];
        }

        return sumNS / (sumNS + sumNF) + EPSILON;
    }

    /**
     * Selects 1 of 4 candidate strategies: mutation and crossover step. (last
     * one if needed),
     */
    private int selectStrategy() {
        int chosenK = -1;
        double rnd = RND.nextDouble();
        double probAcc = 0.0;
        boolean found = false;
        for (int k = 0; !found & k < K; k++) {
            probAcc += pK[k];
            if (rnd <= probAcc) {
                chosenK = k;
                found = true;
            }
        }
        return chosenK;
    }

    private void applyStrategy(int k, double CRi, double Fi) throws IncompatibleConfigurationException {
        exe.setCR(CRi);
        exe.setF(Fi);
        V = mutators[k].mutateIndividual(p, X);
        if (k != 3) {
            U = crossover.crossIndividuals(p, X, V);
        } else {
            U = V;
        }
    }

    /**
     * Updates success, failure and CR memories after selecting the best
     * individual of a trial generation strategy.
     *
     * @param k Integer representing the applied strategy to the current
     * individual X.
     * @param CR Real representing the CR rate used in the applied k strategy.
     */
    private void updateMemories(int k, double CR) {
        if (S.equals(U)) {
            /* Sucess: f(U) <= f(X)*/
            successMemory[memoryPointer][k]++;
            /* Update CR memory */
            CRMemory[k][memoryPointer] = CR;
            /* Update best individual */
        } else {
            /* Failure: f(U) > f(X)*/
            failureMemory[memoryPointer][k]++;
        }
        memoryPointer = (memoryPointer + 1) % LP;
    }
}
