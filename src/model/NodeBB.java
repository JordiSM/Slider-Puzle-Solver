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

    String key;
    int nMov;
    int[][] tablero;
    int total;
    int good;

    public NodeBB() {
        total = 0;
        good = 0;
        key = null;
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
        
        return updateTotalv1();
        //return updateTotalv2();
    }

    public int updateTotalv1(){
        total = nMov;
        good = 0;
        int index = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != index) {
                    int fila = tablero[i][j]/tablero.length;
                    int columna = tablero[i][j]%tablero.length;
                    
                    int x = fila - i;
                    int y = columna - j;
                    
                    total += Math.sqrt(x*x + y*y) * 100000;
                } else {
                    good++;
                }
                index++;
            }
        }
        return total;
    }
    
    public int updateTotalv2(){
        total = nMov;
        good = 0;
        int index = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != index) {
                    total += 100000;
                } else {
                    good++;
                }
                index++;
            }
        }
        return total;
    }
    
    public int getPoints() {
        return total;
    }
    
    public int getGood(){
        return good;
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
        Model model = null;
        
        ArrayList<NodeBB> list = new ArrayList<>();
        Point blank = findBlank();

        NodeBB nodeAux;
        if (isValidMove(Movement.UP, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(UP, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        if (isValidMove(Movement.DOWN, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(DOWN, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        if (isValidMove(Movement.LEFT, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(LEFT, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
            nodeAux.updateTotal();
            list.add(nodeAux);
        }

        if (isValidMove(Movement.RIGHT, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(RIGHT, tablero.clone(), blank);
            nodeAux.setTablero(t);
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

    public int[][] move(Movement move, int[][] t, Point b) {
        int tablero[][] = deepCopyIntMatrix(t);
        Point blank = (Point) b.clone();
        
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
        int temp = tablero[blank.x + moveX][blank.y + moveY];
        tablero[blank.x + moveX][blank.y + moveY] = tablero[blank.x][blank.y];
        tablero[blank.x][blank.y] = temp;

        // Actualizamos la posición de blank
        blank.x += moveX;
        blank.y += moveY;

        return tablero;
    }
    
    public static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null)
            return null;
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
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

    public String getKey() {
        if(this.key != null) return key;
        
        key = "";
        for (int i = 0; i < this.tablero.length; i++) {
            for (int j = 0; j < this.tablero.length; j++) {
                key += ""  + (char) (65 + this.tablero[i][j]);
            }
        }
        return key;
    }

}
