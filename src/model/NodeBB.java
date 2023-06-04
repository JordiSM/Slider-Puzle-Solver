/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import static model.Movement.DOWN;
import static model.Movement.LEFT;
import static model.Movement.RIGHT;
import static model.Movement.UP;

public class NodeBB implements Comparable<NodeBB> {

    int nMov;
    int[][] tablero;
    int total;

    public NodeBB() {
        total = 0;
    }

    public void setTablero(int[][] t) {
        this.tablero = t;
    }

    public int[][] getTablero() {
        return this.tablero;
    }

    public void setNMov(int n) {
        this.nMov = n;
    }

    public int getnMov() {
        return this.nMov;
    }

    public int updateTotal() {
        total = nMov;
        int index = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] == index) {
                    total += 100;
                }
            }
        }
        return total;
    }

    public int getPoints() {
        return total;
    }

    public boolean isSolved() {
        int index = 0;
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero.length; j++) {

                if (this.tablero[i][j] != index & !(i == tablero.length - 1 & j == tablero.length - 1)) {
                    return false;
                }

                index++;
            }
        }
        return true;
    }

    public ArrayList<NodeBB> generateOptions() {
        ArrayList<NodeBB> list = new ArrayList<>();
        Point blank = findBlank();

        NodeBB nodeAux;
        if (isValidMove(Movement.UP, blank)) {
            nodeAux = new NodeBB();
            nodeAux.setTablero(Model.move(UP, tablero, blank));
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        if (isValidMove(Movement.DOWN, blank)) {
            nodeAux = new NodeBB();
            nodeAux.setTablero(Model.move(DOWN, tablero, blank));
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        if (isValidMove(Movement.LEFT, blank)) {
            nodeAux = new NodeBB();
            nodeAux.setTablero(Model.move(LEFT, tablero, blank));
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        if (isValidMove(Movement.RIGHT, blank)) {
            nodeAux = new NodeBB();
            nodeAux.setTablero(Model.move(RIGHT, tablero, blank));
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        return list;
    }

    private boolean isValidMove(Movement movement, Point blank) {
        switch (movement) {
            case UP:
                return blank.x != 0;
            case LEFT:
                return blank.y != 0;
            case DOWN:
                return blank.x != this.tablero.length - 1;
            case RIGHT:
                return blank.y != this.tablero.length - 1;
        }
        return false;
    }

    private Point findBlank() {
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero.length; j++) {
                if (this.tablero[i][j] == -1) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    @Override
    public int compareTo(NodeBB t) {
        if (this.total > t.getPoints()) {
            return 1;
        } else if (this.total < t.getPoints()) {
            return -1;
        } else {
            return 0;
        }
    }

}
