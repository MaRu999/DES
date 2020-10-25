package at.fhv.sim.des.model;

import at.fhv.sim.des.IInitiable;
import at.fhv.sim.des.statistics.IReport;

import java.util.List;

public interface ISimulationModel extends IInitiable {

    List<IReport> runSimulation();
}
