package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.parts.IRessource;

public class Teller implements IRessource {
    private boolean busy = false;

    @Override
    public boolean isBusy() {
        return busy;
    }

    @Override
    public void busy() {
        busy = true;
    }

    @Override
    public void idle() {
        busy = false;
    }
}
