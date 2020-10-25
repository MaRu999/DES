package at.fhv.sim.des.statistics.impl;

import at.fhv.sim.des.statistics.IReport;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class RessourceReport implements IReport {
    private final Mean mean = new Mean();
    private final String name;

    public RessourceReport(String name) {
        this.name = name;
    }

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
        return "Report for: " + name + ": average Utilization: " + getAverage() + "%";
    }

    @Override
    public void init() {
        mean.clear();
    }
}
