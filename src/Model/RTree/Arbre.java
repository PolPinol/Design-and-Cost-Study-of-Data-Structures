package Model.RTree;

import Entities.Point;
import Entities.Rectangle;

import java.util.ArrayList;

/**
 * Classe que implementa l'arbre recursiu i les seves respectives funcions i procediments
 */
public class Arbre {
    private Node arrel;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Point> punts;
    private ArrayList<Point> puntsArea;
    private Point[] puntsProximitat;
    private Double[] distanciesProximitat;

    public Arbre() {
        rectangles = new ArrayList<>();
        punts = new ArrayList<>();
    }

    /**
     * Fa les crides per a buscar per proximitat
     * @param numPoints: número de punts a buscar
     * @param x: coordenada x per buscar per proximitat
     * @param y: coordenada y per buscar per proximitat
     * @return
     */
    public Point[] buscarPerProximitat(int numPoints, double x, double y) {
        puntsProximitat = new Point[numPoints];
        distanciesProximitat = new Double[numPoints];
        for (int i = 0; i < numPoints; i++) {
            distanciesProximitat[i] = Double.MAX_VALUE;
        }
        buscarPerProximitat(this.arrel, x, y);
        return puntsProximitat;
    }

    /**
     * Busca tots els punts per proximitat
     * @param node: node a calcular la proximitat
     * @param x: coordenada x de proximitat
     * @param y: coordenada y de proximitat
     */
    private void buscarPerProximitat(Node node, double x, double y) {
        if (node.isNodeFulla()) {
            afegirPuntsProxim(node.getPointsArrayList(), x, y);
        } else {
            ArrayList<Node> fills = node.getFills();
            for (int i = 0; i < fills.size(); i++) {
                if (!tallarBranques(fills.get(i), x, y)) {
                    buscarPerProximitat(fills.get(i), x, y);
                }
            }
        }
    }

    /**
     * Metode per intentar no entrar a tants rectangles
     * @param node: Node en que ens trobem
     * @param x: coordenada x
     * @param y: coordenada y
     * @return boolean: Confirma si es pot retallar la branca
     */
    private boolean tallarBranques(Node node, double x, double y) {
        // Es calcula si les quatre cantonades (el minim de les 4) del rectangle estan mes llunyanes que tots els punts ja gurdats en l'array
        double minimDistanciaRectangle = node.calculaDistanciaMinima(x, y);
        for (int i = 0; i < distanciesProximitat.length; i++) {
            if (minimDistanciaRectangle < distanciesProximitat[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mètode que afegeix els punts si aquests son millors als que ja estan guardats a l'array
     * @param pointsArrayList: array de punts
     * @param x: coordenada x
     * @param y: coordenada y
     */
    private void afegirPuntsProxim(ArrayList<Point> pointsArrayList, double x, double y) {
        for (int i = 0; i < pointsArrayList.size(); i++) {
            Point p = pointsArrayList.get(i);
            double distanciaPosible = Math.sqrt((x - p.getX())*(x - p.getX()) + (y - p.getY())*(y - p.getY()));
            for (int j = 0; j < distanciesProximitat.length; j++) {
                if (distanciaPosible < distanciesProximitat[j]) {
                    double temp2;
                    double temp = distanciesProximitat[j];
                    Point pointTemp = puntsProximitat[j];
                    Point pointTemp2;
                    distanciesProximitat[j] = distanciaPosible;
                    puntsProximitat[j] = new Point(p.getName(), p.getX(), p.getY());
                    if (distanciesProximitat.length != j+1) {
                        if (puntsProximitat[j+1] != null) {
                            for (int k = j; k < distanciesProximitat.length-1; k++) {
                                temp2 = distanciesProximitat[k+1];
                                distanciesProximitat[k+1] = temp;
                                temp = temp2;
                                if (puntsProximitat[k+1] != null && pointTemp != null) {
                                    pointTemp2 = new Point(puntsProximitat[k+1].getName(), puntsProximitat[k+1].getX(), puntsProximitat[k+1].getY());
                                    puntsProximitat[k+1] = new Point(pointTemp.getName(), pointTemp.getX(), pointTemp.getY());
                                    pointTemp = pointTemp2;
                                }
                            }
                        } else if (pointTemp != null) {
                            distanciesProximitat[j+1] = temp;
                            puntsProximitat[j+1] = new Point(pointTemp.getName(), pointTemp.getX(), pointTemp.getY());
                        }
                    }
                    break;
                }
            }
        }
    }


    /**
     * Fa la crida de buscar per àrea
     * @param x1: coordenada x del primer punt que delimita la àrea
     * @param y1: coordenada y del primer punt que delimita la àrea
     * @param x2: coordenada x del segon punt que delimita la àrea
     * @param y2: coordenada y del segon punt que delimita la àrea
     * @return points[]: punts trobats per àrea
     */
    public ArrayList<Point> buscarPerArea(double x1, double y1, double x2, double y2) {
        puntsArea = new ArrayList<>();
        buscarPerArea(this.arrel, x1, y1, x2, y2);
        return puntsArea;
    }

    /**
     * Busca els punts que es troben dins una àrea
     * @param node: node en el que ens trobem
     * @param x1: coordenada x del primer punt que delimita la àrea
     * @param y1: coordenada y del primer punt que delimita la àrea
     * @param x2: coordenada x del segon punt que delimita la àrea
     * @param y2: coordenada y del segon punt que delimita la àrea
     */
    private void buscarPerArea(Node node, double x1, double y1, double x2, double y2) {
        if (node.isNodeFulla()) {
            afegirPuntsDinsSeleccio(node.getPointsArrayList(), x1, y1, x2, y2);
        } else {
            ArrayList<Node> fills = node.getFills();
            for (int i = 0; i < fills.size(); i++) {
                if (fills.get(i).intersects(x1, y1, x2, y2)) {
                    buscarPerArea(fills.get(i), x1, y1, x2, y2);
                }
            }
        }
    }

    /**
     * Afegeix els punts dins la selecció obtinguda
     * @param pointsArrayList: selecció de punts
     * @param x1: coordenada x del primer punt que delimita la àrea
     * @param y1: coordenada y del primer punt que delimita la àrea
     * @param x2: coordenada x del segon punt que delimita la àrea
     * @param y2: coordenada y del segon punt que delimita la àrea
     */
    private void afegirPuntsDinsSeleccio(ArrayList<Point> pointsArrayList, double x1, double y1, double x2, double y2) {
        for (int i = 0; i < pointsArrayList.size(); i++) {
            Point p = pointsArrayList.get(i);
            if (x1 <= p.getX() && p.getX() <= x2 && y1 <= p.getY() && p.getY() <= y2) {
                puntsArea.add(p);
            }
        }
    }

    public void primerPunt(Point p) {
        this.arrel = new Node(p);
    }

    /**
     * Insereix un punt
     * @param point: punt a inserir
     */
    public void inserir(Point point) {
        if (!this.arrel.isNodeFulla()) {
            Point p1 = this.arrel.getPoints()[0];
            Point p2 = this.arrel.getPoints()[1];
            this.arrel.setPoints(arrel.getPointsRectangle(p1, p2, point));
        }
        inserir(this.arrel, point);
    }

    /**
     * Insereix un punt dins un rectangle
     * @param node: rectangle on s'insereix
     * @param point: punt a inserir
     */
    private void inserir(Node node, Point point) {
        if (node.isNodeFulla()) {
            node.addPoint(point);
            if (node.askOverflow()) {
                adjustOverflow(node);
            }
        } else {
            // Actualitzar valors rectangle
            Point p1 = node.getPoints()[0];
            Point p2 = node.getPoints()[1];
            node.setPoints(node.getPointsRectangle(p1, p2, point));

            Node nodeFill = chooseSubTree(node, point);
            inserir(nodeFill, point);
        }
    }

    /**
     * Escull a quina branca inserim el punt
     * @param node: branca
     * @param point: punt a inserir
     * @return node generat
     */
    private Node chooseSubTree(Node node, Point point) {
        ArrayList<Node> fills = node.getFills();

        double area;
        double minArea = Double.MAX_VALUE;
        Node millorFill = null;

        for (int i = 0; i < fills.size(); i++) {
            area = fills.get(i).areaAddingPoint(point);
            if (minArea > area) {
                minArea = area;
                millorFill = fills.get(i);
            }
        }

        return millorFill;
    }

    /**
     * Ajusta el overflow
     * @param node: node on ens trobem
     */
    private void adjustOverflow(Node node) {
        Node newNodeSplitted;
        Node pareNode;
        Point p1;
        Point p2;

        if (node.isNodeFulla()) {
            p1 = node.getPointsRectangleNodeLeaf()[0];
            p2 = node.getPointsRectangleNodeLeaf()[1];
            newNodeSplitted = splitNodeFulla(node);
        } else {
            p1 = node.getPoints()[0];
            p2 = node.getPoints()[1];
            newNodeSplitted = split(node);
        }

        if (node == arrel) {
            //create a new root with node and nodeSplitted as its child nodes
            Node newArrel = new Node(p1, p2);
            newArrel.setNoFulla();
            newNodeSplitted.setNodePare(newArrel);
            node.setNodePare(newArrel);
            newArrel.addFills(newNodeSplitted);
            newArrel.addFills(node);
            this.arrel = newArrel;
        } else {
            pareNode = node.getNodePare();
            pareNode.addFills(newNodeSplitted);
            newNodeSplitted.setNodePare(pareNode);
            if (pareNode.askOverflow()) {
                adjustOverflow(pareNode);
            }
        }
    }

    /**
     * Split Leaf Node
     * @param node: node on ens trobem
     * @return node: estat final del node
     */
    private Node splitNodeFulla(Node node) {
        Point[] points = node.getPoints();

        double area1, area2;
        int comb = 0;
        double bestArea = Double.MAX_VALUE;

        // MILLOR SPLIT 1
        area1 = node.areaDosPuntos(points[0], points[1]);
        area2 = node.areaDosPuntos(points[2], points[3]);
        if (bestArea > (area1 + area2)) {
            bestArea = area1 + area2;
            comb = 0;
        }

        // MILLOR SPLIT 2
        area1 = node.areaDosPuntos(points[0], points[2]);
        area2 = node.areaDosPuntos(points[1], points[3]);
        if (bestArea > (area1 + area2)) {
            bestArea = area1 + area2;
            comb = 1;
        }

        // MILLOR SPLIT 3
        area1 = node.areaDosPuntos(points[0], points[3]);
        area2 = node.areaDosPuntos(points[1], points[2]);
        if (bestArea > (area1 + area2)) {
            bestArea = area1 + area2;
            comb = 2;
        }

        // MILLOR SPLIT 4
        area1 = node.areaTresPuntos(points[0], points[1], points[2]);
        if (bestArea > area1) {
            bestArea = area1;
            comb = 3;
        }

        // MILLOR SPLIT 5
        area1 = node.areaTresPuntos(points[0], points[1], points[3]);
        if (bestArea > area1) {
            bestArea = area1;
            comb = 4;
        }

        // MILLOR SPLIT 6
        area1 = node.areaTresPuntos(points[0], points[2], points[3]);
        if (bestArea > area1) {
            comb = 5;
        }

        // MILLOR SPLIT 7
        area1 = node.areaTresPuntos(points[1], points[2], points[3]);
        if (bestArea > area1) {
            comb = 6;
        }


        // FER SPLIT
        if (comb == 0) {
            node.setNewPoints(points[0], points[1], null);
            return new Node(points[2], points[3]);
        } else if (comb == 1) {
            node.setNewPoints(points[0], points[2], null);
            return new Node(points[1], points[3]);
        } else if (comb == 2) {
            node.setNewPoints(points[0], points[3], null);
            return new Node(points[1], points[2]);
        } else if (comb == 3) {
            node.setNewPoints(points[0], points[1], points[2]);
            return new Node(points[3]);
        } else if (comb == 4) {
            node.setNewPoints(points[0], points[1], points[3]);
            return new Node(points[2]);
        } else if (comb == 5) {
            node.setNewPoints(points[0], points[2], points[3]);
            return new Node(points[1]);
        } else {
            node.setNewPoints(points[1], points[2], points[3]);
            return new Node(points[0]);
        }
    }

    /**
     * Split NonLeaf Node
     * @param node: node on ens trobem
     * @return estat final del node
     */
    private Node split(Node node) {
        Node[] fills = node.getFillsArray();
        Point[] points;
        Node nodeSplitted;
        double area1, area2;
        int comb = 0;
        double bestArea = Double.MAX_VALUE;

        // MILLOR SPLIT 1
        area1 = fills[0].areaAddingNode(fills[1]);
        area2 = fills[2].areaAddingNode(fills[3]);
        if (bestArea > (area1 + area2)) {
            bestArea = area1 + area2;
            comb = 0;
        }

        // MILLOR SPLIT 2
        area1 = fills[0].areaAddingNode(fills[2]);
        area2 = fills[1].areaAddingNode(fills[3]);
        if (bestArea > (area1 + area2)) {
            bestArea = area1 + area2;
            comb = 1;
        }

        // MILLOR SPLIT 3
        area1 = fills[0].areaAddingNode(fills[3]);
        area2 = fills[1].areaAddingNode(fills[2]);
        if (bestArea > (area1 + area2)) {
            comb = 2;
        }

        // MILLOR SPLIT 4
        area1 = fills[0].areaAddingNode(fills[1], fills[2]);
        area2 = fills[3].area();
        if (bestArea > (area1 + area2)) {
            comb = 3;
        }

        // MILLOR SPLIT 5
        area1 = fills[0].areaAddingNode(fills[1], fills[3]);
        area2 = fills[2].area();
        if (bestArea > (area1 + area2)) {
            comb = 4;
        }

        // MILLOR SPLIT 6
        area1 = fills[0].areaAddingNode(fills[2], fills[3]);
        area2 = fills[1].area();
        if (bestArea > (area1 + area2)) {
            comb = 5;
        }

        // MILLOR SPLIT 7
        area1 = fills[1].areaAddingNode(fills[2], fills[3]);
        area2 = fills[0].area();
        if (bestArea > (area1 + area2)) {
            comb = 6;
        }


        // FER SPLIT
        if (comb == 0) {
            // Actualitza node amb menys fills i points canviats
            // actualiztar punts que defineixen node
            points = node.trobaPuntsRectangle(fills[1], fills[2]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[2]);
            node.removeFills(fills[3]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[2], fills[3]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[2].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[2]);
            fills[3].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[3]);
        } else if (comb == 1) {
            // Actualitza node amb menys fills i points canviats
            points = node.trobaPuntsRectangle(fills[0], fills[2]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[1]);
            node.removeFills(fills[3]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[1], fills[3]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[1].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[1]);
            fills[3].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[3]);
        } else if (comb == 2) {
            // Actualitza node amb menys fills i points canviats
            points = node.trobaPuntsRectangle(fills[0], fills[3]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[1]);
            node.removeFills(fills[2]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[1], fills[2]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[1].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[1]);
            fills[2].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[2]);
        } else if (comb == 3) {
            // Actualitza node amb menys fills i points canviats
            points = node.trobaPuntsRectangle(fills[0], fills[1], fills[2]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[3]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[3]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[3].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[3]);
        } else if (comb == 4) {
            // Actualitza node amb menys fills i points canviats
            points = node.trobaPuntsRectangle(fills[0], fills[1], fills[3]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[2]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[2]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[2].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[2]);
        } else if (comb == 5) {
            // Actualitza node amb menys fills i points canviats
            points = node.trobaPuntsRectangle(fills[0], fills[2], fills[3]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[1]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[1]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[1].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[1]);
        } else {
            // Actualitza node amb menys fills i points canviats
            points = node.trobaPuntsRectangle(fills[1], fills[2], fills[3]);
            node.setNewPoints(points[0], points[1], null);
            node.removeFills(fills[0]);

            // Crea nou node splitted de node original
            points = node.trobaPuntsRectangle(fills[0]);
            nodeSplitted = new Node(points[0], points[1]);
            nodeSplitted.setNoFulla();
            fills[0].setNodePare(nodeSplitted);
            nodeSplitted.addFills(fills[0]);
        }

        return nodeSplitted;
    }

    /**
     * Funcio que retorna l'arbre per nivells
     */
    public void recorreNivells() {
        rectangles = new ArrayList<>();
        punts = new ArrayList<>();
        ArrayList<Node> cua = new ArrayList<>();
        Node nou;

        cua.add(arrel);
        while (!cua.isEmpty()) {
            nou = cua.get(0);
            cua.remove(0);

            addNodePerImprimir(nou);
            cua.addAll(nou.getFills());
        }
    }

    /**
     * Afegeix un nou node per a imprimir a posteriori
     * @param nou: node nou en qüestió
     */
    private void addNodePerImprimir(Node nou) {
        if (nou.isNodeFulla()) {
            Point p1 = nou.getPoints()[0];
            Point p2 = nou.getPoints()[1];
            Point p3 = nou.getPoints()[2];
            punts.add(p1);
            if (p2 != null) {
                punts.add(p2);

                if (p3 != null) {
                    punts.add(p3);
                    rectangles.add(new Rectangle(nou.getPointsRectangle(p1, p2, p3)));
                } else {
                    rectangles.add(new Rectangle(nou.getPointsRectangle(p1, p2)));
                }
            }
        } else {
            rectangles.add(new Rectangle(nou.getPoints()[0], (nou.getPoints()[1])));
        }
    }

    public ArrayList<Point> getPunts() {
        return punts;
    }

    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }

    /**
     * Elimina un node
     * @param nom: nom del node a eliminar
     * @return boolean: indica si s'ha eliminat correctament
     */
    public boolean eliminarNode(String nom) {
        ArrayList<Node> cua = new ArrayList<>();
        Node nou;

        cua.add(arrel);
        while (!cua.isEmpty()) {
            nou = cua.get(0);
            cua.remove(0);

            if (nou.isNodeFulla()) {
                if (nou.getPoints()[0].getName().equals(nom)) {
                    eliminarPunt(nou, 0);
                    return true;
                } else if (nou.getPoints()[1] != null && nou.getPoints()[1].getName().equals(nom)) {
                    eliminarPunt(nou, 1);
                    return true;
                } else if (nou.getPoints()[2] != null && nou.getPoints()[2].getName().equals(nom)) {
                    eliminarPunt(nou, 2);
                    return true;
                }
            }
            cua.addAll(nou.getFills());
        }
        return false;
    }

    /**
     * Elimina un punt
     * @param node: node on es troba el punt
     * @param indexPunt: posició del punt dins el node
     */
    private void eliminarPunt(Node node, int indexPunt) {
        if (indexPunt == 2) {
            node.eliminarPunt(2);
            recalcularRectanglePare(node.getNodePare());
        } else if (indexPunt == 1) {
            node.eliminarPunt(1);
            recalcularRectanglePare(node.getNodePare());
        } else {
            if (node.getPoints()[1] == null && node.getPoints()[2] == null) {
                recalcularRectanglePare(removePare(node));
            } else {
                node.eliminarPunt(0);
                recalcularRectanglePare(node.getNodePare());
            }
        }
    }

    /**
     * Recalcula el rectangle pare després d'ajustar l'overflow
     * @param node: node pare
     */
    private void recalcularRectanglePare(Node node) {
        ArrayList<Point> fillsPunts = new ArrayList<>();
        for (int i = 0; i < node.getFills().size(); i++) {
            ArrayList<Point> punts = node.getFills().get(i).getPointsArrayList();
            fillsPunts.addAll(punts);
        }

        node.setPoints(node.trobaPuntsRectangle(fillsPunts));
        Node pare = node.getNodePare();
        if (pare != null) {
            recalcularRectanglePare(pare);
        }
    }

    /**
     * Retorna el node pare no borrat per actualitzar-lo
     * @param node: node pare
     * @return node: node pare a actualitzar
     */
    private Node removePare(Node node) {
        if (node != arrel) {
            Node pare = node.getNodePare();
            pare.removeFill(node);
            if (pare.getFills().size() == 0) {
                return removePare(pare);
            } else {
                return pare;
            }
        }

        return node;
    }
}