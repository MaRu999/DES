package at.fhv.sim.des.parts;

public interface IRessource {
    boolean isBusy();

    void busy();

    void idle();
}
