/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAW.arkanoid.interfaz;

import DAW.arkanoid.modelo.Barra;
import DAW.arkanoid.modelo.Campo;
import DAW.arkanoid.modelo.Ladrillo;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Manuel
 */
public class LadrilloUI {

    private Ladrillo ladrillo[];
    private int estado;
    private Campo campo;
    private static int NUMESTADOS = 6;
    private Point2D dibujoladrillo[];
    private Image imagen;
    private static int colorladrillos[][][] = {
        { //blanco
            {0, 0}
        },
        { //naranja
            {16, 0}
        },
        { //celeste
            {32, 0}
        },
        { //verde
            {48, 0}
        },
        { //rojo
            {0, 8}
        },
        { //azul
            {16, 8}
        },
        { //violeta
            {32, 8}
        },
        { //amarillo
            {48, 8}
        },
        { //ris
            {0, 16}
        },
        { //dorado
            {0, 24}
        },};

    public LadrilloUI(Image imagen, Ladrillo ladrillo[]) {

        this.mapear();
        this.imagen = imagen;
        this.ladrillo = ladrillo;
        

    }

    private void mapear() {
        this.dibujoladrillo = new Point2D[LadrilloUI.NUMESTADOS];
        for (int i = 0; i < this.dibujoladrillo.length; i++) {
            this.dibujoladrillo[i] = new Point2D(31, i * 8);
        }
    }

    public void inc() {
        this.estado++;
        if (this.estado >= LadrilloUI.NUMESTADOS) {
            this.estado = 0;
        }
    }

}
