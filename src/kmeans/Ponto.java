package kmeans;
import java.util.Random;

public class Ponto {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    private double r;
    private double g;
    private double b;
    private Cluster cluster;

    public Ponto(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    protected static double distancia(Ponto p, Ponto centroid) {
        return Math.sqrt(Math.pow(p.getR() - centroid.getR(), 2) 
                        + Math.pow(p.getG() - centroid.getG(), 2)
                        + Math.pow(p.getB() - centroid.getB(), 2));
    }

    protected static Ponto createRandomPonto() {
        double r = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * new Random().nextDouble();
        double g = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * new Random().nextDouble();
        double b = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * new Random().nextDouble();
        return new Ponto(r, g, b);
    }

    @Override
    public String toString() {
        return "Ponto [r=" + r + ", g=" + g + ", b=" + b + "], cluster=" + (cluster == null ? "" : cluster.toString());
    }

}