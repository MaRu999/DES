package at.fhv.sim.des.events.impl;

import at.fhv.sim.des.events.ICallback;
import at.fhv.sim.des.events.IEvent;

public class ArrivalFromSourceEvent implements IEvent {
    private double eventTime;
    private ICallback callback;

    public ArrivalFromSourceEvent(double eventTime, ICallback callback) {
        this.eventTime = eventTime;
        this.callback = callback;
    }

    @Override
    public double getEventTime() {
        return eventTime;
    }

    @Override
    public void setEventTime(double time) {
        this.eventTime = time;
    }

    @Override
    public void execute() {
        callback.executeCallback();
    }
}
