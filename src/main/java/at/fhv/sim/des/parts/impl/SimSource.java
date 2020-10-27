package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.elements.factory.CustomerFactory;
import at.fhv.sim.des.parts.ISimPart;
import at.fhv.sim.des.parts.ISource;
import at.fhv.sim.des.scheduling.IScheduler;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

public class SimSource implements ISource {
    private final ISimPart outPort;
    private int counter = 0;
    private final CustomerFactory factory;
    private final IScheduler scheduler;
    private final int endOfSimAt;

    public SimSource(ISimPart outPort, AbstractRealDistribution arrivalRate, IScheduler scheduler, int endOfSimAt) {
        this.outPort = outPort;
        this.factory = new CustomerFactory(arrivalRate);
        this.scheduler = scheduler;
        this.endOfSimAt = endOfSimAt;
    }


    @Override
    public void scheduleArrival() {
        if (counter < endOfSimAt) {
            IElement newEl = factory.createElement();
            scheduler.scheduleArrivalFromSourceEvent(newEl.getArrivalTime(), () -> outPort.handleIncoming(newEl));
            counter += 1;
        } else if (counter == endOfSimAt) {
            scheduler.scheduleSimulationEndEvent(factory.getNextArrivalTime());
            counter += 1;
        }
    }

    @Override
    public void init() {
        counter = 0;
        factory.init();
        scheduler.init();
        outPort.init();

    }
}
