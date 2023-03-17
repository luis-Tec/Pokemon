/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitades;

import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Bonus {

    public String Nombre;
    public int Peso;
    public boolean Activo;
    public Bonus Der, Izq;

    public Bonus(String Nombre, int Peso) {
        this.Nombre = Nombre;
        this.Peso = Peso;
        this.Activo = true;
        this.Der = null;
        this.Izq = null;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getPeso() {
        return Peso;
    }

    public void setPeso(int Peso) {
        this.Peso = Peso;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

    public Bonus getDer() {
        return Der;
    }

    public void setDer(Bonus Der) {
        this.Der = Der;
    }

    public Bonus getIzq() {
        return Izq;
    }

    public void setIzq(Bonus Izq) {
        this.Izq = Izq;
    }

}
