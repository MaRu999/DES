package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.parts.ISink;
import at.fhv.sim.des.scheduling.IClock;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.impl.TimeInSystemReport;

public class SimSink implements ISink {
    private final IClock clock;
    private final IReport report = new TimeInSystemReport();

    public SimSink(IClock clock) {
        this.clock = clock;
    }

    @Override
    public void handleIncoming(IElement el) {
        double timeInSystem = clock.getCurrentTime() - el.getArrivalTime();
        report.addValue(timeInSystem);
    }

    @Override
    public void pushToNext(IElement el) {

    }

    @Override
    public void init() {
        report.init();
    }

    @Override
    public IReport getReport() {
        return report;
    }
}
