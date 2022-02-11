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
public class Nivel {

    

    private Ladrillo[][] ladrillo;
    int nivel;
    private Point2D inicio;
    private Point2D fondo;

    public Nivel() {

    }

    public Nivel(Point2D inicio, int[][] matriz) {
        this.inicio = inicio;
        this.init(matriz);
    }

    public void init(int matriz[][]) {
        Point2D tempo;
        int inicioX = 0;
        int inicioY = 0;

        if (this.getInicio() != null) {
            inicioX = (int) this.getInicio().getX();
            inicioY = (int) this.getInicio().getY();
        }

        this.ladrillo = new Ladrillo[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                tempo = new Point2D(inicioX + j * Ladrillo.ancho, inicioY + i * Ladrillo.alto);
                this.ladrillo[i][j] = new Ladrillo(tempo, matriz[i][j]);
            }
        }
    }

    public Ladrillo[][] getLadrillos() {
        return this.ladrillo;
    }

    public boolean haTerminado() {
        boolean terminado = true;

        for (int i = 0; i < this.getLadrillos().length; i++) {
            for (int j = 0; j < this.getLadrillos()[i].length; j++) {
                if (this.getLadrillos()[i][j].getDureza() > 0) {
                    terminado = false;
                }
            }
        }

        return terminado;
    }

    /**
     * @return the fondo
     */
    public Point2D getFondo() {
        return fondo;
    }

    /**
     * @param fondo the fondo to set
     */
    public void setFondo(Point2D fondo) {
        this.fondo = fondo;
    }
    
    /**
     * @return the inicio
     */
    public Point2D getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Point2D inicio) {
        this.inicio = inicio;
    }

}
