package Model.Taula;

import javax.swing.*;
import java.awt.*;

/**
 * Aquesta classe que exté JPanel mostra i genera un panell on mostrar de forma gràfica les taules
 */
public class GraphicsPanel extends JPanel {
    private int[] values;
    public static final int BORDER = 10;

    public GraphicsPanel(int[] values){
        this.values = values;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);

        for (int i = 0; i < values.length; i++) {
            g.drawLine(40, 9 * (i+1), (values[i]) + 40, 9 * (i+1));
        }

        for (int i = 0; i < 92; i++) {
            g.drawString(String.valueOf(i) , 10 , BORDER * i);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 9));
        }

    }
}