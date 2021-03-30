package kmeans;
import java.util.ArrayList;
import java.util.List;

public class Cluster {

    //pontos deste grupo
    private List<Ponto> Pontos;
    //centroid deste grupo
    private Ponto centroid;
    private int id;

    public Cluster(int id) {
        this.id = id;
        this.Pontos = new ArrayList<>();
        this.centroid = null;
    }

    public List<Ponto> getPontos() {
        return Pontos;
    }

    public void addPonto(Ponto Ponto) {
        Pontos.add(Ponto);
    }

    public void setPontos(List<Ponto> Pontos) {
        this.Pontos = Pontos;
    }

    public Ponto getCentroid() {
        return centroid;
    }

    public void setCentroid(Ponto centroid) {
        this.centroid = centroid;
    }

    public int getId() {
        return id;
    }

    public void limpar() {
        Pontos.clear();
    }

    //Listar pontos do grupo
    public void listarCluster() {
        System.out.println("[Cluster: " + id + "]");
        System.out.println("[Centroid: " + centroid + "]");
        System.out.println("[Pontos: \n");
        for (Ponto p : Pontos) {
            System.out.println(p.toString());
        }
        System.out.println("]");
    }

    @Override
    public String toString() {
        return "Cluster [centroid=" + centroid + ", id=" + id + "]";
    }

}