/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAW.arkanoid.modelo;

import DAW.arkanoid.interfaz.CanvasLayer;
import DAW.arkanoid.interfaz.JuegoUI;
import javafx.geometry.Point2D;

/**
 *
 * @author Pedro
 */
enum EstadoJuego {
    PENDIETEINICIAR,
    INICIADO
}

public class Juego {

    public int vidas = 3;
    public Nivel niveles[];
    public Nivel nivelactual;
    public int nivel = 0; // nivel actual
    
    private Campo campo;
    public JuegoUI juegoUI;

    public static int ANCHO = 448;
    public static int ALTO = 480;
    private EstadoJuego estado;
    
    

    public Juego() {
        this.campo = new Campo(Juego.ANCHO, Juego.ALTO);
        this.campo.setVelocidad(1.5f);

        this.campo.getPelota().setAngulo(280f); // angulo inicial de la pelota
        this.estado = EstadoJuego.PENDIETEINICIAR;

        this.init();
        System.out.println("Nivel actual: " + nivel);
    }

    public void init() {
        this.nivel = 0;
        niveles = new Nivel[4];

        Point2D inicio = new Point2D(110, 50);

        int[][] nivel1 = {
            {1,0,1,0,1,0,1},
        };
        
        int[][] nivel2 = {
            {2,0,1,0,1,0,2},
            {0,2,0,1,0,2,0}
        };

        int[][] nivel3 = {
            {2,0,1,0,1,0,2},
            {0,2,0,1,0,2,0},
            {1,0,1,0,1,0,1}
            
        };
        
      
        niveles[0] = new Nivel(inicio, nivel1);
        niveles[0].setFondo(new Point2D(0, 257));

        niveles[1] = new Nivel(inicio, nivel2);
        niveles[1].setFondo(new Point2D(929, 0));

        niveles[2] = new Nivel(inicio, nivel3);
        niveles[2].setFondo(new Point2D(233, 257));

        this.nivelactual = niveles[nivel];
    }

    /**
     * ciclo del juego, se mueve la pelota, comprueba colision con barra y ladrillos si es con ladrillos se devuelve true para repintar el fondo.
     *
     * @param pulsados
     * @return
     */
    public EstadoCambiosJuego ciclo(boolean pulsados[]) {
        EstadoCambiosJuego vuelta = EstadoCambiosJuego.NADA;

        if (pulsados[0]) {
            this.moverBarraIzquierda();
        }
        if (pulsados[1]) {
            this.moverBarraDerecha();
        }
        //si devuelve true es que toca fondo
        if (this.moverPelota()) {
            vuelta = EstadoCambiosJuego.TOCAFONDO;
        }

        if (this.estado == EstadoJuego.PENDIETEINICIAR) {
            this.colocarPelotaEnBarra();
        }

        if (detectarColisionLadrillo()) {
            vuelta = EstadoCambiosJuego.TOCALADRILLO;
        }

        if (this.nivelactual.haTerminado()) { // comprueba si ha terminado el nivel mirando los ladrillos y su dureza
            this.nivelactual = niveles[nivel++];
            System.out.println("Nivel actual: " + nivel);
            vuelta = EstadoCambiosJuego.CAMBIANIVEL;

            if (nivel == 4) { // si el nivel llega al último nivel, sale el mensaje y quita las barras y pelota
                this.campo.setBarra(null);
                this.campo.getPelota().setPosicion(new Point2D(4000, 4000));
            }
        }

        return vuelta;
    }

    public boolean detectarColisionLadrillo() {
        boolean tocar = false;

        int pAngulo = (int) this.campo.getPelota().getAngulo();
        int py = (int) this.campo.getPelota().getPosicion().getY();
        int px = (int) this.campo.getPelota().getPosicion().getX();
        int pr = (int) this.campo.getPelota().getRadio();
        int tipoangulo = 5;

        for (int i = 0; i < this.nivelactual.getLadrillos().length; i++) {
            for (int j = 0; j < this.nivelactual.getLadrillos()[i].length; j++) {
                Ladrillo l;
                l = this.nivelactual.getLadrillos()[i][j];
                int lx = (int) l.getPosicion().getX();
                int ly = (int) l.getPosicion().getY();
                int lancho = (int) l.getAncho();
                int lalto = (int) l.getAlto();

                if (pAngulo > 180) {
                    if (py == ly + lalto && px + pr > lx && px < lx + lancho && l.getDureza() > 0) {
                        tipoangulo = 0;
                        l.setDureza(l.getDureza() - 1);
                        tocar = true;

                    }
                } else if (pAngulo < 180) {
                    if (py + pr == ly && px + pr > lx && px < lx + lancho && l.getDureza() > 0) {
                        tipoangulo = 1;
                        l.setDureza(l.getDureza() - 1);
                        tocar = true;
                    }

                   
                } else if (pAngulo > 270) {
                    if (px + pr == lx && py < ly + lalto && py + pr > ly && l.getDureza() > 0) {
                        tipoangulo = 2;
                        l.setDureza(l.getDureza() - 1);
                        tocar = true;
                    }
          
                } else {
                    if (px == lx + lancho && py < ly + lalto && py + pr > ly && l.getDureza() > 0) {
                        tipoangulo = 3;
                        l.setDureza(l.getDureza() - 1);
                        tocar = true;
                    }
                }
            }
        }

        // Método que arregla cuando la pelota toca dos bloques a la vez (traspasaba los bloques por en medio.)
        switch (tipoangulo) {
            case (0): {
                this.campo.getPelota().setAngulo(360 - this.campo.getPelota().getAngulo());
                break;
            }
            case (1): {
                this.campo.getPelota().setAngulo(360 - this.campo.getPelota().getAngulo());
                break;
            }

            case (2): {
                this.campo.getPelota().setAngulo(180 - this.campo.getPelota().getAngulo());
                break;
            }

            case (3): {
                this.campo.getPelota().setAngulo(180 - this.campo.getPelota().getAngulo());
                break;
            }

        }

        return tocar;
    }

    public boolean moverPelota() {
        boolean tocafondo = false;

        if (this.estado == EstadoJuego.INICIADO) {
            this.campo.getPelota().mover();

            // rebote izquierda del borde
            if (this.campo.getPelota().getPosicion().getX() <= this.campo.borde) {
                this.campo.getPelota().setAngulo(180 - this.campo.getPelota().getAngulo());
            }

            // rebote derecha del borde
            if (this.campo.getPelota().getPosicion().getX() + this.campo.getPelota().getRadio() >= Campo.getWidth() - this.campo.getBorde()) {
                this.campo.getPelota().setAngulo(180 - this.campo.getPelota().getAngulo());
            }

            // rebote arriba del borde
            if (this.campo.getPelota().getPosicion().getY() <= this.campo.getBorde()) {
                this.campo.getPelota().setAngulo(360 - this.campo.getPelota().getAngulo());
            }

            // rebote de la pelota con la barra parte de la izquierda
            if (this.campo.getPelota().getPosicion().getY() + this.campo.getPelota().getRadio() >= this.campo.getBarra().getPosicion().getY()) {
                if (this.campo.getPelota().getPosicion().getX() + this.campo.getPelota().getRadio() > this.campo.getBarra().getPosicion().getX()) {
                    this.campo.getPelota().setAngulo(360 - this.campo.getPelota().getAngulo());
                }
            }

            // rebote de la pelota con la barra parte de la derecha
            if (this.campo.getPelota().getPosicion().getY() + this.campo.getPelota().getRadio() >= this.campo.getBarra().getPosicion().getY()) {
                if (this.campo.getBarra().getPosicion().getX() + this.campo.getBarra().getAncho() 
                        <= this.campo.getPelota().getPosicion().getX() + this.campo.getPelota().getRadio()) {
                    this.campo.getPelota().setAngulo(360 - this.campo.getPelota().getAngulo());
                }
            }

            // Toca el fondo la pelota (una vida menos)
            if (this.campo.getPelota().getPosicion().getY() >= Campo.getHeight()) {
                this.estado = EstadoJuego.PENDIETEINICIAR;
                this.setVidas(this.getVidas() - 1);
                tocafondo = true;
                System.out.println("Vidas: " + this.getVidas());
                System.out.println("Nivel: " + nivel);
            }
        }

        return tocafondo;

    }

    public void iniciarPelota() {
        this.estado = EstadoJuego.INICIADO;
    }

    public void setPendienteIniciar() {
        this.colocarPelotaEnBarra();
        this.estado = EstadoJuego.PENDIETEINICIAR;
    }

    public void colocarPelotaEnBarra() {
        if (this.campo.getBarra() != null) {
            this.campo.getPelota().setPosicion(new Point2D(this.campo.getBarra().getPosicion().getX() + this.campo.getBarra().getAncho() / 2, this.campo.getBarra().getPosicion().getY() - 15));
        }
    }

    public void moverBarraIzquierda() {
        if (this.campo.getBarra() != null) {
            if (this.campo.getBarra().getPosicion().getX() > this.campo.getBorde()) {
                this.campo.getBarra().moverIzquierda();
                if (this.estado == EstadoJuego.PENDIETEINICIAR) {
                    this.colocarPelotaEnBarra();
                }
            }
        }
    }

    public void moverBarraDerecha() {
        if (this.campo.getBarra() != null) {
            if (this.campo.getBarra().getPosicion().getX() + this.campo.getBarra().getAncho() 
                    < Campo.getWidth() - this.campo.getBorde()) {
                this.campo.getBarra().moverDerecha();
                if (this.estado == EstadoJuego.PENDIETEINICIAR) {
                    this.colocarPelotaEnBarra();
                }
            }
        }

    }

    /**
     * @return the campo
     */
    public Campo getCampo() {
        return campo;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public int getVidas() {
        return this.vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}
