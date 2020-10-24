package at.fhv.sim.des.scheduling.impl;

import at.fhv.sim.des.events.IEvent;
import at.fhv.sim.des.events.impl.ArrivalFromSourceEvent;
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
    public void scheduleEvent(IEvent event) {
        if (event instanceof SimulationEndEvent) {
            stopSimulation();
        } else if (event instanceof ArrivalFromSourceEvent) {
            events.add(event);
        } else {
            double delta = event.getEventTime();
            event.setEventTime(delta + sysClock.getCurrentTime());
            events.add(event);
        }
    }

    @Override
    public void executeNextEvent() {
        IEvent nextEvent = events.poll();
        if (nextEvent != null) {
            sysClock.setTime(nextEvent.getEventTime());
            System.out.println("sysClock = " + sysClock.getCurrentTime());
            nextEvent.execute();
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
