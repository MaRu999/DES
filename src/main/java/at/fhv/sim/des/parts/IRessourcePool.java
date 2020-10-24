package at.fhv.sim.des.parts;

import at.fhv.sim.des.IInitiable;

public interface IRessourcePool extends IInitiable {

    IRessource getAvailableRessource();

}
