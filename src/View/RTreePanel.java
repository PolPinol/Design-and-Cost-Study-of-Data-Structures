package View;

import Entities.Point;
import Entities.Rectangle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Implementa la classe dels arbres recursius i totes les funcions relacionades
 */
public class RTreePanel extends JPanel implements ChangeListener {
    public static final int BORDER = 40;
    static final int MIN = 0;
    static final int MAX = 100;
    static final int INIT = 40;

    private ArrayList<Point> punts;
    private ArrayList<Rectangle> rectangles;
    private int multiplier;
    private JSlider slide;

    public RTreePanel(ArrayList<Point> punts, ArrayList<Rectangle> rectangles) {
        this.setLayout(new BorderLayout());
        this.punts = punts;
        this.rectangles = rectangles;
        this.multiplier = INIT;
        addJSlider();
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(1500, 800));
    }

    /**
     * Mostra de forma gràfica tots els punts i rectangles generats
     * @param g: Gràfics necessaris per a poder pintar per pantalla
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(BORDER, BORDER, BORDER, 5000);
        g.drawLine(BORDER, BORDER, 5000, BORDER);
        g.drawString("eix X" , 40, 30);
        g.drawString("eix Y" , 10, 60);

        for (int i = 1; i < 500; i++) {
            g.drawString(String.valueOf(i) , BORDER + i* multiplier - 5, BORDER - 10);
            g.drawString(String.valueOf(i) , BORDER - 20, BORDER + i* multiplier + 5);
            g.drawLine(BORDER + i* multiplier, BORDER - 5, BORDER + i* multiplier, BORDER + 5);
            g.drawLine(BORDER - 5, BORDER + i* multiplier, BORDER + 5, BORDER + i* multiplier);
        }

        for (int i = 0; i < rectangles.size(); i++) {
            int x1 = (int) rectangles.get(i).getX1();
            int y1 = (int) rectangles.get(i).getY1();

            int x2 = (int) rectangles.get(i).getX2();
            int y2 = (int) rectangles.get(i).getY2();

            drawRectangle(x2 - Math.abs(x2-x1), y2 - Math.abs(y2-y1), Math.abs(x2-x1), Math.abs(y2-y1), g);
        }

        for (int i = 0; i < punts.size(); i++) {
            Point p = punts.get(i);
            int x = (int) p.getX();
            int y = (int) p.getY();
            String name = p.getName();
            drawPoint(x, y, name, g);
        }
    }

    /**
     * Dibuixa un rectangle per pantalla
     * @param x: coordenada x del primer punt
     * @param y: coordenade y del primer punt
     * @param width: amplada del rectangle
     * @param height: alçada del rectangle
     * @param g: Gràfics per a poder treballar amb jswing
     */
    private void drawRectangle(int x, int y, int width, int height, Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(x* multiplier + BORDER, y* multiplier + BORDER, width* multiplier + 10, height* multiplier + 10);
    }

    /**
     * Dibuixa un punt per pantalla
     * @param x: coordenada x del punt
     * @param y: coordenada y del punt
     * @param name: nom del tresor que es troba en el punt
     * @param g: Gràfics per a poder treballar amb jswing
     */
    private void drawPoint(int x, int y, String name, Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x* multiplier + BORDER, y* multiplier + BORDER,10,10);
        g.setColor(Color.WHITE);
        g.drawString(name, x* multiplier + BORDER, y* multiplier + BORDER);
    }

    /**
     * Afegeix un slider per a poder fer zoom in/out
     */
    private void addJSlider() {
        slide = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INIT);
        slide.setMajorTickSpacing(10);
        slide.setMinorTickSpacing(1);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        slide.addChangeListener(this);
        this.add(slide, BorderLayout.SOUTH);
    }

    /**
     * Detecta quan es realitza un canvi en la interfície gràfica
     * @param e: Event que provoca un canvi
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            multiplier = (int) source.getValue();
            this.repaint();
            this.revalidate();
        }
    }
}