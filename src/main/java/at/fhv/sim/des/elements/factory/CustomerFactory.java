package at.fhv.sim.des.elements.factory;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.elements.IElementFactory;
import at.fhv.sim.des.elements.impl.Customer;
import org.apache.commons.math3.distribution.AbstractRealDistribution;

public class CustomerFactory implements IElementFactory {
    private double currentArrivalTime = 0;
    private final AbstractRealDistribution arrivalRate;

    public CustomerFactory(AbstractRealDistribution arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    @Override
    public IElement createElement() {
        currentArrivalTime += arrivalRate.sample();
        return new Customer(currentArrivalTime);
    }

    @Override
    public double getNextArrivalTime() {
        return currentArrivalTime + arrivalRate.sample();
    }

    @Override
    public void init() {
        currentArrivalTime = 0;
    }
}
