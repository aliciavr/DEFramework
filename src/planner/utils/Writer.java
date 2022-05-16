package planner.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import planner.configuration.Experiment;

public class Writer {

    public static void writeExperimentInfo(Experiment experiment) {
        String experimentPath = Reader.getExperimentPath(experiment.getEXPERIMENT_ID());
        String experimentFolderPath = experimentPath.substring(0,experimentPath.lastIndexOf("/"));
        System.out.println(experimentFolderPath);
        String experimentInfoPath = experimentFolderPath + "/ExperimentInfo.log";
        String executionsInfoPath = experimentFolderPath + "/Execution";
        try {
            Files.write(Paths.get(experimentInfoPath), experiment.toString().getBytes());
            int i = 0;
            for (ArrayList<ExecutionResults> executionResults: experiment.getExecutionsResults()) {
                String executionData = "";
                for (ExecutionResults results: executionResults) {
                    executionData += results.toString();
                }
                Files.write(Paths.get(executionsInfoPath + i + ".csv"), executionData.getBytes());
                i++;
            }
        } catch (IOException ex) {
            System.err.println("Output path not found.");
        }
    }

}
