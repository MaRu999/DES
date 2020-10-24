package at.fhv.sim.des.model;

import at.fhv.sim.des.IInitiable;

public interface ISimulationModel extends IInitiable {

    String runSimulation();

    boolean isFinished();
}
