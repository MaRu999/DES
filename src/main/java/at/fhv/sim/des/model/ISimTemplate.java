package at.fhv.sim.des.model;

public interface ISimTemplate {
    ISimulationModel createNewSimulation(boolean repeatable);

    void runConcurrent(int numberOfParallelRuns, int totalNumberOfRuns, boolean repeatable);
}
