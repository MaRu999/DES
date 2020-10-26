package at.fhv.sim.des.model;

import at.fhv.sim.des.statistics.IReport;

public interface IStatisticsSummarizer {
    void collectStatistics(IReport report);

    String getStatisticsReport();
}
