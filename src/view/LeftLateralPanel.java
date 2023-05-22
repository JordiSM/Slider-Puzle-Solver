/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package view;

import java.awt.Color;

import javax.swing.JPanel;

import javax.swing.border.LineBorder;

/**
 * Panel lateral izquierdo de la ventana principal.
 */
public class LeftLateralPanel extends JPanel {

    private final View vista;
    
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    
    
    /**
     * Panel Lateral izquierdo encargado de la configuración del algoritmo y los
     * datos de la aplicación
     * @param v JFrame View
     */
    public LeftLateralPanel(View v) {
        this.vista = v;
        
        this.setLayout(null);
        this.x = 10;
        this.y = this.vista.MARGENVER;
        this.width = this.vista.MARGENLAT - 20;
        this.height = this.vista.getHeight() - this.vista.MARGENVER - 40;

        this.setBounds(x, y, width, height);
        this.setBackground(new Color(245, 245, 220));
        this.setBorder(new LineBorder(Color.BLACK, 2));
        
        this.init();
    }

    /**
     * Método encargado de la inicialización del JPanel y todos los componentes
     * que lo componen (JLabels, JComboBoxs y otros JPanels)
     */
    private void init() {
        
    }
    
}
