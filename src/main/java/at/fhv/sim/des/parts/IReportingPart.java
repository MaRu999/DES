package at.fhv.sim.des.parts;

import at.fhv.sim.des.IInitiable;
import at.fhv.sim.des.statistics.IReport;

public interface IReportingPart extends IInitiable {
    IReport getReport();
}
