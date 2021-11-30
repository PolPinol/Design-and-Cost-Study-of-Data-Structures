package Model.Graf;

import Model.Graf.Graf;
import Model.Graf.Node;

import java.io.*;
import java.util.*;

/**
 * Classe que gestiona totes les crides relacionades amb Grafs
 */
public class Programa {
    private Distancia[] distancies;
    private Node[] nodes;
    private int numNodes;
    private Node nodeDesti;
    private Graf graf;

    public Programa() {
        ArrayList<String> text = llegirFitxer("Graphs/graphXXS.paed");

        creaNodes(text);
        creaDistancies(text);

        this.graf = new Graf(nodes, distancies);
    }

    /**
     * Realitza la cerca usant MST
     * @return mst: Estat final del graf
     */
    public Graf MST() {
        int bandera = 0;
        int numArestes = 0;
        Graf mst = new Graf();

        Arrays.sort(distancies, Comparator.comparing(Distancia::getDistancia));

        bandera = 0;
        Graf mstCopia;
        while (!isSpanningTree(mst, numArestes)) {
            Node nodeOrigen;
            Node nodeDesti;

            mstCopia = new Graf(mst);

            nodeOrigen = retornaNode(distancies[bandera].getOrigen());
            nodeDesti = retornaNode(distancies[bandera].getDesti());

            mstCopia.afegirNode(nodeOrigen);
            mstCopia.afegirNode(nodeDesti);
            mstCopia.afegirAdjacent(nodeOrigen, nodeDesti);
            mstCopia.afegirAdjacent(nodeDesti, nodeOrigen);

            if (!mst.containsNode(nodeOrigen) || !mst.containsNode(nodeDesti) || gDescon(mst, mstCopia)) {
                mst.afegirNode(nodeOrigen);
                mst.afegirNode(nodeDesti);
                mst.afegirAdjacent(nodeOrigen, nodeDesti);
                mst.afegirAdjacent(nodeDesti, nodeOrigen);
                numArestes++;
            }

            bandera++;
        }

        return mst;
    }

    /**
     * Mira si un node està desconnectat
     * @param mst: graf en el qual estem mirant
     * @param mstCopia: còpia generada per a poder testejar
     * @return boolean: Indica si està desconnectat o no
     */
    private boolean gDescon(Graf mst, Graf mstCopia) {
        ArrayList<Node> nodesMst = mst.getNodes();
        Node node;
        for (int i = 0; i < nodesMst.size(); i++) {
            node = nodesMst.get(i);
            if (!sameConnected(node, mst, mstCopia)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comprova si està connectat al mateix node o no
     * @param node: node en qüestió
     * @param mst: estat del graf
     * @param mstCopia: còpia del graf
     * @return boolean: indica si està connectat al mateix
     */
    private boolean sameConnected(Node node, Graf mst, Graf mstCopia) {
        ArrayList<Node> nodesConnectatsMst = new ArrayList<>();
        ArrayList<Node> nodesConnectatsMstCopia = new ArrayList<>();

        retornaGrafConnectatNodeInicial(mst, node, nodesConnectatsMst);
        retornaGrafConnectatNodeInicial(mstCopia, node, nodesConnectatsMstCopia);

        return nodesConnectatsMst.size() == nodesConnectatsMstCopia.size();
    }

    /**
     * Comprova si és un spanning tree
     * @param mst: estat del graf
     * @param numArestes: numero d'arestes
     * @return boolean: indica si és un spanning tree
     */
    private boolean isSpanningTree(Graf mst, int numArestes) {
        if (mst.quantitatNodes() == graf.quantitatNodes()) {
            return numArestes == (graf.quantitatNodes() - 1);
        }

        return false;
    }

    /**
     * Retorna un graf connectat a partir del node especificat com a inicial
     * @param mst: estat actual del graf
     * @param nodeFixat: node del que partim
     * @param nodesConnectats: nodes connectats a dit node inicial
     */
    private void retornaGrafConnectatNodeInicial(Graf mst, Node nodeFixat, ArrayList<Node> nodesConnectats) {
        ArrayList<Node> adjs = mst.getAdjacents(nodeFixat);

        if (nodesConnectats.contains(nodeFixat)) {
            return;
        }

        if (!nodesConnectats.contains(nodeFixat)) {
            nodesConnectats.add(nodeFixat);
        }

        for (int i = 0; i < adjs.size(); i++) {
            retornaGrafConnectatNodeInicial(mst, adjs.get(i), nodesConnectats);
        }
    }

    public void creaIdsCorrectes() {
        Arrays.sort(nodes, Comparator.comparing(Node::getId));
        for (int i = 0; i < numNodes; i++) {
            nodes[i].setIdResumit(i);
        }
    }

    /**
     * Fa el càlcul de una ruta òptima mitjançant Dijkstra
     * @param nodeOrigenId: id del node d'origen
     * @param nodeDestiId: id del node destí
     * @return ID dels nodes pels quals passa
     */
    public ArrayList<Integer> dijkstra(int nodeOrigenId, int nodeDestiId) {
        this.nodeDesti = retornaNode(nodeDestiId);
        Node nodeOrigen = retornaNode(nodeOrigenId);
        ArrayList<Integer> cami = new ArrayList<>();
        ArrayList<Integer> ruta = new ArrayList<>();
        float nova = 0;
        float distanciaActualFinsAdj = 0;
        int indexActual = 0;
        int indexAdj = 0;
        Node actual = nodeOrigen;

        // Farem que distanciesCami estigui sincronitzat amb nodes
        // Per tant, distanciesCami[i] sera la distancia de Node Origen a node[i]
        float[] distanciesCami = inicialitzarDistanciesCami(nodeOrigen);

        // Afegim el node origen al cami
        cami.add(nodeOrigenId);

        // Mentres NO Final i NO visitats tots nodes
        while ((ruta.size() != nodes.length) && !(ruta.contains(nodeDesti.getId()))) {

            // Agafem tots els Nodes Adjacents del Node Actual
            ArrayList<Node> adjs = graf.getAdjacents(actual);

            // Per cada Adjacent fem
            for (int i = 0; i < adjs.size(); i++) {

                // Si Adjacent Afagat es NO visitat
                if (!ruta.contains(adjs.get(i).getId())) {

                    // Distancia del Node Actual fins Node Adjacent
                    distanciaActualFinsAdj = retornaDistancia(actual, adjs.get(i));

                    // Busquem el index del Array de distanciesCami pel NodeActual i NodeAdj
                    indexActual = returnIndexArrayNodes(actual);
                    indexAdj = returnIndexArrayNodes(adjs.get(i));

                    // Calculem nova possible distancia
                    nova = distanciesCami[indexActual] + distanciaActualFinsAdj;

                    // Comprovem si ens interessa mes aquesta nova distancia
                    if (distanciesCami[indexAdj] > nova) {
                        distanciesCami[indexAdj] = nova;
                        actualitzaCami(cami, actual.getId());
                    }
                }
            }
            ruta.add(actual.getId());
            actual = retornaNodeAdjMenorPes(actual, adjs, ruta);
        }

        // Afegim el node desti al cami
        actualitzaCami(cami, nodeDestiId);

        return cami;
    }

    private void actualitzaCami(ArrayList<Integer> cami, int insertar) {
        if (!cami.contains(insertar)) {
            cami.add(insertar);
        }
    }

    /**
     * Retorna el node adjacent al node especificat que tingui menor pes
     * @param actual: node on ens trobem
     * @param adjs: nodes adjacents
     * @param ruta: estat de la ruta fins ara
     * @return node que compleix la condició
     */
    private Node retornaNodeAdjMenorPes(Node actual, ArrayList<Node> adjs, ArrayList<Integer> ruta) {
        float minimaDistancia = Float.MAX_VALUE;
        Node adj;
        Node minimNode = null;
        float distanciaActualAdj;

        for (int i = 0; i < adjs.size(); i++) {
            adj = adjs.get(i);
            distanciaActualAdj = retornaDistancia(actual, adj);

            if (!ruta.contains(adjs.get(i).getId())) {
                if (minimaDistancia >= distanciaActualAdj) {
                    minimNode = adj;
                    minimaDistancia = distanciaActualAdj;
                }
            }
        }

        return minimNode;
    }

    private int returnIndexArrayNodes(Node node) {
        for (int i = 0; i < nodes.length; i++) {
            if (node.getId() == nodes[i].getId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Inicialitza les distàncies dels possibles camins
     * @param nodeOrigen: node d'origen
     * @return: distàncies generades
     */
    private float[] inicialitzarDistanciesCami(Node nodeOrigen) {
        float[] distanciesCami = new float[nodes.length];

        Arrays.fill(distanciesCami, Float.MAX_VALUE);

        ArrayList<Node> adjs = graf.getAdjacents(nodeOrigen);

        for (int i = 0; i < adjs.size(); i++) {
            int posicio = returnIndexArrayNodes(adjs.get(i));
                distanciesCami[posicio] = retornaDistancia(nodeOrigen, adjs.get(i));
        }

        int index = returnIndexArrayNodes(nodeOrigen);
        distanciesCami[index] = 0;

        return distanciesCami;
    }

    /**
     * Retorna la distància entre dos nodes
     * @param node1: primer node
     * @param node2: segon node
     * @return float: valor de la distància entre aquests
     */
    private float retornaDistancia(Node node1, Node node2) {
        Node nodeOrigenLocal;
        Node nodeDestiLocal;

        for (int i = 0; i < distancies.length; i++) {
            nodeOrigenLocal = retornaNode(distancies[i].getOrigen());
            nodeDestiLocal = retornaNode(distancies[i].getDesti());

            if (nodeOrigenLocal == node1 && nodeDestiLocal == node2) {
                if(node2 != nodeDesti && node2.getTipus() == Node.DANGER){
                    return distancies[i].getDistancia() * 10;
                } else {
                    return distancies[i].getDistancia();
                }
            } else if (nodeDestiLocal == node1 && nodeOrigenLocal == node2) {
                if(node2 != nodeDesti && node2.getTipus() == Node.DANGER){
                    return distancies[i].getDistancia() * 10;
                } else {
                    return distancies[i].getDistancia();
                }
            }
        }

        return -1;
    }

    private Node retornaNode(int id) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getId() == id) {
                return nodes[i];
            }
        }

        return null;
    }

    /**
     * Genera una ruta usant BFS
     * @param identificador: id del node especificat
     * @return ruta a seguir
     */
    public ArrayList<Node> BFS(int identificador) {
        ArrayList<Node> cua = new ArrayList<>();
        ArrayList<Node> dangers = new ArrayList<>();
        ArrayList<Node> ruta = new ArrayList<>();
        Node seg;
        ArrayList<Node> danger = new ArrayList<>();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getId() == identificador) {
                cua.add(nodes[i]);
                ruta.add(nodes[i]);
                if (nodes[i].getTipus() == Node.DANGER) {
                    dangers.add(nodes[i]);
                }

                while (!cua.isEmpty()) {
                    seg = cua.get(0);
                    cua.remove(0);
                    ArrayList<Node> adj = graf.getAdjacents(seg);
                    if (adj != null) {
                        for (int j = 0; j < adj.size(); j++) {
                            if (!ruta.contains(adj.get(j))) {
                                cua.add(adj.get(j));
                                ruta.add(adj.get(j));
                                if (!dangers.contains(adj.get(j))) {
                                    if (adj.get(j).getTipus() == Node.DANGER) {
                                        dangers.add(adj.get(j));
                                    }
                                }
                            }
                        }
                    }
                }

                for (int j = 0; j < dangers.size(); j++) {
                    danger.add(dangers.get(j));
                }

                break;
            }
        }

        return danger;
    }

    /**
     * Realitza la cerca usant DFS
     * @param identificador: node inicial especificat
     * @return ruta a seguir obtinguda
     */
    public ArrayList<Node> DFS(int identificador) {
        ArrayList<Node> nodeDFS = new ArrayList<>();

        // Buscar el node del Id entrat i inicialitzar llistes
        for (int i = 0; i < nodes.length; i++) {
            ArrayList<Node> ruta = new ArrayList<>();
            ArrayList<Node> interests = new ArrayList<>();
            if (nodes[i].getId() == identificador) {
                interests = DFS(graf, nodes[i], ruta, interests);

                for (int j = 0; j < interests.size(); j++) {
                    //nodeId.add(interests.get(j).getId());
                    nodeDFS.add(interests.get(j));
                }
                break;
            }
        }

        return nodeDFS;
    }

    /**
     * Realitza la lògica del DFS
     * @param g: estat del graf
     * @param node: node on ens trobem
     * @param ruta: estat de la ruta
     * @param interests: nodes que són punts d'interès
     * @return: interests: nodes que són punts d'interès
     */
    private ArrayList<Node> DFS(Graf g, Node node, ArrayList<Node> ruta, ArrayList<Node> interests) {
        if (!ruta.contains(node)) {
            node.marcar();
            ruta.add(node);
            if (!interests.contains(node)) {
                interests.add(node);
            }
            ArrayList<Node> adj = g.getAdjacents(node);
            if (adj != null) {
                for (int i = 0; i < adj.size(); i++) {
                    if (!adj.get(i).isVisitat()) {
                        interests = DFS(g, adj.get(i), ruta, interests);
                    }
                }
            }
            node.desmarcar();
        }
        return interests;
    }

    /**
     * Crea totes les distàncies un cop extreta la informació del fitxer .paed
     * @param text: informació extreta del fitxer
     */
    private void creaDistancies(ArrayList<String> text) {
        int tamanyDistancies = Integer.parseInt(text.get(Integer.parseInt(text.get(0)) + 1));
        int liniaText = Integer.parseInt(text.get(0)) + 2;

        distancies = new Distancia[tamanyDistancies];
        for (int i = 0; i < tamanyDistancies; i++) {
            String[] parts = text.get(liniaText + i).split(",");
            distancies[i] = new Distancia(parts[0], parts[1], parts[2]);
        }
    }

    /**
     * Crea tots els nodes un cop extreta la informació del fitxer .paed
     * @param text: informació extreta del fitxer
     */
    private void creaNodes(ArrayList<String> text) {
        numNodes = Integer.parseInt(text.get(0));
        nodes = new Node[numNodes];
        for (int i = 1; i <= numNodes; i++) {
            String[] parts = text.get(i).split(",");
            nodes[i - 1] = new Node(parts[0], parts[1], parts[2]);
        }
    }

    /**
     * Llegeix el fitxer .paed i n'extreu la informació necessària per a poder crear el Graf
     * @param filePath: path que condueix al fitxer en qüestió
     * @return text: tota la informació extreta del fitxer
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
}