package Entities;

/**
 * Classe que implementa el rectangle dels Grafs i les seves respectives funcions i procediments
 */
public class Rectangle {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public Rectangle(Point[] points) {
        this.x1 = points[0].getX();
        this.y1 = points[0].getY();
        this.x2 = points[1].getX();
        this.y2 = points[1].getY();
    }

    public Rectangle(Point p1, Point p2) {
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }
}
