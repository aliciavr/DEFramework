package dealib.components;

import exceptions.IncompatibleConfigurationException;
import planner.configuration.Execution;
import planner.utils.PrintingTools;

/**
 * Individual is a class representing the structure and functionalities of an
 * individual in differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Individual implements Comparable {

    /**
     * Configuration of the current execution.
     */
    private final Execution exe;

    /**
     * Represents the generation where the Individual was created.
     */
    private int G;

    /**
     * Represents the solution coded in the Individual.
     */
    private double[] genotype;

    /**
     * Represents the fitness of the Individual.
     */
    private double fitness;

    /**
     * Constructor of an Individual, given the configuration of the current
     * execution.
     *
     * @param exe configuration of the current execution.
     */
    public Individual(Execution exe) {
        this.exe = exe;
        this.G = 0;
        this.genotype = new double[exe.getD()];
        this.fitness = Double.MAX_VALUE;
    }

    /**
     * Constructor of an Individual, given the configuration of the current
     * execution and the generation, G, when it was created.
     *
     * @param exe configuration of the current execution.
     * @param G generation when the Individual was created.
     */
    public Individual(Execution exe, int G) {
        this.exe = exe;
        this.G = G;
        this.genotype = new double[exe.getD()];
        this.fitness = Double.MAX_VALUE;
    }

    /**
     * Constructor of an Individual, given the configuration of the current
     * execution, the generation, G, when it was created and the genotype to be
     * stored by the Individual.
     *
     * @param exp configuration of the current execution.
     * @param G generation when the Individual was created.
     * @param genotype
     */
    public Individual(Execution exp, int G, double[] genotype) {
        this.exe = exp;
        this.G = G;
        this.genotype = new double[exp.getD()];
        System.arraycopy(genotype, 0, this.genotype, 0, exp.getD());
        this.fitness = Double.MAX_VALUE;
    }

    /**
     * Gets the generation when the Individual was created.
     *
     * @return the generation when the Individual was created.
     */
    public int getG() {
        return this.G;
    }

    /**
     * Gets a reference to the Individual genotype.
     *
     * @return the Individual genotype.
     */
    public double[] getGenotype() {
        return this.genotype;
    }

    /**
     * Gets the fitness of the Individual.
     *
     * @return last update of the fitness of the Individual.
     */
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Gets the value of a given index gene, i, in the Individual genotype.
     *
     * @param i index of the gene.
     * @return the current value of the gene in position i.
     */
    public double getGene(int i) {
        return this.genotype[i];
    }

    /**
     * Sets the generation when the Individual was created.
     *
     * @param G the generation when the Individual was created.
     */
    public void setG(int G) {
        this.G = G;
    }

    /**
     * Sets a new genotype for the Individual.
     *
     * @param genotype new genotype reference for the Individual.
     */
    public void setGenotype(double[] genotype) {
        this.genotype = genotype;
    }

    /**
     * Sets a new fitness value for the Individual.
     *
     * @param fitness new fitness value.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
        exe.setFitness(fitness);
    }

    /**
     * Sets a new value for a given index gene, i, in the Individual genotype.
     *
     * @param i index of the gene.
     * @param gene new value of the updated gene in position i.
     */
    public void setGene(int i, double gene) {
        this.genotype[i] = gene;
    }

    /**
     * Copies a given genotype to the current genotype of the Individual.
     *
     * @param genotype new genotype to be copied.
     */
    public void copyGenotype(double[] genotype) {
        System.arraycopy(genotype, 0, this.genotype, 0, genotype.length);
    }

    /**
     * Updates fitness calling the evaluator configured in the current execution
     * of an experiment.
     *
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator.
     * @return a new value for fitness.
     */
    public double updateFitness() throws IncompatibleConfigurationException {
        this.fitness = this.exe.getEvaluator().evaluate(this.genotype);
        exe.incrFEs();
        return this.fitness;
    }

    /**
     * Gets a string representing the visualization by console of the
     * Individual.
     *
     * @return string representing the current state of the Individual.
     */
    @Override
    public String toString() {
        String str = "Individual[";
        str += "\nGenoype = " + PrintingTools.toString(this.genotype) + ",";
        str += "\nFitness= " + this.fitness + ",";
        str += "\nG = " + this.G + "]";
        return str;
    }

    /**
     * Compares two Individuals: the individual with minimum fitness has
     * preference over the other individual.
     *
     * @param t other individual to be compared.
     * @return 1 if this Individual has preference over the other Individual, -1
     * if the other Individual has preference over the this Individual and 0 if
     * both are equal.
     */
    @Override
    public int compareTo(Object t) {
        int value = 0;
        if (t instanceof Individual & t instanceof Individual) {
            Individual indA = this;
            Individual indB = (Individual) t;
            if (indA.getFitness() < indB.getFitness()) {
                value = -1;
            } else if (indA.getFitness() > indB.getFitness()) {
                value = 1;
            }
        }
        return value;
    }

}
