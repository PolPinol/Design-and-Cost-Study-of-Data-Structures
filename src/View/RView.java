package View;

import Entities.Point;
import Entities.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Classe que mostra de forma gràfica els tresors respecte als rectangles
 */
public class RView {
    private JFrame frame;

    public RView(ArrayList<Point> punts, ArrayList<Rectangle> rectangles) {
        frame = new JFrame("DrawRect");
        frame.getContentPane().add(new RTreePanel(punts, rectangles));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
    }

    /**
     * Fa el gràfic visible
     */
    public void show() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}