package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.exceptions.QueueOverrunException;
import at.fhv.sim.des.parts.IQueue;
import at.fhv.sim.des.parts.ISimPart;
import at.fhv.sim.des.scheduling.IScheduler;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

public class SimDelay implements ISimPart {
    private final ISimPart outPort;
    private final IQueue queue;
    private boolean busy;
    private final AbstractRealDistribution dist;
    private final IScheduler scheduler;

    public SimDelay(ISimPart outPort, IQueue queue, AbstractRealDistribution dist, IScheduler scheduler) {
        this.outPort = outPort;
        this.queue = queue;
        this.dist = dist;
        this.scheduler = scheduler;
        busy = false;
    }

    @Override
    public void handleIncoming(IElement el) {
        if(!busy) {
            scheduler.scheduleDiscreteEvent(dist.sample(), () -> pushToNext(el));
            busy = true;
        } else {
            try {
                queue.addElement(el);
            } catch (QueueOverrunException e) {
                scheduler.abortSimulation();
            }
        }
    }

    @Override
    public void pushToNext(IElement el) {
        outPort.handleIncoming(el);
        busy = false;
        if(!queue.isEmpty()) {
            IElement element = queue.getElement();
            handleIncoming(element);
        }
    }

    @Override
    public void init() {
        queue.init();
        busy = false;
        outPort.init();
    }
}
