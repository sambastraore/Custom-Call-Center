package sn.ept.dic2git.stoch.entities;

public class Tuple2 {
    private final double mu;
    private final double sigma;

    public Tuple2(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    public double getMu() {
        return mu;
    }

    public double getSigma() {
        return sigma;
    }

}
