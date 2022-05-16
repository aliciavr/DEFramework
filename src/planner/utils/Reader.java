package planner.utils;

import exceptions.ExperimentFormatException;
import exceptions.IncompatibleConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import planner.configuration.Experiment;
import static planner.utils.MathTools.RND;

/**
 * Reader is a class implementing all input operations of the framework when
 * working with experiment files: configures a list of experiment given the
 * configuration defined in the framework experiments files.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @version 1.0
 * @since 1.0
 */
public class Reader {

    private static final String ALGORITHM_KEY = "Algorithm";
    private static final String REP_KEY = "Repetitions";
    private static final String DATASET_KEY = "Dataset";
    private static final String CV_KEY = "CV";
    private static final String D_KEY = "D";
    private static final String NP_KEY = "NP";
    private static final String IPR_KEY = "IPR";
    private static final String CR_KEY = "CR";
    private static final String F_KEY = "F";
    private static final String STOP_KEY = "StoppingCriterion";
    private static final String MAXSTOP_KEY = "MaxStop";
    private static final String INIT_KEY = "Initializer";
    private static final String MUT_KEY = "Mutator";
    private static final String CROSS_KEY = "Crossover";
    private static final String SEL_KEY = "Selector";
    private static final String EVAL_KEY = "Evaluator";
    private static final String REGMETRIC_KEY = "RegressionMetric";
    private static final String OBJFUNC_KEY = "ObjectiveFunction";
    private static final String TSTFUNC_KEY = "TestFunction";

    /**
     * Path of the file with core data about the framework configuration.
     */
    private static String frameworkDataPath;

    /**
     * Path of the file with core data about the experiments configuration.
     */
    private static String experimentsPath;

    /**
     * Path of the file supporting the aliases and full qualified name of the
     * main components in the Differential Evolition Algorithms Library (dealib)
     * of this framework.
     */
    private static String componentsPath;

    /**
     * Path of the file registering default components properties.
     */
    private static String defaultComponentsPath;

    /**
     * Properties object of the framework configuration.
     */
    private static Properties configDataProperties;

    /**
     * Properties object of the experiments core configuration.
     */
    private static Properties configExperimentsProperties;

    /**
     * Properties object of the components registered in the framework.
     */
    private static Properties componentsProperties;

    /**
     * Properties object of the default components registered in the framework.
     */
    private static Properties defaultProperties;

    /**
     * Aliases of all registered components.
     */
    private static Stream<String> strComp;

    /**
     * Initializes essential properties and configuration in the framework.
     *
     * @param path path of the file containing the dataset.
     */
    public static void loadConfigurationData(String path) {
        frameworkDataPath = path;
        try {
            // Loads the framework configuration into an object Properties.
            configDataProperties = new Properties();
            configDataProperties.load(new FileInputStream(new File(frameworkDataPath)));
            MathTools.setRandomSeed(Long.parseLong(configDataProperties.getProperty("SEED")));

            // Loads the experiments core data path.
            experimentsPath = configDataProperties.getProperty("EXPERIMENTS_PATH");
            // Loads the aliases of the dealib components data path.
            componentsPath = configDataProperties.getProperty("COMPONENTS_PATH");
            // Loads the default components properties data path.
            defaultComponentsPath = configDataProperties.getProperty("DEFAULT_COMPONENTS_PATH");

            // Experiments configuration data loading.
            configExperimentsProperties = new Properties();
            configExperimentsProperties.load(new FileInputStream(new File(experimentsPath)));

            // Aliases for library components.
            componentsProperties = new Properties();
            componentsProperties.load(new FileInputStream(new File(componentsPath)));
            strComp = componentsProperties.stringPropertyNames().stream();

            // Aliases of default components.
            defaultProperties = new Properties();
            defaultProperties.load(new FileInputStream(new File(defaultComponentsPath)));
        } catch (IOException e) {
            System.err.println("[Reader::loadConfigurationData]:" + e.getMessage());
        }
    }

    public static String getExperimentPath(int i) {
        return configExperimentsProperties.getProperty("EXPERIMENT" + (i + 1) + "_PATH");
    }

    private static boolean hasALL(String input) {
        return input.toUpperCase().trim().contains("ALL");
    }

    private static boolean hasRND(String input) {
        return input.toUpperCase().trim().contains("RND");
    }

    private static ArrayList<String> readStrings(String input) {
        StringTokenizer tokens = new StringTokenizer(input, ",");
        String str;
        ArrayList<String> elements = new ArrayList<>();
        while (tokens.hasMoreTokens()) {
            str = tokens.nextToken();
            elements.add(str.trim());
        }

        return elements;
    }

    private static ArrayList<Integer> readIntegers(String input) throws ExperimentFormatException {
        ArrayList<String> elements = readStrings(input);
        ArrayList<Integer> integers = new ArrayList<>();
        String[] intervalSplit;
        String[] stepSplit;
        int lb;
        int ub;
        int step = 1;
        for (String e : elements) {
            if (e.contains("-")) {
                intervalSplit = e.split("-");
                if (intervalSplit.length != 2) {
                    throw new ExperimentFormatException("Incorrect interval"
                            + " configuration. Character \'-\' is binary, it"
                            + " must have a lower bound and an upper bound");
                } else {
                    lb = Integer.parseInt(intervalSplit[0]);
                    if (e.contains(":")) {
                        stepSplit = intervalSplit[1].split(":");
                        if (stepSplit.length != 2) {
                            throw new ExperimentFormatException("Incorrect step"
                                    + " configuration. Character \':\' is unary"
                                    + ", it must have a step value.");
                        } else {
                            ub = Integer.parseInt(stepSplit[0]);
                            step = Integer.parseInt(stepSplit[1]);
                        }
                    } else {
                        ub = Integer.parseInt(intervalSplit[1]);
                    }
                }

                for (int i = lb; i <= ub; i += step) {
                    integers.add(i);
                }

            } else {
                integers.add(Integer.parseInt(e));
            }
        }

        return integers;
    }

    private static ArrayList<Double> readReals(String input) throws ExperimentFormatException {
        ArrayList<String> elements = readStrings(input);
        ArrayList<Double> reals = new ArrayList<>();
        String[] intervalSplit;
        String[] stepSplit;
        double lb;
        double ub;
        double step = 1.0;
        for (String e : elements) {
            if (e.contains("-")) {
                intervalSplit = e.split("-");
                if (intervalSplit.length != 2) {
                    throw new ExperimentFormatException("Incorrect interval"
                            + " configuration. Character \'-\' is binary, it"
                            + " must have a lower bound and an upper bound");
                } else {
                    lb = Double.parseDouble(intervalSplit[0]);
                    if (e.contains(":")) {
                        stepSplit = intervalSplit[1].split(":");
                        if (stepSplit.length != 2) {
                            throw new ExperimentFormatException("Incorrect step"
                                    + " configuration. Character \':\' is unary"
                                    + ", it must have a step value.");
                        } else {
                            ub = Double.parseDouble(stepSplit[0]);
                            step = Double.parseDouble(stepSplit[1]);
                        }
                    } else {
                        ub = Double.parseDouble(intervalSplit[1]);
                    }
                }

                for (double i = lb; i <= ub; i += step) {
                    reals.add(i);
                }

            } else {
                reals.add(Double.parseDouble(e));
            }
        }

        return reals;

    }

    private static ArrayList<ArrayList<Double>>[] readIPRIntervals(Properties properties) throws ExperimentFormatException {
        String input = properties.getProperty(IPR_KEY);
        ArrayList<ArrayList<Double>>[] intervals = new ArrayList[2];
        int LB = 0;
        int UB = 1;

        // Configured IPRs.
        intervals[LB] = new ArrayList<>();
        intervals[UB] = new ArrayList<>();
        // A single IPR, storing real values for lower bound and upper bound.
        ArrayList<Double> IPR_LB;
        ArrayList<Double> IPR_UB;
        // Auxiliar variables for formatting.
        String[] varsSplit;
        String[] boundsSplit;
        if (!properties.containsKey(IPR_KEY)) {
            throw new ExperimentFormatException(IPR_KEY + " not "
                    + "defined or incorrect written.");
        } else {
            // Configuration elements.
            ArrayList<String> elements = readStrings(input);
            if (input.trim().isEmpty()) {
                IPR_LB = new ArrayList<>();
                IPR_UB = new ArrayList<>();
                input = defaultProperties.getProperty(IPR_KEY);
                varsSplit = input.split("\\|");
                for (String var : varsSplit) {
                    boundsSplit = var.split(";");
                    if (boundsSplit.length != 2) {
                        throw new ExperimentFormatException("Incorrect variable"
                                + "IPR specification.");
                    } else {
                        IPR_LB.add(Double.parseDouble(boundsSplit[LB]));
                        IPR_UB.add(Double.parseDouble(boundsSplit[UB]));
                    }

                }
                intervals[LB].add(IPR_LB);
                intervals[UB].add(IPR_UB);
                System.out.println(IPR_KEY + " properties not found. "
                        + "Default value for " + IPR_KEY + " loaded: "
                        + defaultProperties.get(IPR_KEY));
            } else {

                for (String e : elements) {
                    IPR_LB = new ArrayList<>();
                    IPR_UB = new ArrayList<>();
                    varsSplit = e.split("\\|");
                    for (String var : varsSplit) {
                        boundsSplit = var.split(";");
                        if (boundsSplit.length != 2) {
                            throw new ExperimentFormatException("Incorrect variable"
                                    + "IPR specification.");
                        } else {
                            IPR_LB.add(Double.parseDouble(boundsSplit[LB]));
                            IPR_UB.add(Double.parseDouble(boundsSplit[UB]));
                        }

                    }
                    intervals[LB].add(IPR_LB);
                    intervals[UB].add(IPR_UB);
                }
            }
        }

        return intervals;
    }

    public static Properties loadExperimentProperties(int i) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(configExperimentsProperties.getProperty("EXPERIMENT" + (i + 1) + "_PATH"))));
        } catch (IOException e) {
            System.err.println("[Reader.loadExperimentProperties]: error when "
                    + "loading experiments properties.");
        }
        return properties;
    }

    private static ArrayList<Integer> integerElementsCreator(Properties properties, String key) throws ExperimentFormatException {
        ArrayList<Integer> elements = new ArrayList<>();
        if (!properties.containsKey(key)) {
            throw new ExperimentFormatException(key + " not "
                    + "defined or incorrect written.");
        } else {
            String input = properties.getProperty(key);
            if (input.trim().isEmpty()) {
                elements.add(Integer.parseInt(defaultProperties.getProperty(key)));
                System.out.println(key + " properties not found. "
                        + "Default value for " + key + " loaded: "
                        + defaultProperties.getProperty(key));
            } else {
                elements = Reader.readIntegers(input);
                if (hasRND(input)) {
                    elements.add(RND.nextInt());
                }
            }
        }
        return elements;
    }

    private static ArrayList<Double> realElementsCreator(Properties properties, String key) throws ExperimentFormatException {
        ArrayList<Double> elements = new ArrayList<>();
        if (!properties.containsKey(key)) {
            throw new ExperimentFormatException(key + " not "
                    + "defined or incorrect written.");
        } else {
            String input = properties.getProperty(key);
            if (input.trim().isEmpty()) {
                elements.add(Double.parseDouble(defaultProperties.getProperty(key)));
                System.out.println(key + " properties not found. "
                        + "Default value for " + key + " loaded: "
                        + defaultProperties.getProperty(key));
            } else {
                elements = Reader.readReals(input);
                if (hasRND(input)) {
                    elements.add(RND.nextDouble());
                }
            }
        }
        return elements;
    }

    private static ArrayList<String> stringElementsCreator(Properties properties, String key, String prefix) throws ExperimentFormatException {
        strComp = componentsProperties.stringPropertyNames().stream();
        ArrayList<String> elements = new ArrayList<>();
        if (!properties.containsKey(key)) {
            throw new ExperimentFormatException(key + " not "
                    + "defined or incorrect written.");
        } else {
            String input = properties.getProperty(key);
            if (input.trim().isEmpty()) {
                elements.add(defaultProperties.getProperty(key));
                System.out.println(key + " properties not found. "
                        + "Default value for " + key + " loaded: "
                        + defaultProperties.getProperty(key));
            } else {
                final ArrayList<String> ele = Reader.readStrings(input);
                if (hasALL(input)) {
                    ele.clear();
                    strComp.filter((String s) -> s.startsWith(prefix))
                            .forEach((String s) -> ele.add(s));
                }
                elements = ele;
            }
        }
        return elements;
    }

    public static Experiment buildExperiment(Properties properties) throws ExperimentFormatException, IncompatibleConfigurationException {
        Experiment experiment = new Experiment();

        // Algorithm and algorithm's components.
        ArrayList<String> algorithms = stringElementsCreator(properties, ALGORITHM_KEY, "alg-");
        experiment.setParamAlgorithmValues(algorithms);

        ArrayList<String> stopping = stringElementsCreator(properties, STOP_KEY, "sc-");
        experiment.setParamStoppingCriterionValues(stopping);

        ArrayList<String> initializers = stringElementsCreator(properties, INIT_KEY, "init-");
        experiment.setParamInitializerValues(initializers);

        ArrayList<String> mutators = stringElementsCreator(properties, MUT_KEY, "mut-");
        experiment.setParamMutatorValues(mutators);

        ArrayList<String> crossovers = stringElementsCreator(properties, CROSS_KEY, "cross-");
        experiment.setParamCrossoverValues(crossovers);

        ArrayList<String> selectors = stringElementsCreator(properties, SEL_KEY, "sel-");
        experiment.setParamSelectorValues(selectors);

        // Numeric parameters.
        ArrayList<Integer> d = integerElementsCreator(properties, D_KEY);
        experiment.setParamDValues(d);

        ArrayList<Integer> np = integerElementsCreator(properties, NP_KEY);
        experiment.setParamNPValues(np);

        ArrayList<Double> cr = realElementsCreator(properties, CR_KEY);
        experiment.setParamCRValues(cr);

        ArrayList<Double> f = realElementsCreator(properties, F_KEY);
        experiment.setParamFValues(f);

        ArrayList<Integer> maxStop = integerElementsCreator(properties, MAXSTOP_KEY);
        experiment.setParamMaxStopValues(maxStop);

        ArrayList<Integer> repetitions = integerElementsCreator(properties, REP_KEY);
        experiment.setParamRepetitionsValues(repetitions);

        // Cross validation is optional, if not defined an independent 
        // execution is performed.
        if (properties.containsKey(CV_KEY)) {
            ArrayList<Integer> CV = integerElementsCreator(properties, CV_KEY);
            experiment.setParamCVValues(CV);
        }

        // IPR parameter.
        ArrayList<ArrayList<Double>>[] IPRs = readIPRIntervals(properties);
        experiment.setParamIPR_LBValues(IPRs[0]);
        experiment.setParamIPR_UBValues(IPRs[1]);

        // Evaluation configuration.
        if (properties.containsKey(DATASET_KEY)) {
            // Checks for the complete configuration of the dataset experimentation.

            ArrayList<String> datasets = Reader.readStrings(properties.getProperty(DATASET_KEY));
            if (datasets.isEmpty()) {
                throw new ExperimentFormatException(DATASET_KEY + " parameter is empty.");
            } else {
                experiment.setParamDatasetValues(datasets);
            }

            ArrayList<String> regmetrics = stringElementsCreator(properties, REGMETRIC_KEY, "rm-");
            experiment.setParamRegressionMetricValues(regmetrics);

            ArrayList<String> objfunctions = stringElementsCreator(properties, OBJFUNC_KEY, "of-");
            experiment.setParamObjFunctionValues(objfunctions);

        } else if (properties.contains(REGMETRIC_KEY) || properties.contains(OBJFUNC_KEY)) {
            throw new IncompatibleConfigurationException(DATASET_KEY + " "
                    + "parameter not defined.");
        }
        // If not dataset complete configuration defined, then check for 
        // test functions.
        if (!properties.containsKey(DATASET_KEY) & properties.containsKey(TSTFUNC_KEY)) {
            ArrayList<String> testFunctions = stringElementsCreator(properties, TSTFUNC_KEY, "tf-");
            experiment.setParamTestFunctionValues(testFunctions);
        }

        return experiment;
    }

    /**
     * Gets the number of experiments configured in the experiments core data.
     *
     * @return the number of experiments configured.
     */
    public static int getNumExperiments() {
        return Integer.parseInt(configExperimentsProperties.getProperty("NUM_EXPERIMENTS"));
    }

    /**
     * Loads a dataset with ARFF format.
     *
     * @param path String representing the path.
     * @return a Dataset object.
     */
    public static Dataset loadDataset(String path) {
        Stream<String> lines;
        Dataset dataset = null;
        try {
            lines = Files.lines(Paths.get(path));
            String datasetPath = path;
            String datasetName = "";
            String datasetFormat;
            String datasetDescription = "";
            String[] datasetAttributes;
            double[][] data;

            String[] strings = path.split("\\.");
            datasetFormat = strings[strings.length - 1];

            Iterator<String> it = lines.iterator();
            String s;
            boolean stopHeader = false;
            ArrayList<String> attributes = new ArrayList<>();
            while (!stopHeader & it.hasNext()) {
                s = it.next();
                s = s.trim();
                if (s.startsWith("@relation") | s.startsWith("@RELATION")) {
                    datasetName = s.substring(s.indexOf(" ")).trim();
                } else if (s.startsWith("%")) {
                    datasetDescription += s.replace("%", "").trim() + "\n";
                } else if (s.startsWith("@attribute") | s.startsWith("@ATTRIBUTE")) {
                    attributes.add(s.substring(s.indexOf(" ")).trim().split(" ")[0]);
                } else if (s.startsWith("@data") | s.startsWith("@DATA")) {
                    stopHeader = true;
                }
            }

            Object[] objectArray = attributes.toArray();
            datasetAttributes = Arrays.copyOf(objectArray, objectArray.length, String[].class);
            int numAttributes = datasetAttributes.length;

            // Starts reading data.
            String[] tempData;
            ArrayList<Double> tempReal = new ArrayList<>();
            ArrayList<ArrayList<Double>> tempTotal = new ArrayList<>();
            String temp;
            while (it.hasNext()) {
                s = it.next();
                s = s.trim();
                tempData = s.split(",");

                for (int i = 0; i < numAttributes; i++) {
                    if (i < tempData.length) {
                        temp = tempData[i];
                        try {
                            tempReal.add(Double.parseDouble(temp.trim()));
                        } catch (NumberFormatException e) {
                            tempReal.add(Double.NaN);
                        }
                    } else {
                        tempReal.add(Double.NaN);
                    }
                }
                tempTotal.add(tempReal);
                tempReal = new ArrayList<>();
            }
            int numInstances = tempTotal.size();
            data = new double[numInstances][numAttributes];

            for (int i = 0; i < numInstances; i++) {
                for (int j = 0; j < numAttributes; j++) {
                    data[i][j] = tempTotal.get(i).get(j);
                }
            }

            dataset = new Dataset(datasetPath, datasetName, datasetFormat,
                    datasetDescription, datasetAttributes, data);
            System.out.println("Dataset correctly loaded:\n" + dataset);
        } catch (IOException e) {
            System.err.println("[Reader::loadDataset]: " + e.getMessage());
        }

        return dataset;
    }

    public static String getComponentFullName(String alias) {
        return componentsProperties.getProperty(alias);
    }
}
