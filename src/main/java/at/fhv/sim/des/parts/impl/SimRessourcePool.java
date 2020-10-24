package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.parts.IRessource;
import at.fhv.sim.des.parts.IRessourcePool;

import java.util.LinkedList;
import java.util.List;

public class SimRessourcePool implements IRessourcePool {
    private int maxAmount;
    private List<IRessource> tellers = new LinkedList<>();

    public SimRessourcePool(int maxAmount) {
        this.maxAmount = maxAmount;
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
        if(teller != null) teller.busy();
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
