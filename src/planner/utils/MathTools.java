package planner.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * MathTools provides some useful and frequently used mathematical tools needed
 * when developing Differential Evolution algorithms throught this framework.
 *
 * @author Alicia Vazquez Ramos.
 * @version %I%, %G%.
 * @since 1.0
 */
public class MathTools {

    /**
     * Random object of the DEFramework.
     */
    public static Random RND = new Random();

    /**
     * Updates random object seed.
     *
     * @param seed of the execution of the DEFramework.
     */
    public static void setRandomSeed(long seed) {
        RND.setSeed(seed);
    }

    /**
     * Computes the maximum given a set of real values.
     *
     * @param s set of real values.
     * @return maximum value.
     */
    public static double max(double... s) {
        double max = -Double.MAX_VALUE;

        for (int i = 0; i < s.length; i++) {
            if (s[i] > max) {
                max = s[i];
            }
        }

        return max;
    }

    /**
     * Computes the maximum given a set of real values.
     *
     * @param s set of real values.
     * @return minimum value.
     */
    public static double min(double... s) {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < s.length; i++) {
            if (s[i] < min) {
                min = s[i];
            }
        }

        return min;
    }

    /**
     * Computes the arithmetic mean for an array of real values.
     *
     * @param values real values.
     * @return the arithmetic mean.
     */
    public static double arithmeticMean(double[] values) {
        double mean;
        double sum = 0.0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        mean = sum / values.length;

        return mean;
    }

    /**
     * Computes the arithmetic mean for a set of real values.
     *
     * @param values real values.
     * @return the arithmetic mean.
     */
    public static double arithmeticMean(ArrayList<Double> values) {
        double mean;
        double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }
        mean = sum / values.size();

        return mean;
    }

    /**
     * Computes the Lehmer mean for sn array of real values.
     *
     * @param values real values.
     * @return the Lehmer mean.
     */
    public static double lehmerMean(double[] values) {
        double mean;
        double sum2 = 0.0;
        double sum = 0.00000001;

        for (int i = 0; i < values.length; i++) {
            sum2 += Math.pow(values[i], 2);
            sum += values[i];
        }
        mean = sum2 / sum;

        return mean;
    }

    /**
     * Computes the Lehmer mean for a set of real values.
     *
     * @param values real values.
     * @return the Lehmer mean.
     */
    public static double lehmerMean(ArrayList<Double> values) {
        double mean;
        double sum2 = 0.0;
        double sum = 0.00000001;

        for (int i = 0; i < values.size(); i++) {
            sum2 += Math.pow(values.get(i), 2);
            sum += values.get(i);
        }
        mean = sum2 / sum;
        return mean;
    }

    /**
     * Generates an array of N non-negative integer pseudorandom values in an
     * interval [0, L) in Uniform distribution distinct among them and from a
     * given integer value i.
     *
     * @param i given integer to be distinct from the ones generated.
     * @param N number of pseudorandom integer to be generated.
     * @param L maximum possible integer, not included.
     * @return an array of pdeudoranfom integers distinct among them and from i.
     */
    public static int[] uniformIntegerDistinctRND(int i, int N, int L) {
        int[] rndAuxValues = RND.ints(0, L).distinct().limit(N + 1).toArray();
        int[] rndValues = new int[N];
        int k = 0;
        int r = 0;
        while (k < N && r <= N) {
            if (i != rndAuxValues[r]) {
                rndValues[k++] = rndAuxValues[r];
            }
            r++;
        }
        return rndValues;
    }

    public static int[] uniformDistinctRND(int N, int L) {
        return RND.ints(0, L).distinct().limit(N).toArray();
    }

    /**
     * Computes a pseudorandom real number in Normal distribution given the mean
     * and statistic deviation parameters.
     *
     * @param mean mean of the Normal distribution.
     * @param stdev statistic deviation of the Normal distribution.
     * @return pseudorandom real number in Normal distribution.
     */
    public static double normalRND(double mean, double stdev) {
        double rnd = RND.nextGaussian() * stdev + mean;
        return rnd;
    }

    /**
     * Computes a pseudorandom real number in Cauchy distribution given the
     * location and scale parameters.
     *
     * @param location location where Cauchy distribution is centered;
     * @param scale scale paremeter.
     * @return pseudorandom real number in Cauchy distribution.
     */
    public static double cauchyRND(double location, double scale) {
        double rnd = 1.0 / Math.PI * (scale / Math.pow(RND.nextDouble() - location, 2) + Math.pow(scale, 2));
        return rnd;
    }
}
