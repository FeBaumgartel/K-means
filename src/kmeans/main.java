package kmeans;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class main {
    public static final Logger LOGGER = Logger.getLogger(main.class.getName());

    //Pontos a serem agrupados
    private static final int PONTOS_PARA_GERAR[][] = { { 1, 10, 200, 1 }, { 2, 20, 230, 1 }, { 6, 25, 150, 1 },
            { 7, 45, 100, 1 }, { 10, 50, 125, 1 }, { 3, 24, 111, 1 }, { 100, 4, 10, 2 }, { 250, 7, 50, 2 },
            { 243, 5, 68, 2 }, { 210, 2, 90, 2 }, { 200, 1, 95, 2 }, { 215, 0, 68, 2 }, { 56, 200, 1, 3 },
            { 79, 234, 3, 3 }, { 80, 210, 8, 3 }, { 95, 200, 10, 3 }, { 80, 210, 4, 3 }, { 49, 207, 1, 3 } };

    //Numero de grupos 
    private static final int NUM_CLUSTERS = 3;
    //Lista de pontos
    private List<Ponto> Pontos;
    //Lista de grupos
    private List<Cluster> clusters;

    //Inicializando listas
    public main() {
        LOGGER.info("-=-=-=-=-=-=-=-=-= INICIALIZANDO");
        this.Pontos = new ArrayList<>();
        this.clusters = new ArrayList<>();
    }

    public static void main(String[] args) {
        main main = new main();
        main.init();
        main.calcular();
    }

    public void init() {
        //Transformando pontos em List<Ponto>
        for (int i = 0; i < PONTOS_PARA_GERAR.length; i++) {
            Ponto Ponto = new Ponto(PONTOS_PARA_GERAR[i][0], PONTOS_PARA_GERAR[i][1], PONTOS_PARA_GERAR[i][2]);
            this.Pontos.add(Ponto);
        }

        //Gerando Lista de centroids e definindo um para cada grupo
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster cluster = new Cluster(i);
            Ponto centroid = Ponto.createRandomPonto();
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        listarClusters();
    }

    //Lista os grupos
    private void listarClusters() {
        LOGGER.info("\n\nCLUSTERS:");
        for (int i = 0; i < NUM_CLUSTERS; i++) {
            Cluster c = clusters.get(i);
            c.listarCluster();
        }
    }

    //Calculo do algoritmo
    public void calcular() {
        int iteracao = 0;

        while (true) {
            iteracao++;
            limparClusters();
            List<Ponto> ultimosCentroids = getCentroids();

            atribuirCluster();
            calcularCentroids();

            List<Ponto> centroidsAtuais = getCentroids();

            //calcula a distancia de movimentacao dos centroids antes e depois do calculo
            double distancia = 0;
            for (int i = 0; i < ultimosCentroids.size(); i++) {
                distancia += Ponto.distancia(ultimosCentroids.get(i), centroidsAtuais.get(i));
            }

            LOGGER.info("################# Iteração: ".concat(String.valueOf(iteracao)));

            //Se os centroids se moveram menos de 0.1, para o loop infinito 
            if (distancia < 0.1)
                break;
        }
        //Informa o resultado
        listarClusters();
    }

    //Limpa todos os pontos do grupo
    private void limparClusters() {
        for (Cluster cluster : clusters) {
            cluster.limpar();
        }
    }

    //Obtem os centroids dos grupos
    private List<Ponto> getCentroids() {
        List<Ponto> centroids = new ArrayList<>();
        for (Cluster cluster : clusters) {
            Ponto aux = cluster.getCentroid();
            centroids.add(new Ponto(aux.getR(), aux.getG(), aux.getB()));
        }
        return centroids;
    }

    //Seta o ponto a um grupo e o grupo para o ponto
    private void atribuirCluster() {
        double max = Double.MAX_VALUE;
        double min;
        double distancia;
        int clusterIndex = 0;

        for (Ponto Ponto : Pontos) {
            min = max;
            for (int i = 0; i < NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);

                StringBuilder builder = new StringBuilder();
                builder.append("Distancia de Ponto ");
                builder.append(Ponto.toString());
                builder.append(" até Ponto ");
                builder.append(c.getCentroid().toString());
                builder.append(" é: ");
                builder.append(Ponto.distancia(Ponto, c.getCentroid()));
                System.out.println(builder.toString());

                //Calcula a distancia de cada ponto com cada centroid
                distancia = Ponto.distancia(Ponto, c.getCentroid());
                //salva a menor distancia e o id do grupo
                if (distancia < min) {
                    min = distancia;
                    clusterIndex = i;
                }
            }

            Ponto.setCluster(clusters.get(clusterIndex));
            clusters.get(clusterIndex).addPonto(Ponto);
        }
    }

    //Move o centroid para a posição media de R,G e B dos pontos do grupo
    private void calcularCentroids() {
        for (Cluster cluster : clusters) {
            double sumR = 0;
            double sumG = 0;
            double sumB = 0;
            List<Ponto> list = cluster.getPontos();
            int PontosSize = list.size();

            for (Ponto Ponto : list) {
                sumR += Ponto.getR();
                sumG += Ponto.getG();
                sumB += Ponto.getB();
            }

            if (PontosSize > 0) {
                cluster.getCentroid().setR(sumR / PontosSize);
                cluster.getCentroid().setG(sumG / PontosSize);
                cluster.getCentroid().setB(sumB / PontosSize);
            }
        }
    }
}