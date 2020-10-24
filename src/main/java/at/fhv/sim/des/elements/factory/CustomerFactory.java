package at.fhv.sim.des.elements.factory;

import at.fhv.sim.des.elements.IElement;
import at.fhv.sim.des.elements.IElementFactory;
import at.fhv.sim.des.elements.impl.Customer;

public class CustomerFactory implements IElementFactory {
    private double currentArrivalTime = 0;
    private final double arrivalRate;

    public CustomerFactory(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    @Override
    public IElement createElement() {
        currentArrivalTime += arrivalRate;
        return new Customer(currentArrivalTime);
    }

    @Override
    public double getNextArrivalTime() {
        return currentArrivalTime + arrivalRate;
    }

    @Override
    public void init() {
        currentArrivalTime = 0;
    }
}
