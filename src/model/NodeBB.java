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

    public int updateTotal() {
        
        //return updateTotalv1();
        //return updateTotalv2();
        return updateTotalv3();
    }
    
    public int updateTotalv1(){
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
    
    public int updateTotalv2(){
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
     
    public int updateTotalv3(){
        total = 0;
        good = 0;
        int index = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != index) {
                    int fila = tablero[i][j]/tablero.length;
                    int columna = tablero[i][j]%tablero.length;
                    
                    int x = fila - i;
                    int y = columna - j;
                    
                    total += Math.sqrt(x*x + y*y) * (((tablero.length - i) + (tablero.length - j)) * 20000);
                } else {
                    good++;
                }
                index++;
            }
        }
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
            int[][] t = nodeAux.move(UP, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
            list.add(nodeAux);
        }

        if (isValidMove(Movement.DOWN, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(DOWN, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
            list.add(nodeAux);
        }

        if (isValidMove(Movement.LEFT, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(LEFT, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
            list.add(nodeAux);
        }

        if (isValidMove(Movement.RIGHT, blank)) {
            nodeAux = new NodeBB();
            int[][] t = nodeAux.move(RIGHT, tablero.clone(), blank);
            nodeAux.setTablero(t);
            nodeAux.setNMov(nMov + 1);
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

    public void move(Movement movement) {
        Point blank = findBlank();
        int[][] t = this.move(movement, tablero, blank);
        this.setTablero(t);
        this.nMov++;
    }
    
    private int[][] move(Movement move, int[][] t, Point b) {
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
    

    // SOLVING ESQUINA
    private Point getEsquina(int size) {
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        
        for (int i = tablero.length - size; i < tablero.length; i++) {
            for (int j = tablero.length - size; j < tablero.length; j++) {
                if(tablero[i][j] == index) return new Point(i,j);
            }
        }
        
        return null;
    }

    public void updateTotalEsquina(int size) {
        if(modifiedBefore(size)){
            this.total = -1;
            return;
        }
        
        total = 0;
        
        Point piezaEsquina = getEsquina(size);
        Point blank = findBlank();
        Point esquina = new Point(tablero.length - size, tablero.length - size);
        
        int x = piezaEsquina.x - blank.x;
        int y = piezaEsquina.y - blank.y;

        double distancia = Math.sqrt(x*x + y*y);
        
        if(distancia > 2){
            total += 100000 * distancia;
        } else{
            x = esquina.x - piezaEsquina.x;
            y = esquina.y - piezaEsquina.y;
            total += 10000 * Math.sqrt(x*x + y*y);
            
            x = esquina.x - blank.x;
            y = esquina.y - blank.y;
            total += 100 * Math.sqrt(x*x + y*y);
        }
        
    }
    
    public boolean isSolvedEsquina(int size) {
        Point piezaEsquina = getEsquina(size);
        return tablero[tablero.length - size][tablero.length - size] == (tablero.length * piezaEsquina.x + piezaEsquina.y);
    }

    
    // SOLVING TOP ROW - 2
    public void updateTotalTop(int size) {   
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        Point esquina = new Point(tablero.length - size, tablero.length - size);
        
        if(modifiedBefore(size) | tablero[esquina.x][esquina.y] != index){
            total = -1;
            return;
        }

        index++;
        
        boolean rowIncomplete = false;
        int i = esquina.x;
        int j = esquina.y;
        for (j = esquina.y + 1; j < tablero.length - 2; ) {
            if(tablero[i][j] != index){
                rowIncomplete = true;
                break;
            }
            index++;
            j++;
        }
        
        total = 0;
        
        if(rowIncomplete){
            
            Point piezaToFix = findPieza(size, index);
            total = 100000 * (tablero.length - j);
        
            Point blank = findBlank();

            int x = piezaToFix.x - blank.x;
            int y = piezaToFix.y - blank.y;

            double distancia = Math.sqrt(x*x + y*y);

            if(distancia > 2){
                total += 10000 * distancia;
            } else{
                x = i - piezaToFix.x;
                y = j - piezaToFix.y;
                total += 1000 * Math.sqrt(x*x + y*y);

                x = i - blank.x;
                y = j - blank.y;
                total += 100 * Math.sqrt(x*x + y*y);
            }
            
        } 

    }
    
    private boolean modifiedTop(int size) {
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        int i = tablero.length - size;
        
        for (int j = tablero.length - size; j < tablero.length; j++) {
            if(tablero[i][j] != index) return true;
            index++;
        }
        
        return false;
    }

    public boolean isSolvedTop(int size) {
        return !modifiedTop(size);
    }

    
    // SOLVING LAST 2 SQUARES OF TOP ROW
    public void updateTotalTopEsquina(int size) {
         int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        Point esquina = new Point(tablero.length - size, tablero.length - size);
        
        if(modifiedBefore(size) | tablero[esquina.x][esquina.y] != index){
            total = -1;
            return;
        }

        index++;
        
        int i = esquina.x;
        int j;
        for (j = esquina.y + 1; j < tablero.length - 2; ) {
            if(tablero[i][j] != index){
                total = -1;
                return;
            }
            index++;
            j++;
        }
        
        total = 0;
        
        if(tablero[i][j+1] != index){
            Point piezaToFix = findPieza(size, index);
            total = 200000;

            Point blank = findBlank();

            int x = piezaToFix.x - blank.x;
            int y = piezaToFix.y - blank.y;

            double distancia = Math.sqrt(x*x + y*y);

            if(distancia > 2){
                total += 10000 * distancia;
            } else{
                x = i - piezaToFix.x;
                y = j - piezaToFix.y;
                total += 1000 * Math.sqrt(x*x + y*y);

                x = i - blank.x;
                y = j - blank.y;
                total += 100 * Math.sqrt(x*x + y*y);
            }
            return;
        }
        
        index++;
        
        if(tablero[i+1][j+1] != index){
            total = 100000;
            Point piezaToFix = findPieza(size, index);

            Point blank = findBlank();

            int x = piezaToFix.x - blank.x;
            int y = piezaToFix.y - blank.y;

            double distancia = Math.sqrt(x*x + y*y);

            if(distancia > 2){
                total += 10000 * distancia;
            } else{
                x = i - piezaToFix.x;
                y = j - piezaToFix.y;
                total += 1000 * Math.sqrt(x*x + y*y);

                x = i - blank.x;
                y = j - blank.y;
                total += 100 * Math.sqrt(x*x + y*y);
            }
            
            return;
        }
        
        if(tablero[i][j] != -1){
            Point piezaToFix = findBlank();

            int x = i - piezaToFix.x;
            int y = j - piezaToFix.y;
            total += 1000 * Math.sqrt(x*x + y*y);
            
            return;
        }
        
        this.move(Movement.RIGHT);
        this.move(Movement.DOWN);
    }

    public boolean isSolvedTopRow(int size) {
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        int i = tablero.length - size;
        
        for (int j = tablero.length - size; j < tablero.length - 2; j++) {
            if(tablero[i][j] != index) return false;
            index++;
        }
        
        return true;
    }    
    

    // SOLVING COLUMN - 2
    public void updateTotalLeft(int size) {
        if(modifiedBefore(size) | modifiedTop(size)){
            total = -1;
            return;
        }
                
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        Point esquina = new Point(tablero.length - size, tablero.length - size);
        
        boolean colIncomplete = false;
        int i = esquina.x;
        int j = esquina.y;
        for (i = esquina.x ; i < tablero.length - 2; ) {
            if(tablero[i][j] != index){
                colIncomplete = true;
                break;
            }
            index += tablero.length;
            i++;
        }
        
        total = 0;
        
        if(colIncomplete){
            
            Point piezaToFix = findPieza(size, index);
            total = 100000 * (tablero.length - i);
        
            Point blank = findBlank();

            int x = piezaToFix.x - blank.x;
            int y = piezaToFix.y - blank.y;

            double distancia = Math.sqrt(x*x + y*y);

            if(distancia > 2){
                total += 10000 * distancia;
            } else{
                x = i - piezaToFix.x;
                y = j - piezaToFix.y;
                total += 1000 * Math.sqrt(x*x + y*y);

                x = i - blank.x;
                y = j - blank.y;
                total += 100 * Math.sqrt(x*x + y*y);
            }
            
        } 
        
    }
    
    public boolean isSolvedLeftColumn(int size) {
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        int j = tablero.length - size;
        
        for (int i = tablero.length - size; i < tablero.length - 2; i++) {
            if(tablero[i][j] != index) return false;
            index += tablero.length;
        }
        
        return true;
    }
    
    
    // SOLVING LAST 2 SQUARES OF LEFT COLUMN
    public void updateTotalLeftEsquina(int size) {
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        Point esquina = new Point(tablero.length - size, tablero.length - size);
        
        if(modifiedBefore(size)){
            total = -1;
            return;
        }
        
        int i = esquina.x;
        int j = esquina.y;
        for (i = esquina.x; i < tablero.length - 2; ) {
            if(tablero[i][j] != index){
                total = -1;
                return;
            }
            index += tablero.length;
            i++;
        }
        
        total = 0;
        
        if(tablero[i+1][j] != index){
            Point piezaToFix = findPieza(size, index);
            total = 200000;

            Point blank = findBlank();

            int x = piezaToFix.x - blank.x;
            int y = piezaToFix.y - blank.y;

            double distancia = Math.sqrt(x*x + y*y);

            if(distancia > 2){
                total += 10000 * distancia;
            } else{
                x = i - piezaToFix.x;
                y = j - piezaToFix.y;
                total += 1000 * Math.sqrt(x*x + y*y);

                x = i - blank.x;
                y = j - blank.y;
                total += 100 * Math.sqrt(x*x + y*y);
            }
            return;
        }
        
        index += tablero.length;
        
        if(tablero[i+1][j+1] != index){
            total = 100000;
            Point piezaToFix = findPieza(size, index);

            Point blank = findBlank();

            int x = piezaToFix.x - blank.x;
            int y = piezaToFix.y - blank.y;

            double distancia = Math.sqrt(x*x + y*y);

            if(distancia > 2){
                total += 10000 * distancia;
            } else{
                x = i - piezaToFix.x;
                y = j - piezaToFix.y;
                total += 1000 * Math.sqrt(x*x + y*y);

                x = i - blank.x;
                y = j - blank.y;
                total += 100 * Math.sqrt(x*x + y*y);
            }
            
            return;
        }
        
        if(tablero[i][j] != -1){
            Point piezaToFix = findBlank();

            int x = i - piezaToFix.x;
            int y = j - piezaToFix.y;
            total += 1000 * Math.sqrt(x*x + y*y);
            
            return;
        }
        
        this.move(Movement.DOWN);
        this.move(Movement.RIGHT);
    }
    
    public boolean isSolvedLeft(int size) {
        int index = tablero.length *  (tablero.length - size) + (tablero.length - size);
        int j = tablero.length - size;
        
        for (int i = tablero.length - size; i < tablero.length; i++) {
            if(tablero[i][j] != index) return false;
            index += tablero.length;
        }
        
        return true;    }

    
    // CHECKS IF THE ROW AND COLUMN FROM BEFORE HAS BEEN MODIFIED
    private boolean modifiedBefore(int size){
        if(size == tablero.length) return false;

        int index = tablero.length *  (tablero.length - size -1) + (tablero.length - size -1);
        int i = tablero.length - size - 1;
        
        for (int j = tablero.length - size - 1; j < tablero.length; j++) {
            if(tablero[i][j] != index) return true;
            index++;
        }
        
        index = tablero.length *  (tablero.length - size -1) + (tablero.length - size -1);
        i = tablero.length - size - 1;
        
        for (int j = tablero.length - size - 1; j < tablero.length; j++) {
            if(tablero[j][i] != index) return true;
            index += tablero.length;
        }

        return false;
    }

    
    // SEARCHS A PIECE IN A SUB-PUZZLE
    private Point findPieza(int size, int index) {
        for (int i = tablero.length - size; i < tablero.length; i++) {
            for (int j = tablero.length - size; j < tablero.length; j++) {
                if(tablero[i][j] == index) return new Point(i,j);
            }
        }
        
        return null;
    }

    
    //GENERALES
    public static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null)
            return null;
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
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

    
    // GETTERS AND SETTERS
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
    
    public int getPoints() {
        return total;
    }
    
    public int getGood(){
        return good;
    }

}
