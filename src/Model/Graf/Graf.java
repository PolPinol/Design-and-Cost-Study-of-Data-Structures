package Model.Graf;

import java.util.*;

/**
 * Classe que implementa els Grafs i les seves respectives funcions i procediments
 */
public class Graf implements Cloneable{
    private HashMap<Node, ArrayList<Node>> llistaAdjacencia;

    public Graf(Node [] nodes, Distancia[] distancias) {
        llistaAdjacencia = new HashMap<>();
        creaGraf(nodes, distancias);
    }

    public Graf() {
        llistaAdjacencia = new HashMap<>();
    }

    /**
     * Crea un nou Graf
     * @param nodes: nodes que contindrà el graf
     * @param distancies: distàncies entre aquests
     */
    public void creaGraf(Node[] nodes, Distancia[] distancies) {
        Node origen = null;
        Node desti = null;
        for (int i = 0; i < distancies.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                if (nodes[j].getId() == distancies[i].getOrigen()) {
                    origen = nodes[j];
                    break;
                }
            }

            for (int j = 0; j < nodes.length; j++) {
                if (nodes[j].getId() == distancies[i].getDesti()) {
                    desti = nodes[j];
                    break;
                }
            }

            afegirAdjacent(origen, desti);
            afegirAdjacent(desti, origen);
        }

    }

    public ArrayList<Node> getAdjacents(Node node) {
        return llistaAdjacencia.get(node);
    }

    public ArrayList<Node> getNodes() {
        return new ArrayList<>(llistaAdjacencia.keySet());
    }

    public int quantitatNodes() {
        return new ArrayList<>(llistaAdjacencia.keySet()).size();
    }

    public boolean containsNode(Node node) {
        return getNodes().contains(node);
    }

    /**
     * Afegeix un adjacent
     * @param origen: punt d'origen
     * @param adj: node adjacent a afegir
     */
    public void afegirAdjacent(Node origen, Node adj) {
        if (!llistaAdjacencia.containsKey(origen)) {
            ArrayList<Node> nodesAdjacents = new ArrayList<>();
            nodesAdjacents.add(adj);
            llistaAdjacencia.put(origen, nodesAdjacents);
        } else {
            ArrayList<Node> nodesAdjacents = llistaAdjacencia.get(origen);
            if (!nodesAdjacents.contains(adj)) {
                nodesAdjacents.add(adj);
                llistaAdjacencia.remove(origen);
                llistaAdjacencia.put(origen, nodesAdjacents);
            }
        }
    }

    public void afegirNode(Node node) {
        if (!llistaAdjacencia.containsKey(node)) {
            llistaAdjacencia.put(node, new ArrayList<>());
        }
    }

    /**
     * Mostra tots els ID del graf
     * @return ids: hashmap amb tots els ID i el seu contingut
     */
    public HashMap<Integer, ArrayList<Integer>> mostrarGrafIDs(){
        HashMap<Integer, ArrayList<Integer>> ids = new HashMap<>();
        ArrayList<Node> nodes = getNodes();
        ArrayList<Integer> ints;
        ArrayList<Node> adjacents;

        for (int i = 0; i < nodes.size(); i++) {
            adjacents = getAdjacents(nodes.get(i));
            ints = new ArrayList<>();
            for (int j = 0; j < adjacents.size(); j++) {
                ints.add(adjacents.get(j).getId());
            }
            ids.put(nodes.get(i).getId(), ints);
        }
        return ids;
    }

    public Graf(Graf g) {
        llistaAdjacencia = new HashMap<>();
        ArrayList<Node> adjs;
        ArrayList<Node> adjsCopia = new ArrayList<>();
        for (int i = 0; i < g.getNodes().size(); i++) {
            adjs = g.getAdjacents(g.getNodes().get(i));
            adjsCopia = (ArrayList<Node>) adjs.clone();
            llistaAdjacencia.put(g.getNodes().get(i), adjsCopia);
        }
    }
}