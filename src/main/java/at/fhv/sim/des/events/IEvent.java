package at.fhv.sim.des.events;

public interface IEvent {

    double getEventTime();

    void setEventTime(double time);

    void execute();
}
