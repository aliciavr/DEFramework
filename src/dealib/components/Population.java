package dealib.components;

import exceptions.IncompatibleConfigurationException;
import java.util.ArrayList;
import java.util.Collections;
import planner.configuration.Execution;
import planner.utils.MathTools;
import planner.utils.evaluators.Evaluator;

/**
 * Population is a class representing the structure and functionalities of a
 * Population in differential evolution.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Population {

    /**
     * Configuration of the current execution.
     */
    private final Execution exe;
    /**
     * Represents the Population as an unordered list of individuals.
     */
    private ArrayList<Individual> population;

    /**
     * Represents the current generation of the evolution of the Population.
     */
    private int currentG;

    /**
     * Represents the state of the Population: if TRUE, the entire Population
     * will behave as having subpopulations, if FALSE, the entire Population
     * will behave as an unified block of Individuals.
     */
    private boolean activeSubPopulations;

    /**
     * When behaving as having subpopulations, it indicates the lower bound
     * index of the active subpopulation.
     */
    private int subPopLB;

    /**
     * When behaving as having subpopulations, it indicates the upper bound
     * index of the active subpopulation.
     */
    private int subPopUB;

    /**
     * Default constructor.
     *
     * @param exe configuration of the current execution.
     */
    public Population(Execution exe) {
        this.exe = exe;
        population = new ArrayList<>();
        currentG = 1;
        subPopLB = 0;
        subPopUB = 0;
        activeSubPopulations = false;
    }

    /**
     * Constructor given a set of individuals.
     *
     * @param exe configuration of the current execution.
     * @param p population.
     */
    public Population(Execution exe, ArrayList<Individual> p) {
        this.exe = exe;
        population = p;
        currentG = 1;
        subPopLB = 0;
        subPopUB = 0;
        activeSubPopulations = false;
    }

    /**
     * Gets a list with all the individuals of the population.
     *
     * @return a list with all the individuals.
     */
    public ArrayList<Individual> getAllIndividuals() {
        return population;
    }

    /**
     * Gets a list with all the individuals of the population ordered by
     * fitness.
     *
     * @return a list with all the individuals ordered by fitness.
     */
    public ArrayList<Individual> getAllOrderedIndividuals() {
        ArrayList<Individual> orderedPopulation = new ArrayList<>(population);
        Collections.sort(orderedPopulation);
        return orderedPopulation;
    }

    /**
     * Gets current generation in the evolution of the population.
     *
     * @return the current generation.
     */
    public int getCurrentG() {
        return currentG;
    }

    /**
     * Gets the next generation in the evolution of the population.
     *
     * @return the next generation.
     */
    public int getNextG() {
        return currentG + 1;
    }

    /**
     * Gets the number of individuals of the Population.
     *
     * @return current size of the Population.
     */
    public int getNP() {
        return population.size();
    }

    /**
     * Gets an Individual of the unordered population given an index.
     *
     * @param index the position of the required Individual in the unordered
     * population.
     * @return the Individual stored in the given position.
     */
    public Individual getIndividual(int index) {
        return population.get(index);
    }

    /**
     * Gets the index of a given Individual in the unordered population.
     *
     * @param ind Individual searched.
     * @return the index of the Individual in the unordered population, -1 if
     * not found.
     */
    public int getIndexOfIndividual(Individual ind) {
        return population.indexOf(ind);
    }

    /**
     * Gets the index of a given Individual in the ordered population.
     *
     * @param ind Individual searched.
     * @return the index of the Individual in the ordered population, -1 if not
     * found.
     */
    public int getIndexOfOrderedIndividual(Individual ind) {
        ArrayList<Individual> orderedPopulation = new ArrayList<>(population);
        Collections.sort(orderedPopulation);
        return orderedPopulation.indexOf(ind);
    }

    /**
     * Gets the best k individual in the ordered population by fitness.
     *
     * @param k the kth individual.
     * @return the Individual stored in the given position in the ordered
     * population.
     */
    public Individual getKBestIndividual(int k) {
        ArrayList<Individual> orderedPopulation = new ArrayList<>(population);
        Collections.sort(orderedPopulation);
        return orderedPopulation.get(k);
    }

    /**
     * Gets the best individual of the population.
     *
     * @return the best individual of the population.
     */
    public Individual getBestIndividual() {
        int iBest = 0;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getFitness() < population.get(iBest).getFitness()) {
                iBest = i;
            }
        }
        return population.get(iBest);
    }

    /**
     * Gets the lower bound of the active subpopulation if mechanism for
     * subpopulations is active.
     *
     * @return lower bound of active subpopulation.
     */
    public int getSubPopLB() {
        return subPopLB;
    }

    /**
     * Gets the upper bound of the active subpopulation if mechanism for
     * subpopulations is active.
     *
     * @return upper bound of active subpopulation.
     */
    public int getSubPopUB() {
        return subPopUB;
    }

    /**
     * Gets N random individuals from the Population which are distinct among
     * them.
     *
     * @param numIndividuals number of individuals to be generated.
     * @return an array of individuals.
     */
    public Individual[] getNRandDistinctIndividuals(int numIndividuals) {
        Individual[] randomDistinctIndividuals = new Individual[numIndividuals];

        int maxInteger = population.size();
        int minInteger = 0;
        if (activeSubPopulations) {
            maxInteger = subPopUB - subPopLB;
            minInteger = subPopLB;
        }

        int[] rndIndices = MathTools.uniformIntegerDistinctRND(-1, numIndividuals, maxInteger);

        for (int i = 0; i < numIndividuals; i++) {
            randomDistinctIndividuals[i] = population.get(minInteger + rndIndices[i]);
        }

        return randomDistinctIndividuals;
    }

    /**
     * Gets N random individuals from the Population which are distinct among
     * them and disintct from the given the index of an Individual ind. If the
     * subpopulations mechanism is active it searchs from the active
     * subpopulation.
     *
     * @param ind index of the Individual to be distinct.
     * @param numIndividuals number of individuals to be generated.
     * @return an array of individuals.
     */
    public Individual[] getNRandDistinctIndividuals(int ind, int numIndividuals) {
        Individual[] randomDistinctIndividuals = new Individual[numIndividuals];

        int maxInteger = population.size();
        int minInteger = 0;
        if (activeSubPopulations) {
            maxInteger = subPopUB - subPopLB;
            minInteger = subPopLB;
        }

        int[] rndIndices = MathTools.uniformIntegerDistinctRND(ind, numIndividuals, maxInteger);

        for (int i = 0; i < numIndividuals; i++) {
            randomDistinctIndividuals[i] = population.get(minInteger + rndIndices[i]);
        }

        return randomDistinctIndividuals;
    }

    /**
     * Gets N random individuals from the Population which are distinct among
     * them and disintct from the given Individual ind. If the subpopulations
     * mechanism is active it searchs from the active subpopulation.
     *
     * @param ind Individual to be distinct.
     * @param numIndividuals number of individuals to be generated.
     * @return an array of individuals.
     */
    public Individual[] getNRandDistinctIndividuals(Individual ind, int numIndividuals) {
        Individual[] randomDistinctIndividuals = new Individual[numIndividuals];

        int maxInteger = population.size();
        int minInteger = 0;
        if (activeSubPopulations) {
            maxInteger = subPopUB - subPopLB;
            minInteger = subPopLB;
        }

        int[] rndIndices = MathTools.uniformIntegerDistinctRND(getIndexOfIndividual(ind), numIndividuals, maxInteger);

        for (int i = 0; i < numIndividuals; i++) {
            randomDistinctIndividuals[i] = population.get(minInteger + rndIndices[i]);
        }

        return randomDistinctIndividuals;
    }

    /**
     * Checks if the mechanism for subpopulations is active or not.
     *
     * @return TRUE if it is active, FALSE otherwise.
     */
    public boolean isActiveSubPopulations() {
        return activeSubPopulations;
    }

    /**
     * Sets a new value for the mechanism of subpopulations: it can activated
     * (TRUE) or deactivated (FALSE).
     *
     * @param b the new state for subpopulation mechanism.
     */
    public void setActiveSubPopulations(boolean b) {
        activeSubPopulations = b;
    }

    /**
     * Lower bound of the active subpopulation if subpopulations mechanism is
     * active.
     *
     * @param lb the lower bound of the active subpopulation.
     */
    public void setSubPopLB(int lb) {
        subPopLB = lb;
    }

    /**
     * Upper bound of the active subpopulation if subpopulations mechanism is
     * active.
     *
     * @param ub the upper bound of the active subpopulation.
     */
    public void setSubPopUB(int ub) {
        subPopUB = ub;
    }

    /**
     * Sets a new Population and increments the generation value in its
     * evolution.
     *
     * @param p new set of individuals of the next population.
     */
    public void setNextPopulation(ArrayList<Individual> p) {
        population = p;
        incrPopulationG();
    }

    /**
     * Increments the current generation in the evolution of the population.
     */
    public void incrPopulationG() {
        currentG++;
        exe.setFitness(getBestIndividual().getFitness());
        exe.getResults().addF(exe.getF());
        exe.getResults().addCR(exe.getCR());
        exe.getResults().addNP(exe.getNP());

        exe.incrGenerations();
    }

    /**
     * Adds a new Individual to the Population.
     *
     * @param ind new Individual.
     */
    public void addIndividual(Individual ind) {
        population.add(ind);
    }

    /**
     * Replaces an Individual at position i by the given Individual ind.
     *
     * @param i position where set the new individual ind
     * @param ind new individual for position i.
     */
    public void replaceIndividual(int i, Individual ind) {
        population.set(i, ind);
    }

    /**
     * Evaluates all the individuals of the Population given an Evaluator.
     *
     * @param evaluator the way each individual is going to be evaluated.
     * @throws exceptions.IncompatibleConfigurationException when the dimension
     * of the problem does not fit with the dimensionality of the function used
     * as evaluator.
     */
    public void evaluatePopulation(Evaluator evaluator) throws IncompatibleConfigurationException {
        Individual ind;
        for (int i = 0; i < population.size(); i++) {
            ind = population.get(i);
            ind.updateFitness();
        }
    }

    /**
     * Reduces the Population given a reduction factor.
     *
     * @param factor reduction factor.
     */
    public void reducePopulation(double factor) {
        if (factor < 1.0) {
            ArrayList<Individual> p = new ArrayList<>();
            int size = (int) Math.round(population.size() * factor);
            while (p.size() < size) {
                p.add(population.get(p.size()));
            }
            population = p;
        }
        exe.setNP(population.size());
    }

    /**
     * Gets a string representing the visualization by console of the
     * Population.
     *
     * @return string representing the current state of the Population.
     */
    @Override
    public String toString() {
        String str = "Population[";
        str += "\nNP = " + this.population.size();
        str += "\nG = " + this.currentG;
        if (this.activeSubPopulations) {
            str += "\nActive subpopulation: [" + this.subPopLB + ", " + this.subPopUB + ")";
        }
        str = this.population.stream().map(ind -> ind.toString() + "\n").reduce(str, String::concat);
        return str;
    }

}
