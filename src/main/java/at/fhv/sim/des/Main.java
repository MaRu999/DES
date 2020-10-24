package at.fhv.sim.des;

import at.fhv.sim.des.model.ISimulationModel;
import at.fhv.sim.des.model.impl.SimulationModel;
import at.fhv.sim.des.parts.IQueue;
import at.fhv.sim.des.parts.ISimPart;
import at.fhv.sim.des.parts.ISource;
import at.fhv.sim.des.parts.impl.SimDelay;
import at.fhv.sim.des.parts.impl.SimQueue;
import at.fhv.sim.des.parts.impl.SimSink;
import at.fhv.sim.des.parts.impl.SimSource;
import at.fhv.sim.des.scheduling.IClock;
import at.fhv.sim.des.scheduling.IScheduler;
import at.fhv.sim.des.scheduling.impl.SimClock;
import at.fhv.sim.des.scheduling.impl.SimScheduler;
import at.fhv.sim.des.statistics.IStatisticsCollector;
import at.fhv.sim.des.statistics.impl.StatisticsCollector;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting run...");
        IClock sysClock = new SimClock();
        SimSink sink = new SimSink(sysClock);
        SimQueue qu = new SimQueue(45, "ATM");
        IScheduler sched = new SimScheduler(sysClock);
        ISimPart delay = new SimDelay(sink, qu, new TriangularDistribution(1,2,4), sched);
        ISource src = new SimSource(delay, 0.7, sched);
        IStatisticsCollector collector = new StatisticsCollector();
        collector.registerReportingPart(qu);
        collector.registerReportingPart(sink);
        ISimulationModel mod = new SimulationModel(sched, src, collector);
        String res = mod.runSimulation();
        System.out.println(res);


    }
}
