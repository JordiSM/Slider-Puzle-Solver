/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package main;

import static java.lang.Thread.sleep;
import view.View;

import mesurament.Mesurament;
import model.Model;


/**
 * Clase principal desde la que iniciamos la aplicación.
 */
public class MainClass {

    private View vista;
     // Punter a la Vista del patró

    public static void main(String[] args) throws InterruptedException, Exception {
        //Mesurament.mesura();
        (new MainClass()).MVCInit();
    }

    /**
     * Establece los punteros entre las distintas clases del patrón MVC para que
     * se puedan comunicar entre ellas.
     */
    private void MVCInit() throws InterruptedException, Exception {
        vista = new View();
        vista.mostrar();
        sleep(2200);
        Model model = new Model(vista, 4);
        model.randomize();
        sleep(2200);
        vista.getPuzzle().cambiaPieza(1, 0);
        sleep(1200);
        vista.getPuzzle().cambiaPieza(1, 1);
        sleep(1200);
        vista.getPuzzle().cambiaPieza(0, 1);
        sleep(1200);
        vista.getPuzzle().cambiaPieza(0, 0);
    }

}
