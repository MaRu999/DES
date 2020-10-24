package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.elements.factory.CustomerFactory;
import at.fhv.sim.des.events.IEvent;
import at.fhv.sim.des.events.impl.ArrivalFromSourceEvent;
import at.fhv.sim.des.events.impl.SimulationEndEvent;
import at.fhv.sim.des.parts.ISimPart;
import at.fhv.sim.des.parts.ISource;
import at.fhv.sim.des.scheduling.IScheduler;

public class SimSource implements ISource {
    private final ISimPart outPort;
    private int counter = 0;
    private final CustomerFactory factory;
    private final IScheduler scheduler;

    public SimSource(ISimPart outPort, double arrivalRate, IScheduler scheduler) {
        this.outPort = outPort;
        this.factory = new CustomerFactory(arrivalRate);
        this.scheduler = scheduler;
    }


    @Override
    public void scheduleArrival() {
        if (counter < 50000) {
            IElement newEl = factory.createCustomer();
            IEvent event = new ArrivalFromSourceEvent(newEl.getArrivalTime(), outPort, newEl);
            scheduler.scheduleEvent(event);
            System.out.println("counter = " + counter);
            counter += 1;
        } else if (counter == 50000) {
            IEvent simEnd = new SimulationEndEvent(factory.getNextArrivalTime());
            scheduler.scheduleEvent(simEnd);
            System.out.println("counter = " + counter);
            counter += 1;
        }

    }

    @Override
    public void init() {
        counter = 0;
        factory.reset();
    }
}
