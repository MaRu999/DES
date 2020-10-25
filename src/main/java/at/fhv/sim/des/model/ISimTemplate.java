package at.fhv.sim.des.model;

public interface ISimTemplate {
    ISimulationModel createNewSimulation(boolean repeatable);

    String runConcurrent(int numberOfParallelRuns, int totalNumberOfRuns, boolean repeatable);

    String getStatisticsReport();
}
