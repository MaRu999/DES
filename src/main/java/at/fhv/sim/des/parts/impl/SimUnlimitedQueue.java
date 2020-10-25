package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.parts.IQueue;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.impl.QueueReport;

import java.util.LinkedList;
import java.util.Queue;

public class SimUnlimitedQueue implements IQueue {
    private final Queue<IElement> elements = new LinkedList<>();
    private final IReport report;

    public SimUnlimitedQueue(String name) {
        this.report = new QueueReport(name);
    }

    @Override
    public void addElement(IElement el) {
        elements.offer(el);
        addLengthToStatistic(elements.size());
    }

    @Override
    public IElement getElement() {
        return elements.poll();
    }

    @Override
    public void clearQueue() {
        elements.clear();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public void addLengthToStatistic(double d) {
        report.addValue(d);
    }

    @Override
    public IReport getReport() {
        return report;
    }

    @Override
    public void init() {
        clearQueue();
        report.init();
    }
}
