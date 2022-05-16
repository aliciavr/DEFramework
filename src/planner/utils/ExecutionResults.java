package planner.utils;

import java.util.ArrayList;

public class ExecutionResults {

    private final String EXECUTION_ID;
    private String algorithm;
    private String stoppingCriterion;
    private int maxStop;
    private String initializer;
    private String mutator;
    private String crossover;
    private String selector;
    private String evaluator;
    private String objFunction;
    private String dataset;
    private int D;
    private int CV;
    private final ArrayList<double[]> NPValues;
    private final ArrayList<double[]> FValues;
    private final ArrayList<double[]> CRValues;
    private final ArrayList<double[]> fitnessValues;
    private int numGenerations;
    private int numFEs;
    private double time;
    private double[] solution;
    private double solutionFitness;
    private boolean successful;

    public ExecutionResults(String EXECUTION_ID) {
        this.EXECUTION_ID = EXECUTION_ID;
        NPValues = new ArrayList<>();
        FValues = new ArrayList<>();
        CRValues = new ArrayList<>();
        fitnessValues = new ArrayList<>();
        numGenerations = 0;
        numFEs = 0;
        time = 0;
        successful = true;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setStoppingCriterion(String stoppingCriterion) {
        this.stoppingCriterion = stoppingCriterion;
    }

    public void setMaxStop(int maxStop) {
        this.maxStop = maxStop;
    }

    public void setInitializer(String initializer) {
        this.initializer = initializer;
    }

    public void setMutator(String mutator) {
        this.mutator = mutator;
    }

    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public void setObjFunction(String objFunction) {
        this.objFunction = objFunction;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public void setD(int D) {
        this.D = D;
    }

    public void setCV(int CV) {
        this.CV = CV;
    }

    public void incrNumGenerations() {
        this.numGenerations++;
    }

    public void incrNumGenerations(int numGenerations) {
        this.numGenerations += numGenerations;
    }

    public void incrNumFEs() {
        this.numFEs++;
    }

    public void incrNumFEs(int numFEs) {
        this.numFEs += numFEs;
    }

    public void addNP(int NP) {
        if (NPValues.isEmpty() || NPValues.get(NPValues.size() - 1)[0] != NP) {
            double[] obs = new double[]{NP, System.currentTimeMillis(), numGenerations, numFEs};
            NPValues.add(obs);
        }
    }

    public void addF(double F) {
        if (FValues.isEmpty() || FValues.get(FValues.size() - 1)[0] != F) {
            double[] obs = new double[]{F, System.currentTimeMillis(), numGenerations, numFEs};
            FValues.add(obs);
        }
    }

    public void addCR(double CR) {
        if (CRValues.isEmpty() || CRValues.get(CRValues.size() - 1)[0] != CR) {
            double[] obs = new double[]{CR, System.currentTimeMillis(), numGenerations, numFEs};
            CRValues.add(obs);
        }
    }

    public void addFitness(double fitness) {
        if (fitnessValues.isEmpty() || fitnessValues.get(fitnessValues.size() - 1)[0] != fitness) {
            double[] obs = new double[]{fitness, System.currentTimeMillis(), numGenerations, numFEs};
            fitnessValues.add(obs);
        }
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setSolution(double[] solution) {
        this.solution = solution;
    }

    public void setSolutionFitness(double solutionFitness) {
        this.solutionFitness = solutionFitness;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getEXECUTION_ID() {
        return EXECUTION_ID;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getStoppingCriterion() {
        return stoppingCriterion;
    }

    public int getMaxStop() {
        return maxStop;
    }

    public String getInitializer() {
        return initializer;
    }

    public String getMutator() {
        return mutator;
    }

    public String getCrossover() {
        return crossover;
    }

    public String getSelector() {
        return selector;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public String getObjFunction() {
        return objFunction;
    }

    public String getDataset() {
        return dataset;
    }

    public int getD() {
        return D;
    }

    public int getCV() {
        return CV;
    }

    public ArrayList<double[]> getNPValues() {
        return NPValues;
    }

    public ArrayList<double[]> getFValues() {
        return FValues;
    }

    public ArrayList<double[]> getCRValues() {
        return CRValues;
    }

    public ArrayList<double[]> getFitnessValues() {
        return fitnessValues;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public int getNumFEs() {
        return numFEs;
    }

    public double getTime() {
        return time;
    }

    public double[] getSolution() {
        return solution;
    }

    public double getSolutionFitness() {
        return solutionFitness;
    }

    @Override
    public String toString() {
        String data = "EXECUTION_ID;ALGORITHM;D;STOPCRITERON;STOP;INITIALIZER;MUTATOR;CROSSOVER;SELECTOR;EVALUATOR;OBJFUNCTION;DATASET;CV";
        data += "\n" + getEXECUTION_ID() + ";"
                + getAlgorithm() + ";"
                + getD() + ";"
                + getStoppingCriterion() + ";"
                + getMaxStop() + ";"
                + getInitializer() + ";"
                + getMutator() + ";"
                + getCrossover() + ";"
                + getSelector() + ";"
                + getEvaluator() + ";"
                + getObjFunction() + ";"
                + getDataset() + ";"
                + getCV();
        data += "\n";
        data += "\n" + "FITNESS;SOLUTION;NUM_FES;NUM_GENERATIONS;TIME(ms)";
        data += "\n" + getSolutionFitness() + ";"
                + PrintingTools.toString(getSolution()) + ";"
                + getNumFEs() + ";"
                + getNumGenerations() + ";"
                + getTime();
        data += "\n";
        data += "\n" + "NP;AT_TIME(ms);AT_GENERATION;AT_FE";

        data = "\n" + getNPValues().stream().map(np -> "\n" + (int) np[0] + ";" + np[1] + ";" + (int) np[2] + ";" + (int) np[3]).reduce(data, String::concat);
        data += "\n";
        data += "\n" + "F;AT_TIME(ms);AT_GENERATION;AT_FE";
        data = "\n" + getFValues().stream().map(f -> "\n" + f[0] + ";" + f[1] + ";" + (int) f[2] + ";" + (int) f[3]).reduce(data, String::concat);
        data += "\n";
        data += "\n" + "CR;AT_TIME(ms);AT_GENERATION;AT_FE";
        data = "\n" + getCRValues().stream().map(cr -> "\n" + cr[0] + ";" + cr[1] + ";" + (int) cr[2] + ";" + (int) cr[3]).reduce(data, String::concat);
        data += "\n";
        data += "\n" + "FITNESS;AT_TIME(ms);AT_GENERATION;AT_FE";
        data = "\n" + getFitnessValues().stream().map(fitness -> "\n" + fitness[0] + ";" + fitness[1] + ";" + (int) fitness[2] + ";" + (int) fitness[3]).reduce(data, String::concat);
        data += "\n";
        return data;
    }

}
