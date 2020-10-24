package at.fhv.sim.des.parts;

import at.fhv.sim.des.IInitiable;
import at.fhv.sim.des.elements.IElement;

public interface ISimPart extends IInitiable {

    void handleIncoming(IElement el);

    void pushToNext(IElement el);
}
