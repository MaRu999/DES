package at.fhv.sim.des.model.impl;

import at.fhv.sim.des.model.ISimTemplate;
import at.fhv.sim.des.model.ISimulationModel;
import at.fhv.sim.des.parts.*;
import at.fhv.sim.des.parts.impl.*;
import at.fhv.sim.des.scheduling.IClock;
import at.fhv.sim.des.scheduling.IScheduler;
import at.fhv.sim.des.scheduling.impl.SimClock;
import at.fhv.sim.des.scheduling.impl.SimScheduler;
import at.fhv.sim.des.statistics.IStatisticsCollector;
import at.fhv.sim.des.statistics.impl.StatisticsCollector;
import org.apache.commons.math3.distribution.TriangularDistribution;

import java.util.LinkedList;
import java.util.List;

public class BankSimTemplate implements ISimTemplate {

    @Override
    public ISimulationModel createNewSimulation() {
        IClock sysClock = new SimClock();
        ISink sink = new SimSink(sysClock);
        IQueue qu = new SimQueue(50000, "ATM");
        IScheduler sched = new SimScheduler(sysClock);
        IStatisticsCollector collector = new StatisticsCollector();
        collector.registerReportingPart(qu);
        collector.registerReportingPart(sink);
        IRessourcePool ressourcePool = new SimRessourcePool(5);
        IQueue quTwo = new SimUnlimitedQueue("Cashiers");
        collector.registerReportingPart(quTwo);
        ISimPart service = new SimService(ressourcePool, quTwo, new TriangularDistribution(3, 5, 20), sched, sink);
        ISimPart needAdditionalService = new SimSelectOutput(service, sink, 0.3);
        ISimPart delay = new SimDelay(needAdditionalService, qu, new TriangularDistribution(1, 2, 4), sched);
        ISimPart needToSeeCashier = new SimSelectOutput(service, delay, 0.5);
        ISource src = new SimSource(needToSeeCashier, 0.7, sched, 50000);
        return new SimulationModel(sched, src, collector);
    }

    @Override
    public void runConcurrent(int numberOfParallelRuns, int totalNumberOfRuns) {
        List<ISimulationModel> mods = new LinkedList<>();
        for (int i = 0; i < numberOfParallelRuns; i++) {
            mods.add(createNewSimulation());
        }
        for (int i = 0; i < totalNumberOfRuns; i += numberOfParallelRuns) {
            mods.stream().parallel().forEach(sim -> {
                System.out.println(sim.runSimulation());
                sim.init();
            });

        }
    }

}