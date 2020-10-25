package at.fhv.sim.des.model.impl;

import at.fhv.sim.des.model.ISimulationModel;
import at.fhv.sim.des.parts.ISource;
import at.fhv.sim.des.scheduling.IScheduler;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.IStatisticsCollector;

import java.util.List;

public class SimulationModel implements ISimulationModel {
    private final IScheduler scheduler;
    private final ISource modelSource;
    private final IStatisticsCollector statisticsCollector;

    public SimulationModel(IScheduler scheduler, ISource modelSource, IStatisticsCollector statisticsCollector) {
        this.scheduler = scheduler;
        this.modelSource = modelSource;
        this.statisticsCollector = statisticsCollector;
    }

    @Override
    public List<IReport> runSimulation() {
        while (!scheduler.isFinished()) {
            modelSource.scheduleArrival();
            scheduler.executeNextEvent();
        }
        if (scheduler.wasAborted()) {
            return statisticsCollector.getAbortionReports();
        } else {
            return statisticsCollector.getAllReports();
        }
    }

    @Override
    public boolean isFinished() {
        return scheduler.isFinished();
    }

    @Override
    public void init() {
        modelSource.init();
    }
}
