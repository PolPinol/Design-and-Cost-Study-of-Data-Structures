package Model.Graf;

/**
 * Classe que implementa la distància que usem per calcular correctament dins els Grafs
 */
public class Distancia {
    private int origen;
    private int desti;
    private float distancia;

    public Distancia(String origen, String desti, String distancia) {
        this.origen = Integer.parseInt(origen);
        this.desti = Integer.parseInt(desti);
        this.distancia = Float.parseFloat(distancia);
    }

    public float getDistancia() {
        return distancia;
    }

    public int getDesti() {
        return desti;
    }

    public int getOrigen() {
        return origen;
    }
}