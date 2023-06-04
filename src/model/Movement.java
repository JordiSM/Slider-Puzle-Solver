/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package model;

public enum Movement {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public static boolean isInverse(Movement m1, Movement m2) {
        return (m1.ordinal() + 2) % 4 == m2.ordinal();
    }
}
