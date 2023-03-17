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
public class Arco {
    int Peso;
    Celda Destino;
    Arco Sig;
    
    public Arco(int peso){
        this.Peso = peso;
        this.Destino = null;
        this.Sig = null;
    }
}
