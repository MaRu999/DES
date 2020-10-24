package at.fhv.sim.des.scheduling;

import at.fhv.sim.des.IInitiable;

public interface IClock extends IInitiable {
    double getCurrentTime();

    void setTime(double newTime);
}
