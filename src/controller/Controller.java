/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package controller;

import java.util.ArrayList;
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

    }

    public void Probabilistico() {
        //Mientras no Resuelto
        //  generar mov aleatorio
        //  si NO es el inverso al aterior (UP,DOWN) (LEFT,RIGHT)
        //      cambiar puzzle

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

    }

    public void BranchAndBound() {
        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotal();

        ArrayList<NodeBB> nodeOptions;
        int iterations = 0;

        while (!node.isSolved()) {
            nodeOptions = node.generateOptions();
            for (NodeBB option : nodeOptions) {
                queue.add(option);
            }
            node = queue.poll();

        }

        System.out.println("Solution found in " + node.getnMov() + " moves.");

    }

    public void Estrategico() {
        // ENCONTRADO ONLINE
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
