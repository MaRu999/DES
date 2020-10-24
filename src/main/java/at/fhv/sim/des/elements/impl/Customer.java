package at.fhv.sim.des.elements.impl;

import at.fhv.sim.des.elements.IElement;

public class Customer implements IElement {
    private double arrivalTime;

    public Customer(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
