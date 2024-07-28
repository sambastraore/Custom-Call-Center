package sn.ept.dic2git.stoch.entities;

public class CallData {
    private double arrivalTime; // Temps d'arrivée en heures
    private int serviceType;

    public CallData(double arrivalTime, int serviceType) {
        this.arrivalTime = arrivalTime;
        this.serviceType = serviceType;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceType() {
        return serviceType;
    }


}
