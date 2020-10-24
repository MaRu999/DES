package at.fhv.sim.des.parts;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.exceptions.QueueOverrunException;

public interface IQueue extends IReportingPart {

    void addElement(IElement el) throws QueueOverrunException;

    IElement getElement();

    void clearQueue();

    boolean isEmpty();

    void addLengthToStatistic(double d);

}
