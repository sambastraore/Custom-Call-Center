package sn.ept.dic2git.stoch.entities;

public class Tuple1 {
    private final double mean_service_time;
    private final double std_service_time;
    private final String serviceType;

    public Tuple1(double mean_service_time, double std_service_time, String serviceType) {
        this.mean_service_time = mean_service_time;
        this.serviceType = serviceType;
        this.std_service_time = std_service_time;
    }

    public double getMean_service_time() {
        return mean_service_time;
    }

    public double getStd_service_time() {
        return std_service_time;
    }

    public String getServiceType() {
        return serviceType;
    }
}
