package at.fhv.sim.des.events.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.events.IEvent;
import at.fhv.sim.des.parts.ISimPart;

public class ArrivalFromSourceEvent implements IEvent {
    private double eventTime;
    private final ISimPart simPart;
    private final IElement element;

    public ArrivalFromSourceEvent(double eventTime, ISimPart simPart, IElement element) {
        this.eventTime = eventTime;
        this.simPart = simPart;
        this.element = element;
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
        simPart.handleIncoming(element);
    }
}
