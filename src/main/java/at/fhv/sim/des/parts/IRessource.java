package at.fhv.sim.des.parts;

import at.fhv.sim.des.IInitiable;

public interface IRessource extends IInitiable {
    boolean isBusy();

    double busy(double curTime);

    double idle(double curTIme);
}
