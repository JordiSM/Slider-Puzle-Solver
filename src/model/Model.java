/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package model;

import java.util.List;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import view.View;

/**
 * Modelo de la aplicación, aquí se guardan todos los datos necesarios para su
 * correcta operación.
 */
public class Model {

    // PUNTEROS DEL PATRÓN MVC
    private View vista;
    private ArrayList<Object> solution; //Lista ordenada de movimientos
    private BufferedImage[][] puzzle;
    private int tamañoTablero;
    private Point blank;

    // CONSTRUCTORS
    public Model(View vista, int n) {
        this.vista = vista;
        this.tamañoTablero = n;
        this.puzzle = vista.getPuzzle().getPuzzle();
        //this.puzzle = newTablero(n);
    }

    public Model(View vista) {
        this.vista = vista;
        this.tamañoTablero = 3;
        this.puzzle = vista.getPuzzle().getPuzzle();
        //this.puzzle = newTablero(3);

    }

    // CLASS METHODS
    /*private int[][] newTablero(int n) {
        int[][] tablero = new int[n][n];
        int index = 0;

        for (int fila[] : tablero) {
            for (int columna : fila) {
                columna = index++;
            }
        }
        tablero[n - 1][n - 1] = -1;
        return tablero;
    }*/

 /* public void randomize() throws Exception {
        Random rnd = new Random();
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {

                int randomIndex = rnd.nextInt(this.tamañoTablero * this.tamañoTablero);

                int fila = randomIndex / tamañoTablero;
                int columna = randomIndex % tamañoTablero;

                //SWAP
                BufferedImage temp = this.puzzle[fila][columna];
                this.puzzle[fila][columna] = this.puzzle[i][j];
                this.puzzle[i][j] = temp;
            }
        }

        this.blank = findBlank();
    }*/
    public void randomize() throws Exception {
        Random rnd = new Random();
        BufferedImage[][] puzzleShuf = new BufferedImage[this.tamañoTablero][this.tamañoTablero];
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {

                int randomIndex = rnd.nextInt(this.tamañoTablero * this.tamañoTablero);

                int fila = randomIndex / tamañoTablero;
                int columna = randomIndex % tamañoTablero;

                //SWAP
                BufferedImage temp = this.puzzle[fila][columna];
                puzzleShuf[fila][columna] = this.puzzle[i][j];
                puzzleShuf[i][j] = temp;
            }
        }
        this.vista.getPuzzle().shuffle(puzzleShuf);
        //this.blank = findBlank();
    }

    /* public boolean isSolved() {
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {
                if (this.puzzle[i][j] != (j + i * tamañoTablero)) {
                    if (i != tamañoTablero - 1 && j != tamañoTablero - 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }*/
    public boolean isSolved(BufferedImage[][] puzzleSol) {
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {
                if (this.puzzle[i][j] != puzzleSol[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private Point findBlank() throws Exception {
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {
                if (this.puzzle[i][j] == null) {
                    return new Point(i, j);
                }
            }
        }
        //ERROR
        throw (new RuntimeException("Error calling findBlank(): Blank not found"));
    }

    public Movement getRandomMove() {
        Random rnd = new Random();
        Movement mov = null;
        boolean isValid = false;

        while (!isValid) {
            mov = Movement.values()[rnd.nextInt(4)];
            isValid = isValidMove(mov);
        }
        return mov;
    }

    private boolean isValidMove(Movement mov) {
        switch (mov) {
            case UP:
                return this.blank.x != 0;
            case LEFT:
                return this.blank.y != 0;
            case DOWN:
                return this.blank.x != this.tamañoTablero - 1;
            case RIGHT:
                return this.blank.y != this.tamañoTablero - 1;
        }
        throw new RuntimeException("Error: Movement '" + mov.name() + "' not found");
    }

    public void move(Movement move) {
        int moveX, moveY;
        switch (move) {
            case UP:
                moveX = -1;
                moveY = 0;
                break;
            case LEFT:
                moveX = 0;
                moveY = -1;
                break;
            case DOWN:
                moveX = 1;
                moveY = 0;
                break;
            case RIGHT:
                moveX = 0;
                moveY = 1;
                break;
            default:
                throw new RuntimeException("Error: Movement '" + move.name() + "' not found");
        }
        // SWAP
        BufferedImage temp = puzzle[blank.x + moveX][blank.y + moveY];
        puzzle[blank.x + moveX][blank.y + moveY] = this.puzzle[blank.x][blank.y];
        puzzle[blank.x][blank.y] = temp;

        // Actualizamos la posición de blank
        blank.x += moveX;
        blank.y += moveY;
    }

    public void reset(int n) {
        this.tamañoTablero = n;
        this.puzzle = vista.getPuzzle().getPuzzle();
    }

    // GETTERS & SETTERS
    public View getVista() {
        return vista;
    }

    public void setVista(View vista) {
        this.vista = vista;
    }

    public void setPuzzle(BufferedImage[][] p) {
        this.puzzle = p;
    }

    public BufferedImage[][] getPuzle() {
        return this.puzzle;
    }

    public int getN() {
        return this.tamañoTablero;
    }

}
