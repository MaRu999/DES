package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.exceptions.QueueOverrunException;
import at.fhv.sim.des.parts.*;
import at.fhv.sim.des.scheduling.IScheduler;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.impl.RessourceReport;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.HashMap;

public class SimService implements IService {
    private final IRessourcePool ressourcePool;
    private final IQueue queue;
    private final AbstractRealDistribution dist;
    private final IScheduler scheduler;
    private final ISimPart outPort;
    private final HashMap<IElement, IRessource> ressourceHashMap = new HashMap<>();
    private final IReport report;
    private final Mean idleMean = new Mean();
    private final Mean busyMean = new Mean();

    public SimService(IRessourcePool ressourcePool, IQueue queue, AbstractRealDistribution dist, IScheduler scheduler, ISimPart outPort, String ressourceName) {
        this.ressourcePool = ressourcePool;
        this.queue = queue;
        this.dist = dist;
        this.scheduler = scheduler;
        this.outPort = outPort;
        this.report = new RessourceReport(ressourceName);
    }

    @Override
    public void handleIncoming(IElement el) {
        IRessource teller = ressourcePool.getAvailableRessource();
        if (teller != null) {
            scheduler.scheduleDiscreteEvent(dist.sample(), () -> pushToNext(el));
            idleMean.increment(teller.busy(scheduler.getCurrentTime()));
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
        busyMean.increment(teller.idle(scheduler.getCurrentTime()));
        if (!queue.isEmpty()) {
            IElement element = queue.getElement();
            handleIncoming(element);
        }
    }

    @Override
    public void init() {
        ressourcePool.init();
        queue.init();
        ressourceHashMap.clear();
        outPort.init();
        idleMean.clear();
        busyMean.clear();
        report.init();
    }

    @Override
    public IReport getReport() {
        report.addValue((100 * busyMean.getResult()) / (idleMean.getResult() + busyMean.getResult()));
        return report;
    }
}
