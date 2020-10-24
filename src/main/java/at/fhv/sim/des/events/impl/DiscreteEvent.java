package at.fhv.sim.des.events.impl;

import at.fhv.sim.des.events.ICallback;
import at.fhv.sim.des.events.IEvent;

public class DiscreteEvent implements IEvent {
    private double eventTime;
    private final ICallback callback;

    public DiscreteEvent(double eventTime, ICallback callback) {
        this.eventTime = eventTime;
        this.callback = callback;
    }

    @Override
    public double getEventTime() {
        return eventTime;
    }

    @Override
    public void setEventTime(double time) {
        eventTime = time;
    }

    @Override
    public void execute() {
        callback.executeCallback();
    }
}
