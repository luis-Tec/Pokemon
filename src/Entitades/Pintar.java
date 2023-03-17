/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this Template file, choose Tools | Templates
 * and open the Template in the editor.
 */
package Entitades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Esteban Rojas
 */
public class Pintar extends JPanel {

    Metodos Met = new Metodos();

    public static int Columnas, Filas;
    public static int Tamano = 40;

    public Celda Actual = Met.Inicio;
    public Celda Destino = null;

    public static boolean CambiarDestino = false;
    public static Celda Destino2 = null;

    public static Persona Persona1 = null;
    public static Persona Persona2 = null;
    public static Persona Persona3 = null;

    public static final JLabel Jugador1 = new JLabel("1");
    public static final JLabel Jugador2 = new JLabel("2");
    public static final JLabel Jugador3 = new JLabel("3");

    public static final JLabel Aceleracion = new JLabel("A");
    public static final JLabel Teletransporte = new JLabel("T");
    public static final JLabel EsperarSegunds = new JLabel("E");
    public static final JLabel Relentizar = new JLabel("R");
    public static final JLabel CambiarObjetivo = new JLabel("C");
    public static final JLabel Aleatorio = new JLabel("?");

    public static JLabel Meta = new JLabel("X");

    boolean Bandera = false;
    boolean Bandera2 = false;
    boolean Bandera3 = false;

    public Pintar() {

        Jugador1.setVisible(false);
        Jugador2.setVisible(false);
        Jugador3.setVisible(false);

        add(this.Jugador1);
        add(this.Jugador2);
        add(this.Jugador3);

        Aceleracion.setVisible(false);
        Aleatorio.setVisible(false);
        CambiarObjetivo.setVisible(false);
        EsperarSegunds.setVisible(false);
        Relentizar.setVisible(false);
        Teletransporte.setVisible(false);

        add(this.Aceleracion);
        add(this.Aleatorio);
        add(this.CambiarObjetivo);
        add(this.EsperarSegunds);
        add(this.Relentizar);
        add(this.Teletransporte);

        add(this.Meta);
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (Bandera == false) {
            Met.LeerBonus();

            g.setColor(Color.red);

            Graphics2D g2D = (Graphics2D) g;

            Columnas = (int) Math.floor((400 / Tamano));

            Filas = (int) Math.floor((400 / Tamano));
//Crea nuevos vertices
            for (int fila = 0; fila < Filas; fila++) {
                for (int columna = 0; columna < Columnas; columna++) {
                    Celda Temp = Met.CrearCelda(true, true, true, true, columna, fila, columna * Tamano, fila * Tamano, false, false);
                }
            }
//Limpia las marcas
            Met.limpiarMarcasCamino();

//Agrega los jugadores a la lista
            Met.JugadoresRandom();

//Agrega los Bonus a la lista
            Met.BonusRandom();

//Borra paredes            
            Actual = Met.Inicio;
            Celda Aux = Actual;
            while (Aux != null) {
                Met.BorrarParedesAleatorio(g, Aux);
                Aux = Aux.Sig;
            }

//Crea los caminos
            Met.Recursividad(g, Met.Inicio);

//Repinta las paredes
            Met.RePintar(Met.Inicio);

//Repinta las paredes
            Met.limpiarMarcasCamino();

            Bandera = true;
        }

//Genera un Destino Random
        if (Bandera2 == false) {
            Persona Aux = Met.InicioP;
            Destino = Met.DestinoRandom();
            while (Destino == null) {
                Destino = Met.DestinoRandom();
            }
            //Calcula la ruta mas corta y la agrega al personaje
            Meta.setLocation(Destino.PosX, Destino.PosY);
            while (Aux != null) {
                Met.CaminoCorto(Destino, Met.EncontrarCelda(Aux.Fila, Aux.Columna), "", 0);
                Met.AgregarCamino(Aux);
                Met.RutaMenor = "";
                Met.DistanciaMenor = 10000;
                Aux = Aux.Sig;
            }
//Inicio de los hilos
            Persona Auxp = Met.InicioP;
            while (Auxp != null) {
                Auxp.start();
                Auxp = Auxp.Sig;
            }
            Bandera2 = true;
        }
        g.setColor(Color.BLACK);
        Met.PintarCeldas(g, Met.Inicio);
//Hace visibles los jlabel de cada bonus
        Celda Temp = Met.Inicio;
        while (Temp != null) {
            if (Temp.Destino == true) {
                Meta.setLocation(Destino.PosX + 15, Destino.PosY + 10);
            }
            if (Temp.SigB != null) {
                if (Temp.SigB.Peso == 1) {
                    Aceleracion.setLocation(Temp.PosX + 15, Temp.PosY + 10);
                    Aceleracion.setVisible(true);
                }
                if (Temp.SigB.Peso == 2) {
                    Relentizar.setLocation(Temp.PosX + 15, Temp.PosY + 10);
                    Relentizar.setVisible(true);
                }
                if (Temp.SigB.Peso == 3) {
                    Aleatorio.setLocation(Temp.PosX + 15, Temp.PosY + 10);
                    Aleatorio.setVisible(true);
                }
                if (Temp.SigB.Peso == 4) {
                    Teletransporte.setLocation(Temp.PosX + 15, Temp.PosY + 10);
                    Teletransporte.setVisible(true);
                }
                if (Temp.SigB.Peso == 5) {
                    CambiarObjetivo.setLocation(Temp.PosX + 15, Temp.PosY + 10);
                    CambiarObjetivo.setVisible(true);
                }
                if (Temp.SigB.Peso == 6) {
                    EsperarSegunds.setLocation(Temp.PosX + 15, Temp.PosY + 10);
                    EsperarSegunds.setVisible(true);
                }
            }
            Temp = Temp.Sig;
        }
        if (CambiarDestino == false) {
            Destino2 = Met.DestinoRandom();
            while (Destino2 == null) {
                Destino2 = Met.DestinoRandom();
            }
            CambiarDestino = true;
        }
    }
}
