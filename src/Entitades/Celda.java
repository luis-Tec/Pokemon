/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitades;

/**
 *
 * @author Esteban Rojas
 */
public class Celda {

    boolean Arriba, Abajo, Izquierda, Derecha;
    int Columna, Fila;
    int PosX, PosY;
    boolean Visitado;
    Celda Sig;
    boolean Jugador;
    boolean Destino;
    MovCelda SigM;
    Arco SigA;
    Bonus SigB;

    public Celda(boolean Arriba, boolean Abajo, boolean Izquierda, boolean Derecha, int Columna, int Fila, int PosX, int PosY, boolean Visitado, boolean Jugador) {
        this.Arriba = Arriba;
        this.Abajo = Abajo;
        this.Izquierda = Izquierda;
        this.Derecha = Derecha;
        this.Columna = Columna;
        this.Fila = Fila;
        this.PosX = PosX;
        this.PosY = PosY;
        this.Visitado = Visitado;
        this.Sig = null;
        this.SigM = null;
        this.Jugador = Jugador;
        this.SigA = null;
        this.Destino = false;
        this.SigB = null;
    }
}
