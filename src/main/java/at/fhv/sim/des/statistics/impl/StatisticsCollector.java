package at.fhv.sim.des.statistics.impl;

import at.fhv.sim.des.parts.IReportingPart;
import at.fhv.sim.des.statistics.IStatisticsCollector;

import java.util.LinkedList;
import java.util.List;

public class StatisticsCollector implements IStatisticsCollector {
    private List<IReportingPart> reportingParts = new LinkedList<>();

    @Override
    public void registerReportingPart(IReportingPart reportingPart) {
        reportingParts.add(reportingPart);
    }

    @Override
    public void registerAllReportingParts(List<IReportingPart> parts) {
        reportingParts.addAll(parts);
    }

    public String collectToString() {
        StringBuilder sb = new StringBuilder();
        reportingParts.stream().parallel().forEach( part -> {
            sb.append(part.getReport().reportToString());
        });
        return sb.toString();
    }

    @Override
    public String abortingToString() {
        return "RUN ABORTED DUE TO QUEUE OVERRUN!!!";
    }


}
