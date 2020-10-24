package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.events.IEvent;
import at.fhv.sim.des.events.impl.DepartureEvent;
import at.fhv.sim.des.exceptions.QueueOverrunException;
import at.fhv.sim.des.parts.IQueue;
import at.fhv.sim.des.parts.IRessource;
import at.fhv.sim.des.parts.IRessourcePool;
import at.fhv.sim.des.parts.ISimPart;
import at.fhv.sim.des.scheduling.IScheduler;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

import java.util.HashMap;

public class SimService implements ISimPart {
    private IRessourcePool ressourcePool;
    private IQueue queue;
    private AbstractRealDistribution dist;
    private IScheduler scheduler;
    private ISimPart outPort;
    private HashMap<IElement, IRessource> ressourceHashMap = new HashMap<>();

    public SimService(IRessourcePool ressourcePool, IQueue queue, AbstractRealDistribution dist, IScheduler scheduler, ISimPart outPort) {
        this.ressourcePool = ressourcePool;
        this.queue = queue;
        this.dist = dist;
        this.scheduler = scheduler;
        this.outPort = outPort;
    }

    @Override
    public void handleIncoming(IElement el) {
        IRessource teller = ressourcePool.getAvailableRessource();
        if(teller != null) {
            IEvent nextEvent = new DepartureEvent(dist.sample(), this, el);
            scheduler.scheduleEvent(nextEvent);
            ressourceHashMap.put(el, teller);
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
        IRessource teller = ressourceHashMap.remove(el);
        teller.idle();
        if(!queue.isEmpty()) {
            IElement element = queue.getElement();
            handleIncoming(element);
        }
    }

    @Override
    public void init() {
        ressourcePool.init();
        queue.clearQueue();
        ressourceHashMap.clear();
    }
}
