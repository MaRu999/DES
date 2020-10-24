package at.fhv.sim.des.elements;

import at.fhv.sim.des.IInitiable;

public interface IElementFactory extends IInitiable {
    IElement createElement();

    double getNextArrivalTime();
}
