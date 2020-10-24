package at.fhv.sim.des.statistics;

import at.fhv.sim.des.IInitiable;

public interface IReport extends IInitiable {

    void addValue(double val);

    double getAverage();

    String reportToString();
}
