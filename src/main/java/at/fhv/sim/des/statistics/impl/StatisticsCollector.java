package at.fhv.sim.des.statistics.impl;

import at.fhv.sim.des.parts.IReportingPart;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.IStatisticsCollector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsCollector implements IStatisticsCollector {
    private final List<IReportingPart> reportingParts = new LinkedList<>();

    @Override
    public void registerReportingPart(IReportingPart reportingPart) {
        reportingParts.add(reportingPart);
    }

    @Override
    public void registerAllReportingParts(List<IReportingPart> parts) {
        reportingParts.addAll(parts);
    }

    @Override
    public String collectToString() {
        StringBuilder sb = new StringBuilder();
        reportingParts.forEach(part -> sb.append(part.getReport().reportToString()).append(System.lineSeparator()));
        return sb.toString();
    }

    @Override
    public String abortingToString() {
        return "RUN ABORTED DUE TO QUEUE OVERRUN!!!";
    }

    @Override
    public List<IReport> getAllReports() {
        return reportingParts.stream().map(IReportingPart::getReport).collect(Collectors.toList());
    }

    @Override
    public List<IReport> getAbortionReports() {
        List<IReport> abReport = new LinkedList<>();
        abReport.add(new AbortionReport());
        return abReport;
    }

}
