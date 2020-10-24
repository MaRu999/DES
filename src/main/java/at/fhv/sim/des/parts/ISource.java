package at.fhv.sim.des.parts;

import at.fhv.sim.des.IInitiable;

public interface ISource extends IInitiable {
    void scheduleArrival();
}
