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

import java.awt.Toolkit;

import javax.swing.JFrame;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Vista de la aplicación, aquí interactuaremos con la aplicación y
 * visualizaremos todos los datos y los resultados de las operaciónes.
 */
public class View extends JFrame {

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
    private Puzzle puzzle;

    // CONSTRUCTORS
    public View() {
        this.mostrar();
    }

    // CLASS METHODS
    /**
     * Clase que inicializa la ventana principal y añade todos los elementos al
     * JFrame.
     */
    public void mostrar() {
        this.GraphWidth = 500;
        this.GraphHeight = 500;

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
        puzzle = new Puzzle(this, GraphWidth, GraphHeight, 3,  "src/img/imagen.jpg");
        this.add(puzzle);

        // PANELES LATERALES
        leftPanel = new LeftLateralPanel(this);
        this.add(leftPanel);

        rightPanel = new RightLateralPanel(this);
        this.add(rightPanel);

        // ÚLTIMOS AJUSTES
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void cambiarPuzzle(Puzzle p) {
        this.remove(this.puzzle);
        this.puzzle = p;
        this.add(puzzle);
        this.repaint();
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

    public Puzzle getPuzzle() {
        return puzzle;
    }

    protected void paintGraphPanel() {
        this.puzzle.paint(this.getGraphics());
    }

    void newControlador(int i) {
        this.controlador = new Controller(this.puzzle.getModelo(), this);
        this.controlador.setType(i);
        this.controlador.run();

    }

}
