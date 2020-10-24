package at.fhv.sim.des.scheduling;

import at.fhv.sim.des.IInitiable;
import at.fhv.sim.des.events.IEvent;

public interface IScheduler extends IInitiable {

    void scheduleEvent(IEvent event);

    void executeNextEvent();

    void stopSimulation();

    boolean isFinished();

    void abortSimulation();

    boolean wasAborted();
}
