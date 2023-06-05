/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import static model.Movement.*;
import view.View;

/**
 * Modelo de la aplicación, aquí se guardan todos los datos necesarios para su
 * correcta operación.
 */
public class Model {

    // PUNTEROS DEL PATRÓN MVC
    private View vista;
    private ArrayList<Object> solution; //Lista ordenada de movimientos
    private int[][] puzzle;
    private int tamañoTablero;
    private Point blank;

    // CONSTRUCTORS
    public Model(View vista, int n) {
        this.vista = vista;
        this.tamañoTablero = n;
        this.puzzle = newTablero(n);
        this.randomize();
    }

    public Model(View vista) {
        this.vista = vista;
        this.tamañoTablero = 3;
        this.puzzle = newTablero(3);

    }

    // CLASS METHODS
    private int[][] newTablero(int n) {
        int[][] tablero = new int[n][n];
        int index = 0;

        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {
                tablero[i][j] = index++;
            }
        }

        tablero[n - 1][n - 1] = -1;
        return tablero;
    }

    public void randomize() {
        this.blank = this.findBlank();
        this.printPuzzle();

        Random rnd = new Random();
        System.out.println("Tamaño Tablero = " + tamañoTablero);
        Movement moveBefore = Movement.UP;
        for(int i = 0; i < 500; ){
            Movement move = this.getRandomMove();
            if(this.isValidMove(move) & !Movement.isInverse(move, moveBefore)){
                this.move(move);
                moveBefore = move;
                i++;
            }            
        }

        this.printPuzzle();
    }

    public boolean isSolved() {
        int index = 0;
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {

                if (this.puzzle[i][j] != index & !(i == tamañoTablero - 1 & j == tamañoTablero - 1)) {
                    return false;
                }

                index++;
            }
        }
        return true;
    }

    private Point findBlank() {
        for (int i = 0; i < this.tamañoTablero; i++) {
            for (int j = 0; j < this.tamañoTablero; j++) {
                if (this.puzzle[i][j] == -1) {
                    return new Point(i, j);
                }
            }
        }
        return null;
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
        int temp = puzzle[blank.x + moveX][blank.y + moveY];
        puzzle[blank.x + moveX][blank.y + moveY] = this.puzzle[blank.x][blank.y];
        puzzle[blank.x][blank.y] = temp;

        // Actualizamos la posición de blank
        blank.x += moveX;
        blank.y += moveY;
    }

    public void reset(int n) {
        this.tamañoTablero = n;
        this.puzzle = this.newTablero(n);
    }

    // GETTERS & SETTERS
    public View getVista() {
        return vista;
    }

    public void setVista(View vista) {
        this.vista = vista;
    }

    public int[][] getPuzle() {
        return this.puzzle;
    }

    public int getN() {
        return this.tamañoTablero;
    }

    public String printPuzzle() {
        String s = "";
        for (int i = 0; i < this.tamañoTablero; i++) {
            s += "\n";

            for (int j = 0; j < this.tamañoTablero; j++) {
                s += "\t" + this.puzzle[i][j];
            }
        }
        System.out.println(s);
        return s;
    }

    public void setTablero(int[][] tablero) {
        this.puzzle = tablero;
    }

}
