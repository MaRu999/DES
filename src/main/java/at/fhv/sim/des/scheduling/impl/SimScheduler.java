package at.fhv.sim.des.scheduling.impl;

import at.fhv.sim.des.events.ICallback;
import at.fhv.sim.des.events.IEvent;
import at.fhv.sim.des.events.impl.DiscreteEvent;
import at.fhv.sim.des.events.impl.SimulationEndEvent;
import at.fhv.sim.des.scheduling.IClock;
import at.fhv.sim.des.scheduling.IScheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SimScheduler implements IScheduler {
    private final IClock sysClock;
    private final PriorityQueue<IEvent> events = new PriorityQueue<>(Comparator.comparingDouble(IEvent::getEventTime));
    private boolean isFinished = false;
    private boolean aborted = false;

    public SimScheduler(IClock sysClock) {
        this.sysClock = sysClock;
    }

    @Override
    public void scheduleDiscreteEvent(double time, ICallback callback) {
        events.offer(new DiscreteEvent(time + sysClock.getCurrentTime(), callback));
    }

    @Override
    public void scheduleArrivalFromSourceEvent(double time, ICallback callback) {
        events.offer(new DiscreteEvent(time, callback));
    }

    @Override
    public void scheduleSimulationEndEvent(double time) {
        events.offer(new SimulationEndEvent(time));
    }

    @Override
    public void executeNextEvent() {
        IEvent nextEvent = events.poll();
        if (nextEvent != null) {
            if (nextEvent instanceof SimulationEndEvent) {
                stopSimulation();
            } else {
                sysClock.setTime(nextEvent.getEventTime());
                nextEvent.execute();
            }
        } else {
            stopSimulation();
        }
    }

    @Override
    public void stopSimulation() {
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void abortSimulation() {
        aborted = true;
        stopSimulation();
    }

    @Override
    public boolean wasAborted() {
        return aborted;
    }

    @Override
    public void init() {
        events.clear();
        sysClock.init();
        isFinished = false;
        aborted = false;
    }
}
