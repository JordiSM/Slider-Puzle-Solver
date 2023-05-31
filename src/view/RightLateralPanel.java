/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.LineBorder;

/**
 * Panel lateral derecho de la ventana principal.
 */
public class RightLateralPanel extends JPanel {

    private final View vista;
    private int x, y, width, height;
   
    private TimePanel timePanel;
    private JButton start;

    public RightLateralPanel(View v) {
        this.vista = v;
        init();
    }

    private void init() {
        this.setLayout(null);
        this.x = this.vista.getWidth() + 10 - this.vista.MARGENLAT;
        this.y = this.vista.MARGENVER;
        this.width = this.vista.MARGENLAT - 20;
        this.height = this.vista.getHeight() - this.vista.MARGENVER - 40;

        this.setBounds(x, y, width, height);
        this.setBackground(new Color(245, 245, 220));
        this.setBorder(new LineBorder(Color.BLACK, 2));   
        this.start = new JButton("START");
        this.start.setBounds(10, height - 80, width - 20, 70);
        this.start.setBackground(new Color(135,116,89));
        this.start.setBorder(new LineBorder(Color.BLACK, 2)); 
        Font font = new Font("Arial", Font.BOLD,16);
        this.start.setFont(font);
        this.start.setForeground(Color.WHITE);
        this.add(start);
        
        this.timePanel = new TimePanel(10, + 10 ,width - 20, 80);
        this.add(timePanel);
        
        this.setVisible(true);

    }
    
    private class TimePanel extends JPanel {

        private JLabel timeLabel;

        private TimePanel(int x, int y, int width, int height) {
            this.setBounds(x, y, width, height);
            this.setBorder(new LineBorder(Color.BLACK, 2));

            this.timeLabel = new JLabel("");
            this.add(timeLabel);
        }

        public String getTime() {
            return this.timeLabel.getText();
        }

        public void setTime(long nanoseconds) {
            this.timeLabel.setText(String.valueOf(nanoseconds));
        }
    }

    public void setTime(long nanoseconds) {
        this.timePanel.setTime(nanoseconds);
    }

}
