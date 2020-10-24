package at.fhv.sim.des.elements.factory;

import at.fhv.sim.des.elements.impl.Customer;

public class CustomerFactory {
    private double currentArrivalTime = 0;
    private final double arrivalRate;

    public CustomerFactory(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public Customer createCustomer() {
        currentArrivalTime += arrivalRate;
        return new Customer(currentArrivalTime);
    }

    public double getNextArrivalTime() {
        return currentArrivalTime + arrivalRate;
    }

    public void reset() {
        currentArrivalTime = 0;
    }
}
