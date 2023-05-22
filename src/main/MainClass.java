/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package main;

import view.View;

import mesurament.Mesurament;


/**
 * Clase principal desde la que iniciamos la aplicación.
 */
public class MainClass {

    private View vista;
     // Punter a la Vista del patró

    public static void main(String[] args) {
        //Mesurament.mesura();
        (new MainClass()).MVCInit();
    }

    /**
     * Establece los punteros entre las distintas clases del patrón MVC para que
     * se puedan comunicar entre ellas.
     */
    private void MVCInit() {
        vista = new View();
        vista.mostrar();
    }

}
