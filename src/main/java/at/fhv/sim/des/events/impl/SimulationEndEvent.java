package at.fhv.sim.des.events.impl;

import at.fhv.sim.des.events.IEvent;

public class SimulationEndEvent implements IEvent {
    private double endTime;

    public SimulationEndEvent(double endTime) {
        this.endTime = endTime;
    }

    @Override
    public double getEventTime() {
        return endTime;
    }

    @Override
    public void setEventTime(double time) {
        endTime = time;
    }

    @Override
    public void execute() {

    }
}
