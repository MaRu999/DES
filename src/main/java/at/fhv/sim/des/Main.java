package at.fhv.sim.des;

import at.fhv.sim.des.model.ISimTemplate;
import at.fhv.sim.des.model.impl.BankSimTemplate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting run...");
        ISimTemplate sim = new BankSimTemplate();
        sim.runConcurrent(1000, 1000);
        System.out.println("Finished run.");
    }
}
