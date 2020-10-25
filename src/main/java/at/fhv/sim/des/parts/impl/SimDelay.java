package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.exceptions.QueueOverrunException;
import at.fhv.sim.des.parts.IDelay;
import at.fhv.sim.des.parts.IQueue;
import at.fhv.sim.des.parts.ISimPart;
import at.fhv.sim.des.scheduling.IScheduler;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.impl.RessourceReport;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class SimDelay implements IDelay {
    private final ISimPart outPort;
    private final IQueue queue;
    private boolean busy;
    private final AbstractRealDistribution dist;
    private final IScheduler scheduler;
    private final IReport report;
    private final Mean idleMean = new Mean();
    private final Mean busyMean = new Mean();
    private double lastSwitchTime = 0;

    public SimDelay(ISimPart outPort, IQueue queue, AbstractRealDistribution dist, IScheduler scheduler, String name) {
        this.outPort = outPort;
        this.queue = queue;
        this.dist = dist;
        this.scheduler = scheduler;
        busy = false;
        this.report = new RessourceReport(name);
    }

    @Override
    public void handleIncoming(IElement el) {
        if (!busy) {
            scheduler.scheduleDiscreteEvent(dist.sample(), () -> pushToNext(el));
            busy = true;
            idleMean.increment(scheduler.getCurrentTime() - lastSwitchTime);
            lastSwitchTime = scheduler.getCurrentTime();
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
        busyMean.increment(scheduler.getCurrentTime() - lastSwitchTime);
        lastSwitchTime = scheduler.getCurrentTime();
        if (!queue.isEmpty()) {
            IElement element = queue.getElement();
            this.handleIncoming(element);
        }
    }

    @Override
    public void init() {
        queue.init();
        busy = false;
        outPort.init();
        lastSwitchTime = 0;
        busyMean.clear();
        idleMean.clear();
        report.init();
    }

    @Override
    public IReport getReport() {
        report.addValue(((100 * busyMean.getResult()) / (busyMean.getResult() + idleMean.getResult())));
        return report;
    }
}
