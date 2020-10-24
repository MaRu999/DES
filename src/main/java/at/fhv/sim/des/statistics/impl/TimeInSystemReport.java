package at.fhv.sim.des.statistics.impl;

import at.fhv.sim.des.statistics.IReport;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class TimeInSystemReport implements IReport {
    private final Mean mean = new Mean();

    @Override
    public void addValue(double val) {
        mean.increment(val);
    }

    @Override
    public double getAverage() {
        return mean.getResult();
    }

    @Override
    public String reportToString() {
        return "Average Time in System: " + mean.getResult();
    }

    @Override
    public void init() {
        mean.clear();
    }
}
