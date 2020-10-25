package at.fhv.sim.des.scheduling;

import at.fhv.sim.des.IInitiable;
import at.fhv.sim.des.events.ICallback;

public interface IScheduler extends IInitiable {

    void scheduleDiscreteEvent(double time, ICallback callback);

    void scheduleArrivalFromSourceEvent(double time, ICallback callback);

    void scheduleSimulationEndEvent(double time);

    void executeNextEvent();

    void stopSimulation();

    boolean isFinished();

    void abortSimulation();

    boolean wasAborted();

    double getCurrentTime();
}
