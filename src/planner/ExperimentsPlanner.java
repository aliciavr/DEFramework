package planner;

import exceptions.ExperimentFormatException;
import exceptions.IncompatibleConfigurationException;
import planner.utils.Reader;
import planner.configuration.Experiment;
import planner.utils.Writer;

public class ExperimentsPlanner {

    private static int NUM_EXPERIMENTS;

    public static void main(String[] args) {
        Reader.loadConfigurationData("rsc/dataConfig.properties");
        NUM_EXPERIMENTS = Reader.getNumExperiments();
        Experiment experiment;
        for (int i = 0; i < NUM_EXPERIMENTS; i++) {
            try {
                experiment = Reader.buildExperiment(Reader.loadExperimentProperties(i));
                experiment.run();
                Writer.writeExperimentInfo(experiment);
            } catch (IncompatibleConfigurationException | ExperimentFormatException e) {
                System.err.println(e.getMessage());
            }
        }

    }
}
