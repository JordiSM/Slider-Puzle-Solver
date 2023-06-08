/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import model.Model;

/**
 * Panel para pintar los puntos generados.
 */
public final class Puzzle extends JPanel {

    private final View vista;
    private final int width;
    private final int height;
    private BufferedImage[][] puzzle;
    private BufferedImage puzzleImage;
    private final int tamPuzzle;
    private int cuadrilateroWidth;
    private int cuadrilateroHeight;
    private String imagen;
    private Model model;

    public Puzzle(View v, int w, int h, int t, String s) {

        this.vista = v;
        this.width = w;
        this.height = h;
        this.tamPuzzle = t;
        this.imagen = s;
        Border borde = new LineBorder(Color.BLACK, 2);
        setBorder(borde);
        setLayout(null);
        setBackground(Color.WHITE);
        preparaPuzzle(imagen);
        setBounds(vista.MARGENLAT, vista.MARGENVER,
                width, height);
    }

    public void preparaPuzzle(String s) {
        try {
            // Cargar la imagen
            File file = new File(s);
            puzzleImage = ImageIO.read(file);

            // Inicializar la matriz de cuadriláteros
            cuadrilateroWidth = puzzleImage.getWidth() / tamPuzzle;
            cuadrilateroHeight = puzzleImage.getHeight() / tamPuzzle;
            puzzle = new BufferedImage[tamPuzzle][tamPuzzle];

            // Dividir la imagen en cuadriláteros y guardarlos en la matriz puzzle
            for (int i = 0; i < tamPuzzle; i++) {
                for (int j = 0; j < tamPuzzle; j++) {
                    // Obtener la región de la imagen correspondiente al cuadrilátero
                    BufferedImage cuadrilatero = puzzleImage.getSubimage(
                            i * (cuadrilateroWidth), j * (cuadrilateroHeight),
                            (cuadrilateroWidth - 4), (cuadrilateroHeight - 4));

                    // Guardar el cuadrilátero en la matriz puzzle
                    puzzle[i][j] = cuadrilatero;
                }
            }

            // Resto de la preparación del puzzle
        } catch (IOException e) {
            System.out.println("No se encontró el archivo " + s);
        }
        this.model = new Model(this.vista, this.tamPuzzle);

    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth(), getHeight());
        
        int[][] tablero = this.model.getPuzle();

        // Calcular el tamaño de cada cuadrilátero en el JPanel
        int cuadrilateroWidthP = getWidth() / tamPuzzle - 1;
        int cuadrilateroHeightP = getHeight() / tamPuzzle - 1;
        // Dibujar los cuadriláteros en el JPanel
        for (int i = 0; i < tamPuzzle; i++) {
            for (int j = 0; j < tamPuzzle; j++) {

                // Calcular las coordenadas del cuadrilátero en el JPanel
                int x = i * (cuadrilateroWidthP + 1) + (tamPuzzle / 3);
                int y = j * (cuadrilateroHeightP + 1) + (tamPuzzle / 3);

                // Dibujar el cuadrilátero en el JPanel
                int indice = tablero[i][j];

                if (indice != -1) {
                    int fila = indice / tamPuzzle;
                    int columna = indice % tamPuzzle;
                    g2d.drawImage(puzzle[fila][columna], x, y, cuadrilateroWidthP, cuadrilateroHeightP, null);
                }
            }
        }
        
        g.drawImage(img, 0, 0, this);
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTamPuzzle() {
        return this.tamPuzzle;
    }

    public Model getModelo() {
        return this.model;
    }

    void reset(String ruta) {
        this.preparaPuzzle(ruta);
    }

}
