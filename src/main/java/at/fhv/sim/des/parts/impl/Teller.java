package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.parts.IRessource;

public class Teller implements IRessource {
    private double lastSwitchTime = 0;
    private boolean busy = false;

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public double busy(double curTime) {
        busy = true;
        double temp = curTime - lastSwitchTime;
        lastSwitchTime = curTime;
        return temp;
    }

    @Override
    public double idle(double curTime) {
        busy = false;
        double temp = curTime - lastSwitchTime;
        lastSwitchTime = curTime;
        return temp;
    }

    @Override
    public void init() {
        lastSwitchTime = 0;
        busy  = false;
    }
}
