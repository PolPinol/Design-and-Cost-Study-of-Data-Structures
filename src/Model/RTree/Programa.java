package Model.RTree;

import Entities.Point;
import Entities.Rectangle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que gestiona les crides dels procediments i funcions referents a arbres recursius
 */
public class Programa {
    private int numNodes;
    private Point[] points;
    private Arbre arbre;

    public Programa() {
        ArrayList<String> text = llegirFitxer("RTrees/r-treeXXS.paed");

        llegeixNodes(text);
        creaArbre();
    }

    /**
     * Insereix un node dins l'arbre
     * @param text: Informació del node a generar
     */
    public void inserir(String[] text) {
        String nom = text[0];
        double x = Double.parseDouble(text[1]);
        double y = Double.parseDouble(text[2]);
        Point point;

        point = new Point(nom, x, y);
        arbre.inserir(point);

    }

    public boolean eliminar(String nom) {
        return arbre.eliminarNode(nom);
    }

    /**
     * Funcio que crea un R-Tree
     */
    private void creaArbre() {
        this.arbre = new Arbre();

        arbre.primerPunt(points[0]);
        for (int i = 1; i < points.length; i++) {
            arbre.inserir(points[i]);
        }
    }

    /**
     * Lectura inicial dels nodes del fitxer .paed
     * @param text: Informació llegida del fitxer
     */
    private void llegeixNodes(ArrayList<String> text) {
        double x, y;
        numNodes = Integer.parseInt(text.get(0));
        points = new Point[numNodes];
        for (int i = 1; i <= numNodes; i++) {
            String[] parts = text.get(i).split(",");
            x = Double.parseDouble(parts[1]);
            y = Double.parseDouble(parts[2]);

            points[i - 1] = new Point(parts[0], x, y);
        }
    }

    /**
     * Lectura inicial del fitxer .paed
     * @param filePath: path del fitxer en qüestió
     * @return text: informació obtinguda del fitxer
     */
    private ArrayList<String> llegirFitxer(String filePath) {
        File archivo;
        FileReader fr;
        BufferedReader br;
        ArrayList<String> text = new ArrayList<>();

        try {
            archivo = new File (filePath);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linia;
            while((linia = br.readLine()) != null) {
                text.add(linia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public void recorreArbre() {
        arbre.recorreNivells();
    }

    public ArrayList<Point> getPunts() {
        return arbre.getPunts();
    }

    public ArrayList<Rectangle> getRectangles() {
        return arbre.getRectangles();
    }

    /**
     * Realitza una cerca per àrea dels nodes de l'arbre
     * @param punt1: primer punt que determina l'àrea
     * @param punt2: segon punt que delimita l'àrea
     * @return points: tots els punts trobats dins aquesta àrea
     */
    public ArrayList<Point> cercaArea(String punt1, String punt2) {
        String[] parts1 = punt1.split(",");
        double x1 = Double.parseDouble(parts1[0]);
        double y1 = Double.parseDouble(parts1[1]);

        String[] parts2 = punt2.split(",");
        double x2 = Double.parseDouble(parts2[0]);
        double y2 = Double.parseDouble(parts2[1]);
        return arbre.buscarPerArea(x1, y1, x2, y2);
    }

    /**
     * Realitza una cerca per proximitat
     * @param text: informació dels nodes a buscar
     * @return point[]: tots els punts que es troben ordenats per proximitat
     */
    public Point[] cercaProximitat(String[] text) {
        int numPunts = Integer.parseInt(text[0]);

        String[] parts = text[1].split(",");
        double x1 = Double.parseDouble(parts[0]);
        double y1 = Double.parseDouble(parts[1]);
        return arbre.buscarPerProximitat(numPunts, x1, y1);
    }
}