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
        long tiempoI = System.nanoTime();
        int count = 0;
        switch (type) {
            case 1:
                count = Aleatorio();
                break;
            case 2:
                count = Probabilistico();
                break;
            case 3:
                count = BranchAndBound();
                break;
            case 4:
                count = Estrategico();
                break;
        }
        if (modelo.isSolved()) {
            this.vista.getrightPanel().setTimetimePanel(System.nanoTime() - tiempoI);
            this.vista.getrightPanel().setMovimientos(count);
            this.vista.repaint();
            
        }
        // Algoritmo de resolución
    }

    public int Aleatorio() {
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

        return count;
    }

    public int Probabilistico() {
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

        return count;
    }

    public int BranchAndBound() {
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
            if(this.modelo.getN() < 2){
                this.modelo.setTablero(node.getTablero());
                this.vista.getPuzzle().repaint();            }
            
            if(queue.size() > 100000){
               List<NodeBB> listaElementos = new ArrayList<>(queue);
               listaElementos.subList(25000, 100000).clear();
               queue.clear();
               queue.addAll(listaElementos);
               System.out.println("Limpieza " + ++limpiezas + " - Node: " + node.getGood() + " ; " + node.getPoints());
               
               this.modelo.setTablero(node.getTablero());
               this.vista.getPuzzle().repaint();
               
               if(limpiezas > 100) return -1;
                
            }
            
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
            
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                option.updateTotal();
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

        return node.getnMov();

    }

    public int Estrategico() {
        int tamPuzle = modelo.getN();
        int sizeUnresolved = tamPuzle;
        
        int count = 0;
        
        while(sizeUnresolved > 4){
            count += resolveEsquina(sizeUnresolved);
            count += resolveTopRow(sizeUnresolved);
            count += resolveTopEsquina(sizeUnresolved);
            count += resolveLeftColumn(sizeUnresolved);
            count += resolveLeftEsquina(sizeUnresolved);
            sizeUnresolved--;
        }
        
        count += BranchAndBound();
        return count;
    }
    
    private int resolveEsquina(int size){
        System.out.println("\nStarted Esquina " + size);
        int count = 0;
        
        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        HashMap<String,NodeBB> map = new HashMap();
        
        ArrayList<NodeBB> nodeOptions;
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotalEsquina(size);
                
        System.out.println("Nodo Inicial: " + node.getGood() + " ; " + node.getPoints());
        while (!node.isSolvedEsquina(size)) {
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
                        
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                option.updateTotalEsquina(size);
                if(option.getPoints() != -1 & !map.containsKey(option.getKey())){
                    queue.add(option);
                } 
            }   
            
            node = queue.poll();
        }
        
        System.out.println("Solved " + node.getPoints());
        modelo.setTablero(node.getTablero());
        vista.getPuzzle().repaint();
        
        return count;
    }
    
    private int resolveTopRow(int size){
        System.out.println("\nStarted Top " + size);

        int count = 0;
        int limpiezas = 0;
        
        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        HashMap<String,NodeBB> map = new HashMap();
        
        ArrayList<NodeBB> nodeOptions;
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotalTopV2(size);
                
        System.out.println("Nodo Inicial: " + node.getGood() + " ; " + node.getPoints());
        while (!node.isSolvedTopRow(size)) {
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
            //System.out.println("Nodo Actual: " + node.getGood() + "; " + node.getPoints());

            if(queue.size() > 100000){
               List<NodeBB> listaElementos = new ArrayList<>(queue);
               listaElementos.subList(25000, 100000).clear();
               queue.clear();
               queue.addAll(listaElementos);
               System.out.println("Limpieza " + ++limpiezas + " - Node: " + node.getGood() + " ; " + node.getPoints());
               
               this.modelo.setTablero(node.getTablero());
               this.vista.getPuzzle().repaint();
               
               if(limpiezas > 100) return -1;
                
            }
            
            
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                option.updateTotalTopV2(size);
                if(option.getPoints() != -1 & !map.containsKey(option.getKey())){
                    //System.out.println(option.getGood() + " - " + option.getPoints());
                    //System.out.println(map.size());
                    //map.put(option.getKey(), option);
                    queue.add(option);
                } 
            }   
            
            node = queue.poll();
        }
        System.out.println("Solved " + node.getPoints());
        modelo.setTablero(node.getTablero());
        vista.getPuzzle().repaint();
        
        return count;
    }
    
    private int resolveTopEsquina(int size){
        System.out.println("\nStarted Top Esquina " + size);

        int count = 0;
        int limpiezas = 0;
        
        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        HashMap<String,NodeBB> map = new HashMap();
        
        ArrayList<NodeBB> nodeOptions;
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotalTopEsquina(size);
        
        System.out.println("Nodo Inicial: " + node.getGood() + " ; " + node.getPoints());
        while (!node.isSolvedTop(size)) {
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
            //System.out.println("Nodo Actual: " + node.getGood() + "; " + node.getPoints());

            if(queue.size() > 100000){
               List<NodeBB> listaElementos = new ArrayList<>(queue);
               listaElementos.subList(25000, 100000).clear();
               queue.clear();
               queue.addAll(listaElementos);
               System.out.println("Limpieza " + ++limpiezas + " - Node: " + node.getGood() + " ; " + node.getPoints());
               
               //this.modelo.setTablero(node.getTablero());
               //this.vista.getPuzzle().repaint();
               
               if(limpiezas > 100) return -1;
                
            }
            
            
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                option.updateTotalTopEsquina(size);
                if(option.getPoints() != -1 & !map.containsKey(option.getKey())){
                    queue.add(option);
                } 
            }   
            
            node = queue.poll();
        }

        System.out.println("Solved " + node.getPoints());
        modelo.setTablero(node.getTablero());
        vista.getPuzzle().repaint();
        
        return count;
    }

    private int resolveLeftColumn(int size){
        System.out.println("\nStarted LeftColumn " + size);

        int count = 0;
        int limpiezas = 0;
        
        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        HashMap<String,NodeBB> map = new HashMap();
        
        ArrayList<NodeBB> nodeOptions;
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotalLeft(size);
                
        System.out.println("Nodo Inicial: " + node.getGood() + " ; " + node.getPoints());
        while (!node.isSolvedLeftColumn(size)) {
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
            //System.out.println("Nodo Actual: " + node.getGood() + "; " + node.getPoints());

            if(queue.size() > 100000){
               List<NodeBB> listaElementos = new ArrayList<>(queue);
               listaElementos.subList(25000, 100000).clear();
               queue.clear();
               queue.addAll(listaElementos);
               System.out.println("Limpieza " + ++limpiezas + " - Node: " + node.getGood() + " ; " + node.getPoints());
               
               this.modelo.setTablero(node.getTablero());
               this.vista.getPuzzle().repaint();
               
               if(limpiezas > 100) return -1;
                
            }
            
            
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                option.updateTotalLeft(size);
                if(option.getPoints() != -1 & !map.containsKey(option.getKey())){
                    //System.out.println(option.getGood() + " - " + option.getPoints());
                    //System.out.println(map.size());
                    //map.put(option.getKey(), option);
                    queue.add(option);
                } 
            }   
            
            node = queue.poll();
        }
        System.out.println("Solved " + node.getPoints());
        modelo.setTablero(node.getTablero());
        vista.getPuzzle().repaint();
        
        return count;
    }

    private int resolveLeftEsquina(int size){
        System.out.println("\nStarted Left Esquina " + size);

        int count = 0;
        int limpiezas = 0;
        
        PriorityQueue<NodeBB> queue = new PriorityQueue<>();
        HashMap<String,NodeBB> map = new HashMap();
        
        ArrayList<NodeBB> nodeOptions;
        NodeBB node = new NodeBB();
        node.setNMov(0);
        node.setTablero(modelo.getPuzle());
        node.updateTotalLeftEsquina(size);
        
        System.out.println("Nodo Inicial: " + node.getGood() + " ; " + node.getPoints());
        while (!node.isSolvedLeft(size)) {
            if(map.containsKey(node.getKey())){
                 node = queue.poll();
                 continue;
            }
            //System.out.println("Nodo Actual: " + node.getGood() + "; " + node.getPoints());

            if(queue.size() > 100000){
               List<NodeBB> listaElementos = new ArrayList<>(queue);
               listaElementos.subList(25000, 100000).clear();
               queue.clear();
               queue.addAll(listaElementos);
               System.out.println("Limpieza " + ++limpiezas + " - Node: " + node.getGood() + " ; " + node.getPoints());
               
               //this.modelo.setTablero(node.getTablero());
               //this.vista.getPuzzle().repaint();
               
               if(limpiezas > 100) return -1;
                
            }
            
            
            map.put(node.getKey(), node);
            nodeOptions = node.generateOptions();
            
            for (NodeBB option : nodeOptions) {
                option.updateTotalLeftEsquina(size);
                if(option.getPoints() != -1 & !map.containsKey(option.getKey())){
                    queue.add(option);
                } 
            }   
            
            node = queue.poll();
        }

        System.out.println("Solved " + node.getPoints());
        modelo.setTablero(node.getTablero());
        vista.getPuzzle().repaint();
        
        return count;
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
