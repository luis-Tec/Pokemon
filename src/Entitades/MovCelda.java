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
public class MovCelda {
    String Mov;
    MovCelda Sig;
    Celda Movimiento;
    
    public MovCelda(String Mov){
        this.Mov = Mov;
        this.Sig = null;
        this.Movimiento = null;
    }
}
