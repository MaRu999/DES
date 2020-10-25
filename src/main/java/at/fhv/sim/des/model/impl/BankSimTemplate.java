package at.fhv.sim.des.model.impl;

import at.fhv.sim.des.model.ISimTemplate;
import at.fhv.sim.des.model.ISimulationModel;
import at.fhv.sim.des.parts.*;
import at.fhv.sim.des.parts.impl.*;
import at.fhv.sim.des.scheduling.IClock;
import at.fhv.sim.des.scheduling.IScheduler;
import at.fhv.sim.des.scheduling.impl.SimClock;
import at.fhv.sim.des.scheduling.impl.SimScheduler;
import at.fhv.sim.des.statistics.IReport;
import at.fhv.sim.des.statistics.IStatisticsCollector;
import at.fhv.sim.des.statistics.impl.AbortionReport;
import at.fhv.sim.des.statistics.impl.StatisticsCollector;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BankSimTemplate implements ISimTemplate {
    private final HashMap<String, Mean> means = new HashMap<>();
    private int aborted;
    private final DecimalFormat df = new DecimalFormat("0.000");

    @Override
    public ISimulationModel createNewSimulation(boolean repeatable) {
        AbstractRealDistribution delayDist = new TriangularDistribution(1, 2, 4);
        AbstractRealDistribution serviceDist = new TriangularDistribution(3, 5, 20);
        AbstractRealDistribution needAddServiceDist = new UniformRealDistribution(0, 1);
        AbstractRealDistribution needToSeeCashierDist = new UniformRealDistribution(0, 1);
        if (repeatable) {
            delayDist.reseedRandomGenerator(1);
            serviceDist.reseedRandomGenerator(1);
            needAddServiceDist.reseedRandomGenerator(1);
            needToSeeCashierDist.reseedRandomGenerator(1);
        }
        IClock sysClock = new SimClock();
        ISink sink = new SimSink(sysClock);
        IQueue qu = new SimQueue(40, "ATM queue");
        IScheduler scheduler = new SimScheduler(sysClock);
        IStatisticsCollector collector = new StatisticsCollector();
        collector.registerReportingPart(qu);
        collector.registerReportingPart(sink);
        IRessourcePool ressourcePool = new SimRessourcePool(5);
        IQueue quTwo = new SimUnlimitedQueue("Service queue");
        collector.registerReportingPart(quTwo);
        IService service = new SimService(ressourcePool, quTwo, serviceDist, scheduler, sink, "Tellers");
        collector.registerReportingPart(service);
        ISimPart needAdditionalService = new SimSelectOutput(service, sink, 0.3, needAddServiceDist);
        IDelay delay = new SimDelay(needAdditionalService, qu, delayDist, scheduler, "ATM");
        collector.registerReportingPart(delay);
        ISimPart needToSeeCashier = new SimSelectOutput(service, delay, 0.5, needToSeeCashierDist);
        ISource src = new SimSource(needToSeeCashier, 4.0 / 3.0, scheduler, 50000);
        return new SimulationModel(scheduler, src, collector);
    }

    private void collectStatistics(IReport report) {
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


    @Override
    public String runConcurrent(int numberOfParallelRuns, int totalNumberOfRuns, boolean repeatable) {
        List<ISimulationModel> mods = new LinkedList<>();
        for (int i = 0; i < numberOfParallelRuns; i++) {
            mods.add(createNewSimulation(repeatable));
        }
        for (int i = 0; i < totalNumberOfRuns; i += numberOfParallelRuns) {
            mods.stream().parallel().forEach(sim -> {
                sim.runSimulation().forEach(this::collectStatistics);
                sim.init();
            });
        }
        return getStatisticsReport();
    }
}
