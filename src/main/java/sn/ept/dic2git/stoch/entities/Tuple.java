package sn.ept.dic2git.stoch.entities;

public class Tuple {
    private final double arrivalRate;
    private final String serviceType;

    public Tuple(double arrivalRate, String serviceType) {
        this.arrivalRate = arrivalRate;
        this.serviceType = serviceType;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public String getServiceType() {
        return serviceType;
    }
}
