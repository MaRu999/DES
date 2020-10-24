package at.fhv.sim.des.model;

public interface ISimTemplate {
    ISimulationModel createNewSimulation();

    void runConcurrent(int numberOfParallelRuns, int totalNumberOfRuns);
}
