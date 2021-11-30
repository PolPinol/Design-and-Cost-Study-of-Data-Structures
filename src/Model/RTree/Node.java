package Model.RTree;

import Entities.Point;

import java.util.ArrayList;

/**
 * Classe que implementa el node dels arbres recursius i les seves respectives funcions i procediments
 */
public class Node {
    private Point p1;
    private Point p2;
    private Point pIntermig;
    private Point pOverflow;
    private Node nodePare;
    private ArrayList<Node> fills;
    private boolean esFulla;

    // Constructor del node fulla
    public Node(Point p1) {
        this.p1 = p1;
        this.esFulla = true;
        this.fills = new ArrayList<>();
    }

    // Constructor del rectangle
    public Node(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.esFulla = true;
        this.fills = new ArrayList<>();
    }

    public ArrayList<Node> getFills() {
        return fills;
    }

    public Node[] getFillsArray() {
        Node[] nodes = new Node[4];

        nodes[0] = fills.get(0);
        nodes[1] = fills.get(1);
        nodes[2] = fills.get(2);
        nodes[3] = fills.get(3);

        return nodes;
    }

    public boolean isNodeFulla() {
        return esFulla;
    }

    public void addPoint(Point point) {
        if (p2 == null) {
            p2 = point;
        } else if (pIntermig == null)  {
            pIntermig = point;
        } else {
            pOverflow = point;
        }
    }

    public boolean askOverflow() {
        if (esFulla) {
            return pOverflow != null;
        } else {
            return fills.size() == 4;
        }
    }

    /**
     * Funcio que afegeix un node
     * @param node1
     * @param node2
     * @return retorna la àrea generada
     */
    public double areaAddingNode(Node node1, Node node2) {
        ArrayList<Double> xPoints = new ArrayList<>();
        ArrayList<Double> yPoints = new ArrayList<>();
        double maxX, maxY, minX, minY;

        xPoints.add(p1.getX());

        if (p2 != null) {
            xPoints.add(p2.getX());
            yPoints.add(p2.getY());
        }

        xPoints.add(node1.p1.getX());
        xPoints.add(node2.p1.getX());
        if (node1.p2 != null) {
            xPoints.add(node1.p2.getX());
            yPoints.add(node1.p2.getY());
        }
        if (node2.p2 != null) {
            xPoints.add(node2.p2.getX());
            yPoints.add(node2.p2.getY());
        }

        yPoints.add(p1.getY());
        yPoints.add(node1.p1.getY());
        yPoints.add(node2.p1.getY());


        maxX = maximumDouble(xPoints);
        maxY = maximumDouble(yPoints);
        minX = minimumDouble(xPoints);
        minY = minimumDouble(yPoints);

        return areaDosPuntos(maxX, maxY, minX, minY);
    }

    /**
     * Obté els punts i els retorna
     * @return points: punts trobats
     */
    public Point[] getPoints() {
        Point[] points = new Point[4];

        points[0] = p1;
        points[1] = p2;
        points[2] = pIntermig;
        points[3] = pOverflow;

        return points;
    }

    /**
     * Obté els punts dins un array list
     * @return punts: punts trobats
     */
    public ArrayList<Point> getPointsArrayList() {
        ArrayList<Point> punts = new ArrayList<>();

        punts.add(p1);

        if (p2 != null) {
            punts.add(p2);
        }

        if (pIntermig != null) {
            punts.add(pIntermig);
        }

        return punts;
    }

    public double area() {
        if (p2 == null) {
            return 0;
        }
        return areaDosPuntos(p1, p2);
    }

    /**
     * Defineix nous punts per a un rectangle
     * @param p1: primer punt
     * @param p2: segon punt
     * @param pIntermig: punt que queda enmig del rectangle
     */
    public void setNewPoints(Point p1, Point p2, Point pIntermig) {
        this.p1 = p1;
        this.p2 = p2;
        this.pIntermig = pIntermig;
        this.pOverflow = null;
    }

    public void setPoints(Point[] points) {
        this.p1 = points[0];
        this.p2 = points[1];
    }

    public void addFills(Node fill) {
        fills.add(fill);
    }

    public void removeFills(Node fill) {
        fills.remove(fill);
    }

    public void setNoFulla() {
        this.esFulla = false;
    }

    public Node getNodePare() {
        return nodePare;
    }

    public void setNodePare(Node nodePare) {
        this.nodePare = nodePare;
    }

    /**
     * Elimina un punt
     * @param index: identificador del punt dins l'arbre
     */
    public void eliminarPunt(int index) {
        if (index == 0) {
            p1 = null;
            if (p2 != null) {
                p1 = new Point(p2.getName(), p2.getX(), p2.getY());
                p2 = null;
            }
            if (pIntermig != null) {
                p2 = new Point(pIntermig.getName(), pIntermig.getX(), pIntermig.getY());
                pIntermig = null;
            }
        } else if (index == 1) {
            p2 = null;
            if (pIntermig != null) {
                p2 = new Point(pIntermig.getName(), pIntermig.getX(), pIntermig.getY());
                pIntermig = null;
            }
        } else {
            pIntermig = null;
        }
    }

    public void removeFill(Node node) {
        fills.remove(node);
    }

    public Point[] getPointsRectangleNodeLeaf() {
        return getPointsRectangle(p1, p2, pIntermig, pOverflow);
    }

    /**
     * -------------------------------------------------------------------
     * MÈTODES PER FER OPERACIONS AMB RECTANGLES, PUNTS I AREAS.
     * Finalment s'ha decidit no fer una classe Geometry per facilitar el treball en grup
     * però es deixa pendent per un futur la neteja de codi de les següent linies
     * on només es fan operacions bàsiques de geometria i càlcul bàsic.
     * -------------------------------------------------------------------
     */

    /**
     * Funció que retorna l'area d'afegir un punt en aquest node
     * @param point: punt a afegir
     * @return àrea generada
     */
    public double areaAddingPoint(Point point) {
        double x1, x2, x3, x4;
        double y1, y2, y3, y4;
        double maxX, maxY, minX, minY;

        x1 = p1.getX();
        x3 = point.getX();

        y1 = p1.getY();
        y3 = point.getY();

        if (p2 != null) {
            x2 = p2.getX();
            y2 = p2.getY();

            if (pIntermig != null) {
                x4 = pIntermig.getX();
                y4 = pIntermig.getY();

                maxX = maximumDouble(x1, x2, x3, x4);
                maxY = maximumDouble(y1, y2, y3, y4);
                minX = minimumDouble(x1, x2, x3, x4);
                minY = minimumDouble(y1, y2, y3, y4);
            } else {
                maxX = maximumDouble(x1, x2, x3);
                maxY = maximumDouble(y1, y2, y3);
                minX = minimumDouble(x1, x2, x3);
                minY = minimumDouble(y1, y2, y3);
            }
        } else {
            maxX = Math.max(x1, x3);
            maxY = Math.max(y1, y3);
            minX = Math.max(x1, x3);
            minY = Math.max(y1, y3);
        }

        return areaDosPuntos(maxX, maxY, minX, minY);
    }

    /**
     * Funcio que afegeix un node
     * @param node: node a afegir
     * @return àrea generada per la inserció del punt
     */
    public double areaAddingNode(Node node) {
        ArrayList<Double> xPoints = new ArrayList<>();
        ArrayList<Double> yPoints = new ArrayList<>();
        double maxX, maxY, minX, minY;
        xPoints.add(p1.getX());
        xPoints.add(node.p1.getX());


        yPoints.add(p1.getY());
        if (p2 != null) {
            xPoints.add(p2.getX());
            yPoints.add(p2.getY());
        }
        yPoints.add(node.p1.getY());
        if (node.p2 != null) {
            xPoints.add(node.p2.getX());
            yPoints.add(node.p2.getY());
        }


        maxX = maximumDouble(xPoints);
        maxY = maximumDouble(yPoints);
        minX = minimumDouble(xPoints);
        minY = minimumDouble(yPoints);

        return areaDosPuntos(maxX, maxY, minX, minY);
    }

    /**
     * Troba els punts que delimiten un rectangle amb un fill
     * @param fill1: fill del rectangle
     * @return point[]: punts del rectangle
     */
    public Point[] trobaPuntsRectangle(Node fill1) {
        if (fill1.esFulla) {
            ArrayList<Double> xPoints = new ArrayList<>();
            ArrayList<Double> yPoints = new ArrayList<>();
            double maxX, maxY, minX, minY;

            Point p1 = fill1.p1;
            xPoints.add(p1.getX());
            yPoints.add(p1.getY());
            Point p2 = fill1.p2;
            if (p2 != null) {
                xPoints.add(p2.getX());
                yPoints.add(p2.getY());
            }


            if (fill1.pIntermig != null) {
                Point p = fill1.pIntermig;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            if (fill1.pOverflow != null) {
                Point p = fill1.pOverflow;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            maxX = maximumDouble(xPoints);
            maxY = maximumDouble(yPoints);
            minX = minimumDouble(xPoints);
            minY = minimumDouble(yPoints);

            Point[] points = new Point[2];
            points[0] = new Point("", minX, minY);
            points[1] = new Point("", maxX, maxY);

            return points;
        } else {
            ArrayList<Double> xPoints = new ArrayList<>();
            ArrayList<Double> yPoints = new ArrayList<>();
            double maxX, maxY, minX, minY;

            Point p1 = fill1.p1;
            xPoints.add(p1.getX());
            yPoints.add(p1.getY());
            Point p2 = fill1.p2;
            xPoints.add(p2.getX());
            yPoints.add(p2.getY());

            maxX = maximumDouble(xPoints);
            maxY = maximumDouble(yPoints);
            minX = minimumDouble(xPoints);
            minY = minimumDouble(yPoints);

            Point[] points = new Point[2];
            points[0] = new Point("", minX, minY);
            points[1] = new Point("", maxX, maxY);

            return points;
        }
    }

    /**
     * Troba els punts d'un rectangle amb dos fills
     * @param fill1: primer fill del rectangle
     * @param fill2: segon fill del rectangle
     * @return points[]: punts trobats
     */
    public Point[] trobaPuntsRectangle(Node fill1, Node fill2) {
        if (fill1.esFulla) {
            ArrayList<Double> xPoints = new ArrayList<>();
            ArrayList<Double> yPoints = new ArrayList<>();
            double maxX, maxY, minX, minY;

            Point p1 = fill1.p1;
            xPoints.add(p1.getX());
            yPoints.add(p1.getY());
            Point p2 = fill1.p2;
            if (p2 != null) {
                xPoints.add(p2.getX());
                yPoints.add(p2.getY());
            }

            if (fill1.pIntermig != null) {
                Point p = fill1.pIntermig;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            if (fill1.pOverflow != null) {
                Point p = fill1.pOverflow;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            Point p3 = fill2.p1;
            xPoints.add(p3.getX());
            yPoints.add(p3.getY());
            Point p4 = fill2.p2;
            if (p4 != null) {
                xPoints.add(p4.getX());
                yPoints.add(p4.getY());
            }

            if (fill1.pIntermig != null) {
                Point p = fill1.pIntermig;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            if (fill2.pOverflow != null) {
                Point p = fill2.pOverflow;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            maxX = maximumDouble(xPoints);
            maxY = maximumDouble(yPoints);
            minX = minimumDouble(xPoints);
            minY = minimumDouble(yPoints);

            Point[] points = new Point[2];
            points[0] = new Point("", minX, minY);
            points[1] = new Point("", maxX, maxY);

            return points;
        } else {
            Point p1 = fill1.p1;
            Point p2 = fill1.p2;
            Point p3 = fill2.p1;
            Point p4 = fill2.p2;

            return getPointsRectangle(p1, p2, p3, p4);
        }
    }

    /**
     * Troba els punts d'un rectangle amb tres fills
     * @param fill1: primer fill del rectangle
     * @param fill2: segon fill del rectangle
     * @param fill3: tercer fill del rectangle
     * @return points[]: punts obtinguts
     */
    public Point[] trobaPuntsRectangle(Node fill1, Node fill2, Node fill3) {
        if (fill1.esFulla) {
            ArrayList<Double> xPoints = new ArrayList<>();
            ArrayList<Double> yPoints = new ArrayList<>();
            double maxX, maxY, minX, minY;

            Point p1 = fill1.p1;
            xPoints.add(p1.getX());
            yPoints.add(p1.getY());
            Point p2 = fill1.p2;
            if (p2 != null) {
                xPoints.add(p2.getX());
                yPoints.add(p2.getY());
            }

            if (fill1.pIntermig != null) {
                Point p = fill1.pIntermig;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            if (fill1.pOverflow != null) {
                Point p = fill1.pOverflow;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            Point p3 = fill2.p1;
            xPoints.add(p3.getX());
            yPoints.add(p3.getY());

            Point p4 = fill2.p2;
            if (p4 != null) {
                xPoints.add(p4.getX());
                yPoints.add(p4.getY());
            }


            if (fill2.pIntermig != null) {
                Point p = fill2.pIntermig;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            if (fill2.pOverflow != null) {
                Point p = fill2.pOverflow;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            Point p5 = fill3.p1;
            xPoints.add(p5.getX());
            yPoints.add(p5.getY());
            Point p6 = fill3.p2;
            if (p6 != null) {
                xPoints.add(p6.getX());
                yPoints.add(p6.getY());
            }
            if (fill3.pIntermig != null) {
                Point p = fill3.pIntermig;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            if (fill3.pOverflow != null) {
                Point p = fill3.pOverflow;
                xPoints.add(p.getX());
                yPoints.add(p.getY());
            }

            maxX = maximumDouble(xPoints);
            maxY = maximumDouble(yPoints);
            minX = minimumDouble(xPoints);
            minY = minimumDouble(yPoints);

            Point[] points = new Point[2];
            points[0] = new Point("", minX, minY);
            points[1] = new Point("", maxX, maxY);

            return points;
        } else {
            ArrayList<Double> xPoints = new ArrayList<>();
            ArrayList<Double> yPoints = new ArrayList<>();
            double maxX, maxY, minX, minY;
            Point p1 = fill1.p1;
            Point p2 = fill1.p2;
            Point p3 = fill2.p1;
            Point p4 = fill2.p2;
            Point p5 = fill3.p1;
            Point p6 = fill3.p2;

            xPoints.add(p1.getX());
            yPoints.add(p1.getY());
            xPoints.add(p2.getX());
            yPoints.add(p2.getY());
            xPoints.add(p3.getX());
            yPoints.add(p3.getY());
            xPoints.add(p4.getX());
            yPoints.add(p4.getY());
            xPoints.add(p5.getX());
            yPoints.add(p5.getY());
            xPoints.add(p6.getX());
            yPoints.add(p6.getY());

            maxX = maximumDouble(xPoints);
            maxY = maximumDouble(yPoints);
            minX = minimumDouble(xPoints);
            minY = minimumDouble(yPoints);

            Point[] points = new Point[2];
            points[0] = new Point("", minX, minY);
            points[1] = new Point("", maxX, maxY);

            return points;
        }
    }

    /**
     * Troba els punts d'un rectangle
     * @param puntsFills: fills d'aquest
     * @return points[]: punts trobats
     */
    public Point[] trobaPuntsRectangle(ArrayList<Point> puntsFills) {
        ArrayList<Double> xPoints = new ArrayList<>();
        ArrayList<Double> yPoints = new ArrayList<>();
        double maxX, maxY, minX, minY;

        for (int i = 0; i < puntsFills.size(); i++) {
            xPoints.add(puntsFills.get(i).getX());
            yPoints.add(puntsFills.get(i).getY());
        }

        maxX = maximumDouble(xPoints);
        maxY = maximumDouble(yPoints);
        minX = minimumDouble(xPoints);
        minY = minimumDouble(yPoints);

        Point[] points = new Point[2];
        points[0] = new Point("", minX, minY);
        points[1] = new Point("", maxX, maxY);

        return points;
    }

    /**
     * Funció que comproba si hi ha un intersecció entre dos rectangles
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return boolean: indica si intersecta o no
     */
    public boolean intersects(double x1, double y1, double x2, double y2) {
        double x3 = p1.getX();
        double y3 = p1.getY();
        double x4, y4;

        if (p2 == null) {
            x4 = p1.getX();
            y4 = p1.getY();
        } else {
            x4 = p2.getX();
            y4 = p2.getY();
        }

        Point[] punts1 = getPointsRectangle(new Point("", x1, y1), new Point("", x2, y2));
        Point[] punts2 = getPointsRectangle(new Point("", x3, y3), new Point("", x4, y4));

        if ((punts2[0].getX() < punts1[1].getX()) && (punts1[0].getX() < punts2[1].getX()) && (punts2[0].getY() < punts1[1].getY()) && (punts1[0].getY() < punts2[1].getY())) {
            return true;
        }

        return false;
    }

    /**
     * Calcula la distància entre un punt i un rectangle
     * https://stackoverflow.com/questions/5254838/calculating-distance-between-a-point-and-a-rectangular-box-nearest-point
     * @param x: coordenada x del punt
     * @param y: coordenada y del punt
     * @return
     */
    public double calculaDistanciaMinima(double x, double y) {
        double dx, dy;

        if (p2 == null) {
            dx = Math.max(p1.getX() - x, Math.max(0, x - p1.getX()));
            dy = Math.max(p1.getY() - y, Math.max(0, y - p1.getY()));
        } else {
            dx = Math.max(p1.getX() - x, Math.max(0, x - p2.getX()));
            dy = Math.max(p1.getY() - y, Math.max(0, y - p2.getY()));
        }

        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     * Calcula l'àrea entre dos punts
     * @param p1: primer punt
     * @param p2: segon punt
     * @return double: àrea entre els dos punts
     */
    public double areaDosPuntos(Point p1, Point p2) {
        double x = Math.abs(p1.getX()-p2.getX());
        double y = Math.abs(p1.getY()-p2.getY());
        return x*y;
    }

    /**
     * Mateixa funció que la anterior però enlloc de rebre punts rep les seves respectives coordenades
     * @param x1: coordenada x del primer punt
     * @param y1: coordenada y del primer punt
     * @param x2: coordenada x del segon punt
     * @param y2: coordenada y del segon punt
     * @return double: àrea entre els dos punts
     */
    public double areaDosPuntos(double x1, double y1, double x2, double y2) {
        double x = Math.abs(x1 - x2);
        double y = Math.abs(y1 - y2);
        return x*y;
    }

    /**
     * Calcula l'àrea entre tres punts
     * @param p1: primer punt
     * @param p2: segon punt
     * @param p3: tercer punt
     * @return double: àrea generada
     */
    public double areaTresPuntos(Point p1, Point p2, Point p3) {
        double x1, x2, x3;
        double y1, y2, y3;
        double maxX, maxY, minX, minY;

        x1 = p1.getX();
        x2 = p2.getX();
        x3 = p3.getX();

        y1 = p1.getY();
        y2 = p2.getY();
        y3 = p3.getY();

        maxX = maximumDouble(x1, x2, x3);
        maxY = maximumDouble(y1, y2, y3);
        minX = minimumDouble(x1, x2, x3);
        minY = minimumDouble(y1, y2, y3);

        return areaDosPuntos(maxX, maxY, minX, minY);
    }

    /**
     * Obté els punts d'un rectangle
     * @param p1: primer punt del rectangle
     * @param p2: segon punt del rectangle
     * @return points[]: tots els punts trobats dins d'aquest
     */
    public Point[] getPointsRectangle(Point p1, Point p2) {
        ArrayList<Double> xPoints = new ArrayList<>();
        ArrayList<Double> yPoints = new ArrayList<>();
        double maxX, maxY, minX, minY;

        xPoints.add(p1.getX());
        xPoints.add(p2.getX());

        yPoints.add(p1.getY());
        yPoints.add(p2.getY());

        maxX = maximumDouble(xPoints);
        maxY = maximumDouble(yPoints);
        minX = minimumDouble(xPoints);
        minY = minimumDouble(yPoints);

        Point[] points = new Point[2];
        points[0] = new Point("", minX, minY);
        points[1] = new Point("", maxX, maxY);

        return points;
    }

    /**
     * Troba els punts dins un rectangle delimitat per tres punts
     * @param p1: primer punt
     * @param p2: segon punt
     * @param p3: tercer punt
     * @return points[]: punts trobats en el rectangle
     */
    public Point[] getPointsRectangle(Point p1, Point p2, Point p3) {
        ArrayList<Double> xPoints = new ArrayList<>();
        ArrayList<Double> yPoints = new ArrayList<>();
        double maxX, maxY, minX, minY;

        xPoints.add(p1.getX());
        xPoints.add(p2.getX());
        xPoints.add(p3.getX());

        yPoints.add(p1.getY());
        yPoints.add(p2.getY());
        yPoints.add(p3.getY());

        maxX = maximumDouble(xPoints);
        maxY = maximumDouble(yPoints);
        minX = minimumDouble(xPoints);
        minY = minimumDouble(yPoints);

        Point[] points = new Point[2];
        points[0] = new Point("", minX, minY);
        points[1] = new Point("", maxX, maxY);

        return points;
    }

    /**
     * Troba els punts dins un rectangle definit per 4 punts
     * @param p1: primer punt
     * @param p2: segon punt
     * @param p3: tercer punt
     * @param p4: quart punt
     * @return points[]: punts trobats dins el rectangle
     */
    public Point[] getPointsRectangle(Point p1, Point p2, Point p3, Point p4) {
        ArrayList<Double> xPoints = new ArrayList<>();
        ArrayList<Double> yPoints = new ArrayList<>();
        double maxX, maxY, minX, minY;

        xPoints.add(p1.getX());
        xPoints.add(p2.getX());
        xPoints.add(p3.getX());
        xPoints.add(p4.getX());

        yPoints.add(p1.getY());
        yPoints.add(p2.getY());
        yPoints.add(p3.getY());
        yPoints.add(p4.getY());

        maxX = maximumDouble(xPoints);
        maxY = maximumDouble(yPoints);
        minX = minimumDouble(xPoints);
        minY = minimumDouble(yPoints);

        Point[] points = new Point[2];
        points[0] = new Point("", minX, minY);
        points[1] = new Point("", maxX, maxY);

        return points;
    }

    /**
     * ------------------------------------------------------
     * Funcions matemàtiques i/o de càlcul de variables
     * ------------------------------------------------------
     */

    public double minimumDouble(double a, double b, double c) {
        return Math.min(a, Math.min(b, c));
    }

    public double maximumDouble(double a, double b, double c) {
        return Math.max(a, Math.max(b, c));
    }

    public double minimumDouble(double a, double b, double c, double d) {
        return Math.min(a, Math.min(b, Math.min(c, d)));
    }

    public double maximumDouble(double a, double b, double c, double d) {
        return Math.max(a, Math.max(b, Math.max(c, d)));
    }

    public double minimumDouble(ArrayList<Double> points) {
        return minimumDoubleRecursive(points, 0, Double.MAX_VALUE);
    }

    private double minimumDoubleRecursive(ArrayList<Double> points, int n, double min) {
        if (n == points.size()) {
            return min;
        } else {
            return minimumDoubleRecursive(points, n+1,  Math.min(points.get(n), min));
        }
    }

    public double maximumDouble(ArrayList<Double> points) {
        return maximumDoubleRecursive(points, 0, Double.MIN_VALUE);
    }

    private double maximumDoubleRecursive(ArrayList<Double> points, int n, double max) {
        if (n == points.size()) {
            return max;
        } else {
            return maximumDoubleRecursive(points, n+1,  Math.max(points.get(n), max));
        }
    }
}