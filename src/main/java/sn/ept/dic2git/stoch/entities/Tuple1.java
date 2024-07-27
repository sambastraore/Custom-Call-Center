package sn.ept.dic2git.stoch.entities;

public class Tuple1 {
    private final double mu;
    private final double sigma;
    private final String serviceType;

    public Tuple1(double mu, double sigma, String serviceType) {
        this.mu = mu;
        this.serviceType = serviceType;
        this.sigma = sigma;
    }

    public double getMu() {
        return mu;
    }

    public double getSigma() {
        return sigma;
    }

    public String getServiceType() {
        return serviceType;
    }
}
