package at.fhv.sim.des;

import at.fhv.sim.des.model.ISimTemplate;
import at.fhv.sim.des.model.impl.BankSimTemplate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting run...");
        ISimTemplate sim = new BankSimTemplate();
        sim.runConcurrent(500, 1000, false);
        System.out.println("Finished run.");
    }
}
