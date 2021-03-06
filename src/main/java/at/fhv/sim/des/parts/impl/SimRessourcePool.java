package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.parts.IRessource;
import at.fhv.sim.des.parts.IRessourcePool;
import at.fhv.sim.des.statistics.IReport;

import java.util.LinkedList;
import java.util.List;

public class SimRessourcePool implements IRessourcePool {
    private final int maxAmount;
    private final List<IRessource> tellers = new LinkedList<>();


    public SimRessourcePool(int maxAmount) {
        this.maxAmount = maxAmount;
        this.init();
    }

    @Override
    public IRessource getAvailableRessource() {
        IRessource teller = null;
        for (IRessource res : tellers) {
            if(!res.isBusy()) {
                teller = res;
                break;
            }
        }
        return teller;
    }

    @Override
    public void init() {
        tellers.clear();
        for (int i = 0; i < maxAmount; i++) {
            tellers.add(new Teller());
        }
    }

}
