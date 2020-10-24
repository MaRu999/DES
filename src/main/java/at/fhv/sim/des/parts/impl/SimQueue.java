package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.exceptions.QueueOverrunException;
import at.fhv.sim.des.parts.IQueue;
import at.fhv.sim.des.parts.IReportingPart;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.impl.QueueReport;

import java.util.LinkedList;
import java.util.List;

public class SimQueue implements IQueue, IReportingPart {
    private final List<IElement> elements = new LinkedList<>();
    private final int maxCapacity;
    private final IReport report;

    public SimQueue(int maxCapacity, String name) {
        this.maxCapacity = maxCapacity;
        report = new QueueReport(name);
    }

    @Override
    public void addElement(IElement el) throws QueueOverrunException {
        if (elements.size() < maxCapacity) {
            elements.add(el);
            addLengthToStatistic(elements.size());
        } else {
            throw new QueueOverrunException("Queue was overrun!");
        }
    }

    @Override
    public IElement getElement() {
        if(!elements.isEmpty()) {
            addLengthToStatistic(elements.size() - 1);
            return elements.remove(0);
        } else {
            return null;
        }
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
}
