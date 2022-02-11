/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAW.arkanoid.modelo;

import javafx.geometry.Point2D;

/**
 *
 * @author Manuel
 */

public class Ladrillo {
    
    public static int ancho = 32;
    public static int alto = 16;
    private int dureza;
    private boolean fijo;
    private Point2D posicion;
    
    public Ladrillo(){
        this.posicion= new Point2D(0,0);
        this.dureza = 2;
        this.fijo = false;
    }
    
    public Ladrillo(Point2D posicion, int dureza){
        this.posicion = posicion;
        this.dureza = dureza;
    }
    
    public Point2D getPosicion() {
        return posicion;
    }
    
    public int getAlto(){
        return alto;
    }
    
    public int getAncho(){
        return ancho;
    }
    
    public void setAlto(int alto){
        this.alto = alto;
    }
    
    public void setAncho(int ancho){
        this.ancho = ancho;
    }
    
    public int getDureza(){
        return dureza;
    }
    
    public void setDureza(int dureza){
        this.dureza = dureza;
    }
    
    public boolean getFijo(){
        return fijo;
    }
    
    public void setFijo(boolean fijo){
        this.fijo = fijo;
    }
}
