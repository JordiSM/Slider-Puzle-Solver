/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package view;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
    private Random rand;
    private int posXLibre = 0;
    private int posYLibre = 0;

    public Puzzle(View v, int w, int h, int t) {

        this.vista = v;
        this.width = w;
        this.height = h;
        this.tamPuzzle = t;

        Border borde = new LineBorder(Color.BLACK, 2);
        setBorder(borde);
        setLayout(null);
        setBounds(vista.MARGENLAT, vista.MARGENVER,
                width, height);
        setBackground(Color.WHITE);
        preparaPuzzle("src/img/imagen.jpg");
    }

    public void preparaPuzzle(String s) {
        try {
            // Cargar la imagen
            File file = new File(s);
            puzzleImage = ImageIO.read(file);

            // Inicializar la matriz de cuadriláteros
            int cuadrilateroWidth = puzzleImage.getWidth() / tamPuzzle;
            int cuadrilateroHeight = puzzleImage.getHeight() / tamPuzzle;
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
    }

    public void cambiaPieza(int i, int j) {
        //x = columna (width)
        //j = fila    (height)
        if ((posXLibre + 1) == i && (posYLibre == j)) {
            BufferedImage pieza = puzzle[posXLibre + 1][posYLibre];
            puzzle[posXLibre][posYLibre] = pieza;
            puzzle[posXLibre + 1][posYLibre] = null;
            posXLibre = i;
        } else if (posXLibre == i && (posYLibre + 1) == j) {
            BufferedImage pieza = puzzle[posXLibre][posYLibre + 1];
            puzzle[posXLibre][posYLibre] = pieza;
            puzzle[posXLibre][posYLibre + 1] = null;
            posYLibre = j;
        } else if (posXLibre == i && (posYLibre - 1) == j) {
            BufferedImage pieza = puzzle[posXLibre][posYLibre - 1];
            puzzle[posXLibre][posYLibre] = pieza;
            puzzle[posXLibre][posYLibre - 1] = null;
            posYLibre = j;
        } else if ((posXLibre - 1) == i && (posYLibre == j)) {
            BufferedImage pieza = puzzle[posXLibre - 1][posYLibre];
            puzzle[posXLibre][posYLibre] = pieza;
            puzzle[posXLibre - 1][posYLibre] = null;
            posXLibre = i;
        }
        System.out.println(posXLibre+" - "+posYLibre);
        repaint();
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calcular el tamaño de cada cuadrilátero en el JPanel
        int cuadrilateroWidth = getWidth() / tamPuzzle;
        int cuadrilateroHeight = getHeight() / tamPuzzle;
        // Dibujar los cuadriláteros en el JPanel
        for (int i = 0; i < tamPuzzle; i++) {
            for (int j = 0; j < tamPuzzle; j++) {
                if ((i != posXLibre) || (j != posYLibre)) {

                    // Calcular las coordenadas del cuadrilátero en el JPanel
                    int x = i * (cuadrilateroWidth + 2);
                    int y = j * (cuadrilateroHeight + 2);

                    // Dibujar el cuadrilátero en el JPanel
                    g.drawImage(puzzle[i][j], x, y, cuadrilateroWidth, cuadrilateroHeight, null);
                }
            }
        }
    }

    void reset(String ruta) {

    }

}
