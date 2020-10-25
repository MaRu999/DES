package at.fhv.sim.des.parts.impl;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.parts.ISimPart;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

public class SimSelectOutput implements ISimPart {
    private final ISimPart optionOne;
    private final ISimPart optionTwo;
    private final double chanceOfSelection;
    private final AbstractRealDistribution dist;

    public SimSelectOutput(ISimPart optionOne, ISimPart optionTwo, double chanceOfSelection, AbstractRealDistribution dist) {
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.chanceOfSelection = chanceOfSelection;
        this.dist = dist;
    }

    @Override
    public void handleIncoming(IElement el) {
        if(dist.sample() < chanceOfSelection) {
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
