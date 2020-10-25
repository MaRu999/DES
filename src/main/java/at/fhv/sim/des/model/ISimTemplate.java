package at.fhv.sim.des.model;

public interface ISimTemplate {
    ISimulationModel createNewSimulation();

    void runConcurrent(int numberOfParallelRuns, int totalNumberOfRuns);

    void runRepeatable(int numberOfParallelRuns, int totalNumberOfRuns);

    ISimulationModel createNewSimulationSeeded(long seed);
}
