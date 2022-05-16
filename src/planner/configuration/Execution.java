package planner.configuration;

import dealib.algorithms.Algorithm;
import dealib.components.Individual;
import dealib.components.initializers.*;
import dealib.components.mutators.*;
import dealib.components.crossovers.*;
import dealib.components.selectors.*;
import dealib.components.stoppingcriteria.StoppingCriterion;
import exceptions.IncompatibleConfigurationException;
import planner.utils.CrossValidation;
import planner.utils.Dataset;
import planner.utils.ExecutionResults;
import planner.utils.evaluators.Evaluator;
import planner.utils.objfunctions.ObjectiveFunction;

/**
 * Execution is a class for support an execution of an Experiment with a defined
 * configuration, which exports the results after finishing.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Execution {

    private static int NEXT_EXECUTION_ID = 0;
    private String EXECUTION_ID;
    private ExecutionResults results;

    private Algorithm algorithm;
    private Dataset dataset;
    private CrossValidation CV;
    private int NP;
    private int D;
    private double F;
    private double CR;
    private double[] IPR_LB;
    private double[] IPR_UB;
    private StoppingCriterion stopping;
    private int maxStop;
    private Initializer initializer;
    private Mutator mutator;
    private Crossover crossover;
    private Selector selector;
    private Evaluator evaluator;
    private ObjectiveFunction objFunction;

    public Execution() {
        EXECUTION_ID = Integer.toString(NEXT_EXECUTION_ID++);
        results = new ExecutionResults(EXECUTION_ID);
    }

    public ExecutionResults run() {
        results.setAlgorithm(algorithm.toString());
        if (CV != null) {
            results.setCV(CV.getK());
            results.setDataset(dataset.getPath());
            results.setObjFunction(objFunction.toString());
        }
        results.setD(D);
        results.setStoppingCriterion(stopping.toString());
        results.setMaxStop(maxStop);
        results.setInitializer(initializer.toString());
        results.setMutator(mutator.toString());
        results.setCrossover(crossover.toString());
        results.setSelector(selector.toString());
        results.setEvaluator(evaluator.toString());
        System.out.println(this);
        try {
            long initTime = System.currentTimeMillis();
            Individual ind = algorithm.run();
            long finalTime = System.currentTimeMillis();
            results.setTime(finalTime - initTime);
            results.setSolution(ind.getGenotype());
            results.setSolutionFitness(ind.getFitness());        
            System.out.println("FITNESS = " + results.getSolutionFitness());
        } catch (IncompatibleConfigurationException e) {
            results.setSuccessful(false);
            System.err.println(e.getMessage());
        }

        return results;
    }

    public String getEXECUTION_ID() {
        return EXECUTION_ID;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public CrossValidation getCV() {
        return CV;
    }

    public int getNP() {
        return NP;
    }

    public double[] getIPR_LB() {
        return IPR_LB;
    }

    public double[] getIPR_UB() {
        return IPR_UB;
    }

    public ExecutionResults getResults() {
        return results;
    }

    public int getMaxStop() {
        return maxStop;
    }

    public int getD() {
        return D;
    }

    public double getF() {
        return F;
    }

    public double getCR() {
        return CR;
    }

    public StoppingCriterion getStoppingCriterion() {
        return stopping;
    }

    public Initializer getInitializer() {
        return initializer;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public Crossover getCrossover() {
        return crossover;
    }

    public Selector getSelector() {
        return selector;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objFunction;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setNP(int NP) {
        this.NP = NP;
        results.addNP(NP);
    }

    public void setLowerBounds(double[] IPR_LB) {
        this.IPR_LB = IPR_LB;
    }

    public void setUpperBounds(double[] IPR_UB) {
        this.IPR_UB = IPR_UB;
    }

    public void setResults(ExecutionResults results) {
        this.results = results;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public void setCV(CrossValidation CV) {
        this.CV = CV;
    }

    public void setD(int D) {
        this.D = D;
    }

    public void setF(double F) {
        this.F = F;
    }

    public void setCR(double CR) {
        this.CR = CR;
    }

    public void setEXECUTIONCONFIG_ID(int id) {
        EXECUTION_ID = Integer.toString(id) + EXECUTION_ID;
    }

    public void setFitness(double fitness) {
        results.addFitness(fitness);
    }

    public void incrGenerations() {
        results.incrNumGenerations();
    }

    public void incrGenerations(int numGenerations) {
        results.incrNumGenerations(numGenerations);
    }

    public void incrFEs() {
        results.incrNumFEs();
    }

    public void incrFEs(int numFEs) {
        results.incrNumFEs(numFEs);
    }

    public void setStoppingCriterion(StoppingCriterion stopping) {
        this.stopping = stopping;
    }

    public void setMaxStop(int maxStop) {
        this.maxStop = maxStop;
    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }

    public void setCrossover(Crossover crossover) {
        this.crossover = crossover;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setObjectiveFunction(ObjectiveFunction objFunction) {
        this.objFunction = objFunction;
    }

    @Override
    public String toString() {
        String str = "----------------------------------------------------------";
        str += "\nExecution:" + EXECUTION_ID;
        str += "\n----------------------------------------------------------";
        str += "\nAlgorithm: " + algorithm;
        str += "\nD: " + D;
        str += "\nNP: " + NP;
        str += "\nStopping criterion: " + stopping;
        str += "\nInitializer: " + initializer;
        str += "\nMutator: " + mutator;
        str += "\nCrossover: " + crossover;
        str += "\nSelector: " + selector;
        str += "\nEvaluator: " + evaluator;
        if (dataset != null) {
            str += "\nDataset:\n" + dataset;
            str += "\nObjective Function: " + objFunction;
            str += "\nCV: " + CV;
        }
        return str;
    }

}
