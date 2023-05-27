/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package controller;


import model.Model;
import model.Movement;
import view.View;

/**
 * Controlador de la aplicación, aquí se procesarán las funciones y los cálculos
 * de la aplicación.
 */
public class Controller implements Runnable {

    // PUNTEROS DEL PATRÓN MVC
    private Model modelo;
    private View vista;
   

    // CONSTRUCTORS
    public Controller() {
    }

    public Controller(Model modelo, View vista) {
        this.modelo = modelo;
        this.vista = vista;
    }
    
    @Override
    public void run() {
        int type = 1;
        switch(type){
            case 1 -> Aleatorio();
            case 2 -> Probabilistico();
            case 3 -> BranchAndBound();
            case 4 -> Estrategico();
        }
        // Algoritmo de resolución
    }
    
    public void Aleatorio(){
        //Mientras no Resulto
            //generar mov aleatorio
            //cambiar puzzle
        
        int count = 0;
        while(!modelo.isSolved()){
            modelo.move(modelo.getRandomMove());
            count++;
        }
        System.out.println("Solution found in " + count + " moves.");

    }
    
    public void Probabilistico(){
        //Mientras no Resuelto
            //generar mov aleatorio
            //si NO es el inverso al aterior (UP,DOWN) (LEFT,RIGHT)
                //cambiar puzzle
                
        int count = 0;      //TOTAL OF VALID MOVES
        int totalMoves = 0; //TOTAL OF MOVES GENERATED
        Movement movAnterior = Movement.LEFT; //First Movement cant be RIGHT
        Movement mov;
        
        while(!modelo.isSolved()){
            mov = modelo.getRandomMove();
            totalMoves++;
            if(!Movement.isInverse(mov, movAnterior)){
                modelo.move(modelo.getRandomMove());
                count++;
                movAnterior = mov;
            }
        }
        System.out.println("Solution found in " + count + " moves.");
        System.out.println("    Moves stalved:" + (totalMoves - count));
        
    }
    
    public void BranchAndBound(){
        // DIAPOSITIVAS DE CLASE
    }
    
    public void Estrategico(){
        // ENCONTRADO ONLINE
    } 
    
    


    // CLASS METHODS
    // SETTERS & GETTERS

    public Model getModelo(Model modelo){
        return this.modelo;
    }

    public void setModelo(Model modelo) {
        this.modelo = modelo;
    }

    public View getVista() {
        return this.vista;
    }

    public void setVista(View vista) {
        this.vista = vista;
    }

}
