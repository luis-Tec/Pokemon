/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitades;

import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author Esteban Rojas
 */
public class Hoja extends JFrame {

    public Hoja() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(408, 430);
        add(new Pintar());
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setVisible(true);
    }

}
