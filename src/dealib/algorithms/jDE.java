package dealib.algorithms;

import dealib.components.BoundsChecker;
import dealib.components.Individual;
import dealib.components.Population;
import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;
import static planner.utils.MathTools.RND;

/**
 * jDE is a class implementing the jDE algorithm (2006) over the abstract base
 * class Algorithm.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class jDE extends Algorithm {

    /**
     * Lower bound for F parameter.
     */
    private double Fl;

    /**
     * Upper bound for F parameter.
     */
    private double Fu;

    /**
     * Array for F individual values.
     */
    private double[] Fi;

    /**
     * Array for CR individual values.
     */
    private double[] CRi;

    /**
     * Factor for the updating process F.
     */
    private double tau1;

    /**
     * Factor for the updating process CR.
     */
    private double tau2;

    /**
     * Constructor of the Adapatative Differential Evolution (jDE) algorithm
     * (2006 version).
     *
     * @param exe configuration of the current execution.
     */
    public jDE(Execution exe) {
        super("Adaptative Differential Evolution (jDE, 2006)", "", exe);
    }

    /**
     * Runs the Adaptative version of Differential Evolution (jDE) algorithm
     * (2006 version).
     *
     * @return @throws IncompatibleConfigurationException if an incompatibility
     * is detected.
     */
    @Override
    public Individual runAlgorithm() throws IncompatibleConfigurationException {

        initializer.initializePopulation(p);
        p.evaluatePopulation(exe.getEvaluator());
        
        while (!stopCriterion.stops()) {
            for (int i = 0; i < exe.getNP(); i++) {
                X = p.getIndividual(i);

                updateF(i);
                V = mutator.mutateIndividual(p, X);
                BoundsChecker.checkBoundsDefault(exe.getIPR_LB(), exe.getIPR_UB(), V);

                updateCR(i);
                U = crossover.crossIndividuals(p, X, V);
                BoundsChecker.checkBoundsDefault(exe.getIPR_LB(), exe.getIPR_UB(), U);
                U.updateFitness();

                S = selector.selectIndividual(X, U);
                p.replaceIndividual(i, S);
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
     * selector and population with limits for F parameter and a individual F
     * and CR scheme.
     */
    @Override
    protected void initAlgorithmParams() {
        stopCriterion = exe.getStoppingCriterion();
        initializer = exe.getInitializer();
        mutator = exe.getMutator();
        crossover = exe.getCrossover();
        selector = exe.getSelector();
        p = new Population(exe);

        Fl = 0.1;
        Fu = 0.9;
        Fi = new double[exe.getNP()];
        CRi = new double[exe.getNP()];
        for (int i = 0; i < exe.getNP(); i++) {
            Fi[i] = exe.getF();
            CRi[i] = exe.getCR();
        }

        tau1 = 0.1f;
        tau2 = 0.1f;
    }

    /**
     * Updates the value of F for the current Individual at a given stage of the
     * evolution process.
     *
     * @param i index of the current Individual.
     * @return the new value of F for the i Individual.
     */
    private double updateF(int i) {
        float rnd1 = RND.nextFloat();
        float rnd2 = RND.nextFloat();
        if (rnd1 < tau1) {
            Fi[i] = Fl + rnd2 * Fu;
        }

        exe.setF(Fi[i]);

        return Fi[i];
    }

    /**
     * Updates the value of CR for the current Individual at a given stage of
     * the evolution process.
     *
     * @param i index of the current Individual.
     * @return the new value of CR for the i Individual.
     */
    private double updateCR(int i) {
        float rnd1 = RND.nextFloat();
        float rnd2 = RND.nextFloat();

        if (rnd1 < tau2) {
            CRi[i] = rnd2;
        }

        exe.setCR(CRi[i]);

        return CRi[i];
    }

}
