package Entities;

/**
 * Classe que implementa el punt usat per als Grafs i totes les respectives funcions i procediments
 */
public class Point {
    private String name;
    private double x;
    private double y;

    public Point(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}