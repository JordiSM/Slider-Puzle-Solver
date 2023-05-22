/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package view;

import model.Model;
import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Vista de la aplicación, aquí interactuaremos con la aplicación y
 * visualizaremos todos los datos y los resultados de las operaciónes.
 */
public class View extends JFrame{

    // PUNTEROS DEL PATRÓN MVC
    private Controller controlador;
    private Model modelo;

    // CONSTANTES DE LA VISTA
    protected final int MARGENLAT = 300;
    protected final int MARGENVER = 50;

    // VARIABLES DEL JPanel
    private int GraphWidth;
    private int GraphHeight;

    private LeftLateralPanel leftPanel;
    private RightLateralPanel rightPanel;
    private GraphPanel graphPanel;

    // CONSTRUCTORS
    public View() {
    }

    public View(Controller controlador, Model modelo) {
        this.controlador = controlador;
        this.modelo = modelo;
    }

    // CLASS METHODS
    /**
     * Clase que inicializa la ventana principal y añade todos los elementos al
     * JFrame.
     */
    public void mostrar() {
        this.GraphWidth = 800;
        this.GraphHeight = 700;

        // NOT RESIZABLE
        this.setResizable(false);
        this.setLayout(null);
        
        // DIMENSION DEL JFRAME
        setSize(this.GraphWidth + this.MARGENLAT * 2, this.GraphHeight + this.MARGENVER * 3);

        // POSICIONAR EL JFRAME EN EL CENTRO DE LA PANTALLA
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        getContentPane().setBackground(new Color(212, 191, 142));
        
        // GRAPH PANEL
        graphPanel = new GraphPanel(this, GraphWidth, GraphHeight);
        this.add(graphPanel);

        // PANELES LATERALES
        leftPanel = new LeftLateralPanel(this);
        this.add(leftPanel);

        rightPanel = new RightLateralPanel(this);
        this.add(rightPanel);
        
        // TITULO
        JLabel label = new JLabel("Visualizador de Rutas: Djistra y Comercio");
        label.setFont(new Font("Britannic Bold", Font.BOLD, 15));
        label.setHorizontalAlignment(0);
        label.setBounds(getWidth() / 2 - 200, 10, 400, 30);
        this.add(label);
        
        // ÚLTIMOS AJUSTES
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    // GETTERS & SETTERS
    public Controller getControlador() {
        return controlador;
    }

    public void setControlador(Controller controlador) {
        this.controlador = controlador;
    }

    public Model getModelo() {
        return modelo;
    }

    public void setModelo(Model modelo) {
        this.modelo = modelo;
    }

    public int getGraphWidth() {
        return GraphWidth;
    }

    public int getGraphHeight() {
        return GraphHeight;
    }

    protected LeftLateralPanel getLeftPanel() {
        return this.leftPanel;
    }
    
    public RightLateralPanel getrightPanel() {
        return rightPanel;
    }
    
    public GraphPanel getgraphPanel() {
        return graphPanel;
    }
    
    protected void paintGraphPanel() {
        this.graphPanel.paint(this.getGraphics());
    }

    
    
}
