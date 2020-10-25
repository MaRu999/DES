package at.fhv.sim.des.statistics;

import at.fhv.sim.des.parts.IReportingPart;

import java.util.List;

public interface IStatisticsCollector {
    void registerReportingPart(IReportingPart reportingPart);

    void registerAllReportingParts(List<IReportingPart> parts);

    String collectToString();

    String abortingToString();

    List<IReport> getAllReports();

    List<IReport> getAbortionReports();
}
