package at.fhv.sim.des.scheduling.impl;

import at.fhv.sim.des.scheduling.IClock;

public class SimClock implements IClock {
    private double currentTime = 0;

    @Override
    public double getCurrentTime() {
        return currentTime;
    }

    @Override
    public void setTime(double newTime) {
        currentTime = newTime;
    }

    @Override
    public void init() {
        currentTime = 0;
    }
}
