package at.fhv.sim.des;

import at.fhv.sim.des.events.IEvent;
import at.fhv.sim.des.events.impl.SimulationEndEvent;
import at.fhv.sim.des.model.ISimTemplate;
import at.fhv.sim.des.model.impl.BankSimTemplate;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting run...");
        ISimTemplate sim = new BankSimTemplate();
        //System.out.println(sim.createNewSimulation().runSimulation());
        //System.out.println(sim.createNewSimulationSeeded(1).runSimulation());
        sim.runConcurrent(500, 1000);
        System.out.println("Finished run.");
    }
}
