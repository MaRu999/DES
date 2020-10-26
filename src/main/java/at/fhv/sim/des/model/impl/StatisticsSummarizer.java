package at.fhv.sim.des.model.impl;

import at.fhv.sim.des.model.IStatisticsSummarizer;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.impl.AbortionReport;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.text.DecimalFormat;
import java.util.HashMap;

public class StatisticsSummarizer implements IStatisticsSummarizer {
    private final HashMap<String, Mean> means = new HashMap<>();
    private int aborted = 0;
    private final DecimalFormat df = new DecimalFormat("0.000");

    @Override
    public void collectStatistics(IReport report) {
        if (report instanceof AbortionReport) {
            aborted += 1;
        } else {
            String name = report.getName();
            if (means.containsKey(name)) {
                means.get(name).increment(report.getAverage());
            } else {
                Mean mean = new Mean();
                mean.increment(report.getAverage());
                means.put(name, mean);
            }
        }
    }

    @Override
    public String getStatisticsReport() {
        StringBuilder sb = new StringBuilder();
        means.forEach((name, mean) -> sb.append(name).append(": ").append(df.format(mean.getResult())).append(System.lineSeparator()));
        sb.append("Aborted runs: ").append(aborted);
        return sb.toString();
    }


}
