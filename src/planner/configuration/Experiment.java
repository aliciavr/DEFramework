package planner.configuration;

import dealib.algorithms.AlgorithmFactory;
import dealib.components.initializers.*;
import dealib.components.mutators.*;
import dealib.components.crossovers.*;
import dealib.components.selectors.*;
import java.util.ArrayList;
import planner.utils.evaluators.EvaluatorFactory;
import dealib.components.stoppingcriteria.StoppingCriterionFactory;
import planner.utils.CrossValidation;
import planner.utils.ExecutionResults;
import planner.utils.Reader;
import planner.utils.evaluators.regrmetrics.RegressionMetric;
import planner.utils.objfunctions.ObjectiveFunctionFactory;

/**
 * Experiment is a class for support the configuration of an Experiment.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Experiment {

    private static int NEXT_EXPERIMENT_ID = 0;
    private final int EXPERIMENT_ID;
    private static int EXECUTIONCONFIG_ID = 0;

    ArrayList<ArrayList<ExecutionResults>> executionsResults;

    private final Parameter paramRepetitions;
    private final Parameter paramAlgorithm;
    private final Parameter paramDataset;
    private final Parameter paramCV;
    private final Parameter paramIPR_LB;
    private final Parameter paramIPR_UB;
    private final Parameter paramNP;
    private final Parameter paramD;
    private final Parameter paramF;
    private final Parameter paramCR;
    private final Parameter paramStoppingCriterion;
    private final Parameter paramMaxStop;
    private final Parameter paramInitializer;
    private final Parameter paramMutator;
    private final Parameter paramCrossover;
    private final Parameter paramSelector;
    private final Parameter paramTestFunction;
    private final Parameter paramRegressionMetric;
    private final Parameter paramObjectiveFunction;

    private Integer currentRepetitions;
    private String currentAlgorithm;
    private String currentDataset;
    private CrossValidation currentCV;
    private double[] currentIPR_LB;
    private double[] currentIPR_UB;
    private Integer currentNP;
    private Integer currentD;
    private Double currentF;
    private Double currentCR;
    private String currentStoppingCriterion;
    private Integer currentMaxStop;
    private String currentInitializer;
    private String currentMutator;
    private String currentCrossover;
    private String currentSelector;
    private String currentTestFunction;
    private String currentRegressionMetric;
    private String currentObjectiveFunction;

    public Experiment() {
        EXPERIMENT_ID = NEXT_EXPERIMENT_ID++;
        executionsResults = new ArrayList<>();
        paramRepetitions = new Parameter("Number of repetitions", "Repetitions "
                + "to be run by this execution.");
        paramAlgorithm = new Parameter("Algorithm", "Algorithm to be "
                + "executed.");
        paramDataset = new Parameter("Dataset", "Problem data.");
        paramIPR_LB = new Parameter("Lower bounds", "Lower bounds of the IPR"
                + " of the problem variables.");
        paramIPR_UB = new Parameter("Upper bounds", "Upper bounds of the IPR"
                + " of the problem variables.");
        paramNP = new Parameter("NP", "Number of individuals of the "
                + "population.");
        paramD = new Parameter("D", "Dimensionality of the problem.");
        paramF = new Parameter("F", "Factor F in mutation operators.");
        paramCR = new Parameter("CR", "Factor CR in crossover operators.");
        paramStoppingCriterion = new Parameter("Stopping criterion", "Checks"
                + "when the population must stop evolving.");
        paramMaxStop = new Parameter("Maximum value", "Maximum"
                + " number for stopping criterion.");
        paramInitializer = new Parameter("Initializer", "Initializes the "
                + "population.");
        paramMutator = new Parameter("Mutator", "Mutates the target individual"
                + "with one or more individuals of the population.");
        paramCrossover = new Parameter("Crossover", "Crosses the target and "
                + "mutant individuals.");
        paramSelector = new Parameter("Selector", "Selects the best "
                + "individual between the trial and the targets ones.");
        paramRegressionMetric = new Parameter("Regression metric", "Evaluator of "
                + "the fitness of the individuals across the evolution.");
        paramCV = new Parameter("Cross Validation (CV)", "Specifies k "
                + "parameter for CV.");
        paramTestFunction = new Parameter("Test function", "Evaluator of the fitness"
                + "of the individuals across the evolution.");
        paramObjectiveFunction = new Parameter("ObjectiveFunction", "Function"
                + "for evaluating the fitness of the individuals across the "
                + "evolution when experimenting with datasets.");
    }

    public void run() {
        for (Integer rep : (ArrayList<Integer>) paramRepetitions.getValues()) {
            currentRepetitions = rep;
            for (String strAlg : (ArrayList<String>) paramAlgorithm.getValues()) {
                currentAlgorithm = strAlg;
                for (Integer D : (ArrayList<Integer>) paramD.getValues()) {
                    currentD = D;
                    for (Integer NP : (ArrayList<Integer>) paramNP.getValues()) {
                        currentNP = NP;
                        for (Double F : (ArrayList<Double>) paramF.getValues()) {
                            currentF = F;
                            for (Double CR : (ArrayList<Double>) paramCR.getValues()) {
                                currentCR = CR;
                                for (Integer maxStop : (ArrayList<Integer>) paramMaxStop.getValues()) {
                                    currentMaxStop = maxStop;
                                    for (String strStop : (ArrayList<String>) paramStoppingCriterion.getValues()) {
                                        currentStoppingCriterion = strStop;
                                        for (String strInit : (ArrayList<String>) paramInitializer.getValues()) {
                                            currentInitializer = strInit;
                                            for (String strMut : (ArrayList<String>) paramMutator.getValues()) {
                                                currentMutator = strMut;
                                                for (String strCross : (ArrayList<String>) paramCrossover.getValues()) {
                                                    currentCrossover = strCross;
                                                    for (String strSel : (ArrayList<String>) paramSelector.getValues()) {
                                                        currentSelector = strSel;
                                                        for (int i = 0; i < paramIPR_LB.getNumValues(); i++) {
                                                            double[] iprLB = new double[D];
                                                            double[] iprUB = new double[D];
                                                            ArrayList<Double> ipr;
                                                            for (int d = 0; d < D; d++) {
                                                                ipr = (ArrayList<Double>) paramIPR_LB.getValue(i);
                                                                iprLB[d] = ipr.get(Math.min(d, ipr.size() - 1));
                                                                ipr = (ArrayList<Double>) paramIPR_UB.getValue(i);
                                                                iprUB[d] = ipr.get(Math.min(d, ipr.size() - 1));
                                                            }
                                                            currentIPR_LB = iprLB;
                                                            currentIPR_UB = iprUB;
                                                            for (String strTF : (ArrayList<String>) paramTestFunction.getValues()) {
                                                                currentTestFunction = strTF;
                                                                runExecution();
                                                            }
                                                            for (String strDat : (ArrayList<String>) paramDataset.getValues()) {
                                                                currentDataset = strDat;
                                                                for (String strOF : (ArrayList<String>) paramObjectiveFunction.getValues()) {
                                                                    currentObjectiveFunction = strOF;
                                                                    for (String strRM : (ArrayList<String>) paramRegressionMetric.getValues()) {
                                                                        currentRegressionMetric = strRM;
                                                                        for (Integer CV : (ArrayList<Integer>) paramCV.getValues()) {
                                                                            currentCV = new CrossValidation(Reader.loadDataset(currentDataset), CV);
                                                                            runExecution();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Execution createExecution() {
        Execution exe = new Execution();
        exe.setAlgorithm(AlgorithmFactory.createAlgorithm(currentAlgorithm, exe));
        exe.setD(currentD);
        exe.setNP(currentNP);
        exe.setF(currentF);
        exe.setCR(currentCR);
        exe.setMaxStop(currentMaxStop);
        exe.setStoppingCriterion(StoppingCriterionFactory.createStoppingCriterion(currentStoppingCriterion, exe));
        exe.setInitializer(InitializerFactory.createInitializer(currentInitializer, exe));
        exe.setMutator(MutatorFactory.createMutator(currentMutator, exe));
        exe.setCrossover(CrossoverFactory.createCrossover(currentCrossover, exe));
        exe.setSelector(SelectorFactory.createSelector(currentSelector, exe));
        exe.setLowerBounds(currentIPR_LB);
        exe.setUpperBounds(currentIPR_UB);
        if (currentDataset == null | currentObjectiveFunction == null
                | currentRegressionMetric == null | currentCV == null) {
            exe.setEvaluator(EvaluatorFactory.createEvaluator(currentTestFunction, exe));
        } else {
            exe.setDataset(Reader.loadDataset(currentDataset));
            exe.setObjectiveFunction(ObjectiveFunctionFactory.createObjectiveFunction(currentObjectiveFunction));
            exe.setEvaluator(EvaluatorFactory.createEvaluator(currentRegressionMetric, exe));
            exe.setCV(currentCV);
        }

        return exe;
    }

    private void runExecution() {
        ArrayList<ExecutionResults> executionResults = new ArrayList<>();
        if (currentCV != null) {
            for (int i = 0; i < currentCV.getK(); i++) {
                Execution exe = createExecution();
                exe.setEXECUTIONCONFIG_ID(EXECUTIONCONFIG_ID);
                ExecutionResults results = exe.run();
                if (results.isSuccessful()) {
                    CrossValidation CV = exe.getCV();
                    CV.setTestingState();
                    RegressionMetric regrmetric = (RegressionMetric) exe.getEvaluator();
                    regrmetric.evaluate(results.getSolution());
                    executionResults.add(results);
                    exe.getCV().updatePartitions();
                    CV.setTrainingState();
                }
            }
        } else {
            for (int i = 0; i < currentRepetitions; i++) {
                Execution exe = createExecution();
                exe.setEXECUTIONCONFIG_ID(EXECUTIONCONFIG_ID);
                ExecutionResults results = exe.run();
                if (results.isSuccessful()) {
                    executionResults.add(results);
                }
            }
        }

        EXECUTIONCONFIG_ID++;
        executionsResults.add(executionResults);
    }

    public int getEXPERIMENT_ID() {
        return EXPERIMENT_ID;
    }

    public Parameter getParamAlgorithm() {
        return paramAlgorithm;
    }

    public Parameter getParamRepetitions() {
        return paramRepetitions;
    }

    public Parameter getParamDataset() {
        return paramDataset;
    }

    public Parameter getParamCV() {
        return paramCV;
    }

    public Parameter getParamIPR_LB() {
        return paramIPR_LB;
    }

    public Parameter getParamIPR_UB() {
        return paramIPR_UB;
    }

    public Parameter getParamNP() {
        return paramNP;
    }

    public Parameter getParamD() {
        return paramD;
    }

    public Parameter getParamF() {
        return paramF;
    }

    public Parameter getParamCR() {
        return paramCR;
    }

    public Parameter getParamStoppingCriterion() {
        return paramStoppingCriterion;
    }

    public Parameter getParamMaxStop() {
        return paramMaxStop;
    }

    public Parameter getParamInitializer() {
        return paramInitializer;
    }

    public Parameter getParamMutator() {
        return paramMutator;
    }

    public Parameter getParamCrossover() {
        return paramCrossover;
    }

    public Parameter getParamSelector() {
        return paramSelector;
    }

    public Parameter getParamRegressionMetric() {
        return paramRegressionMetric;
    }

    public Parameter getParamTestFunction() {
        return paramTestFunction;
    }

    public ArrayList<ArrayList<ExecutionResults>> getExecutionsResults() {
        return executionsResults;
    }

    public void setParamAlgorithmValues(ArrayList<String> algorithms) {
        this.paramAlgorithm.setValues(algorithms);
    }

    public void setParamRepetitionsValues(ArrayList<Integer> repetitions) {
        this.paramRepetitions.setValues(repetitions);
    }

    public void setParamDatasetValues(ArrayList<String> datasets) {
        this.paramDataset.setValues(datasets);
    }

    public void setParamCVValues(ArrayList<Integer> k) {
        this.paramCV.setValues(k);
    }

    public void setParamIPR_LBValues(ArrayList<ArrayList<Double>> lowerBounds) {
        this.paramIPR_LB.setValues(lowerBounds);
    }

    public void setParamIPR_UBValues(ArrayList<ArrayList<Double>> upperBounds) {
        this.paramIPR_UB.setValues(upperBounds);
    }

    public void setParamNPValues(ArrayList<Integer> NP) {
        this.paramNP.setValues(NP);
    }

    public void setParamDValues(ArrayList<Integer> D) {
        this.paramD.setValues(D);
    }

    public void setParamFValues(ArrayList<Double> F) {
        this.paramF.setValues(F);
    }

    public void setParamCRValues(ArrayList<Double> CR) {
        this.paramCR.setValues(CR);
    }

    public void setParamStoppingCriterionValues(ArrayList<String> stops) {
        this.paramStoppingCriterion.setValues(stops);
    }

    public void setParamMaxStopValues(ArrayList<Integer> maxStop) {
        this.paramMaxStop.setValues(maxStop);
    }

    public void setParamInitializerValues(ArrayList<String> initializers) {
        this.paramInitializer.setValues(initializers);
    }

    public void setParamMutatorValues(ArrayList<String> mutators) {
        this.paramMutator.setValues(mutators);
    }

    public void setParamCrossoverValues(ArrayList<String> crossovers) {
        this.paramCrossover.setValues(crossovers);
    }

    public void setParamSelectorValues(ArrayList<String> selectors) {
        this.paramSelector.setValues(selectors);
    }

    public void setParamRegressionMetricValues(ArrayList<String> evaluators) {
        this.paramRegressionMetric.setValues(evaluators);
    }

    public void setParamTestFunctionValues(ArrayList<String> evaluators) {
        this.paramTestFunction.setValues(evaluators);
    }

    public void setParamObjFunctionValues(ArrayList<String> evaluators) {
        this.paramObjectiveFunction.setValues(evaluators);
    }

    @Override
    public String toString() {
        String str = "=========================================================";
        str += "\nEXPERIMENT::::::::" + EXPERIMENT_ID;
        str += "\n=========================================================";
        str += "\nAlgorithms: " + paramAlgorithm.getValues();
        str += "\nDatasets:\n" + paramDataset.getValues();
        str += "\nObjective Function:" + paramObjectiveFunction.getValues();
        str += "\nCV: " + paramCV.getValues();
        str += "\nIPR_LB: " + paramIPR_LB.getValues();
        str += "\nIPR_UB: " + paramIPR_UB.getValues();
        str += "\nNP: " + paramNP.getValues();
        str += "\nD: " + paramD.getValues();
        str += "\nF: " + paramF.getValues();
        str += "\nCR: " + paramCR.getValues();
        str += "\nStoppingCriterion: " + paramStoppingCriterion.getValues();
        str += "\nMaxStop: " + paramMaxStop.getValues();
        str += "\nInitializer: " + paramInitializer.getValues();
        str += "\nMutator: " + paramMutator.getValues();
        str += "\nCrossover: " + paramCrossover.getValues();
        str += "\nSelector: " + paramSelector.getValues();
        str += "\nEvaluator-RegressionMetric: " + paramRegressionMetric.getValues();
        str += "\nEvaluator-TestFunction: " + paramTestFunction.getValues();
        str += "\nNumber of successful executions: " + executionsResults.size();
        return str;
    }

}
