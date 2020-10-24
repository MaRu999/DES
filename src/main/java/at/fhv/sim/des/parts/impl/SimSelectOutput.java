package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.parts.ISimPart;

public class SimSelectOutput implements ISimPart {
    private final ISimPart optionOne;
    private final ISimPart optionTwo;
    private final double chanceOfSelection;

    public SimSelectOutput(ISimPart optionOne, ISimPart optionTwo, double chanceOfSelection) {
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.chanceOfSelection = chanceOfSelection;
    }

    @Override
    public void handleIncoming(IElement el) {
        if(Math.random() < chanceOfSelection) {
            optionOne.handleIncoming(el);
        } else {
            optionTwo.handleIncoming(el);
        }
    }

    @Override
    public void pushToNext(IElement el) {

    }

    @Override
    public void init() {
        optionOne.init();
        optionTwo.init();
    }
}
