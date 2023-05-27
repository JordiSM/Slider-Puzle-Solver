/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author jordo
 */
public enum Movement {
    UP,
    RIGHT,
    DOWN,
    LEFT;
    
    public static boolean isInverse(Movement m1, Movement m2){
        return (m1.ordinal() + 2) % 4 == m2.ordinal();
    }
}


