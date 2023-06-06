/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import model.Model;
import model.Movement;
import model.NodeBB;
import view.View;

/**
 * Controlador de la aplicación, aquí se procesarán las funciones y los cálculos
 * de la aplicación.
 */
public class Controller implements Runnable {

    // PUNTEROS DEL PATRÓN MVC
    private Model modelo;
    private View vista;
    private int type = 1;

    // CONSTRUCTORS
    public Controller() {
    }

    public Controller(Model modelo, View vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    @Override
    public void run() {
        switch (type) {
            case 1:
                Aleatorio();
                break;
            case 2:
                Probabilistico();
                break;
            case 3:
                BranchAndBound();
                break;
            case 4:
                Estrategico();
                break;
        }
        if (modelo.isSolved()) {
            this.vista.repaint();
        }
        // Algoritmo de resolución
    }

    public void Aleatorio() {
        long tiempoI = System.nanoTime();
        //Mientras no Resulto
        //  generar mov aleatorio
        //  cambiar puzzle
        System.out.println("Started aleatorio");
        int count = 0;
        while (!modelo.isSolved()) {
            modelo.move(modelo.getRandomMove());
            count++;
        }
        System.out.println("Solution found in " + count + " moves.");
        this.vista.getrightPanel().setTimetimePanel(System.nanoTime() - tiempoI);
        this.vista.getrightPanel().setMovimientos(count);
    }

    public void Probabilistico() {
        long tiempoI = System.nanoTime();
        //Mientras no Resuelto
        //  generar mov aleatorio
        //  si NO es el inverso al aterior (UP,DOWN) (LEFT,RIGHT)
        //      cambiar puzzle
        System.out.println("Started probabilistico");
        int count = 0;      //TOTAL OF VALID MOVES
        int totalMoves = 0; //TOTAL OF MOVES GENERATED
        Movement movAnterior = Movement.LEFT; //First Movement cant be RIGHT
        Movement mov;

        while (!modelo.isSolved()) {
            mov = modelo.getRandomMove();
            totalMoves++;
            if (!Movement.isInverse(mov, movAnterior)) {
                modelo.move(mov);
                count++;
                movAnterior = mov;
            }
        }
        System.out.println("Solution found in " + count + " moves.");
        System.out.println("    Moves stalved:" + (totalMoves - count));
        this.vista.getrightPanel().setTimetimePanel(System.nanoTime() - tiempoI);
        this.vista.getrightPanel().setMovimientos(count);
    }

    public void BranchAndBound() {
        long tiempoI = System.nanoTime();
        System.out.println("Started B&B");

        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        HashMap<String,NodeBB> map = new HashMap();
        
        ArrayList<NodeBB> nodeOptions;
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotal();
        
        //map.put(node.getKey(), node);
        
        System.out.println("Nodo Inicial: " + node.getGood() + " ; " + node.getPoints());

        int limpiezas = 0;
        while (!node.isSolved()) {
            
            
            if(queue.size() > 100000){
               List<NodeBB> listaElementos = new ArrayList<>(queue);
               listaElementos.subList(25000, 100000).clear();
               queue.clear();
               queue.addAll(listaElementos);
               System.out.println("Limpieza " + ++limpiezas + " - Node: " + node.getGood() + " ; " + node.getPoints());
               
               this.modelo.setTablero(node.getTablero());
               this.vista.getPuzzle().repaint();
               
               if(limpiezas > 100) return;
                
            }
            
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
            
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                if(!map.containsKey(option.getKey())){
                    //System.out.println(option.getGood() + " - " + option.getPoints());
                    //System.out.println(map.size());
                    //map.put(option.getKey(), option);
                    queue.add(option);
                } 
            }   
            
            node = queue.poll();
        }
        modelo.setTablero(node.getTablero());

        System.out.println("Solution found in " + node.getnMov() + " moves.");
        this.vista.getrightPanel().setTimetimePanel(System.nanoTime() - tiempoI);
        this.vista.getrightPanel().setMovimientos(node.getnMov());

    }

    public void Estrategico() {
        long tiempoI = System.nanoTime();
        int[][] tablero = modelo.getPuzle();
        int tamPuzle = tablero.length;
        
        int rowsToSolve = tamPuzle - 3;
        
        
        /*
        this.vista.getrightPanel().setTimetimePanel(System.nanoTime() - tiempoI);
        this.vista.getrightPanel().setMovimientos(count);
        */
    }

    // CLASS METHODS
    // SETTERS & GETTERS
    public Model getModelo(Model modelo) {
        return this.modelo;
    }

    public void setModelo(Model modelo) {
        this.modelo = modelo;
    }

    public View getVista() {
        return this.vista;
    }

    public void setType(int i) {
        this.type = i;
    }

    public void setVista(View vista) {
        this.vista = vista;
    }

}
