/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitades;

import static Entitades.Pintar.Destino2;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Persona extends Thread {

    public Metodos metodos = new Metodos();
    public String Nombre;
    public int Fila;
    public int Columna;
    public int PosX;
    public int PosY;
    public int Velocidad;
    public Persona Sig;
    public Camino SigC;
    public JLabel JLabel;
    public boolean Terminado;
    public boolean Activo;

    public Persona(String Nombre, int Fila, int Columna, JLabel JLabel) {
        this.Nombre = Nombre;
        this.Fila = Fila;
        this.Columna = Columna;
        this.JLabel = JLabel;
        this.Sig = null;
        this.Velocidad = 48;
        this.Terminado = false;
        this.Activo = true;
    }

//Ejecucion de los hilos
    @Override
    public void run() {
        //El JLabel del personaje se hace visible
        JLabel.setVisible(true);
        //Auxiliar de la sublista del personaje
        Camino Aux = this.SigC;

        while (Aux != null && this.Activo) {
            this.PosX = Aux.Celda.PosX;
            this.PosY = Aux.Celda.PosY;
            this.Fila = Aux.Celda.Fila;
            this.Columna = Aux.Celda.Columna;
            //Pregunta si llego a la ultima posicion de la sublista
            if (Aux.Sig == null) {
                JOptionPane.showMessageDialog(null, "El Jugador (" + this.Nombre + ")" + " Llegó Al Destino");
                this.Terminado = true;
                this.JLabel.setVisible(false);
                this.stop();
                break;
            } else {
                //Si el Destino Cambia , mueva el jlabel para el nuevo destino
                if (Pintar.CambiarDestino) {
                    Pintar.Meta.setLocation(Pintar.Destino2.PosX + 15, Pintar.Destino2.PosY + 10);
                }
                //Entra en la sublista
                if (Aux.Celda.SigB != null) {
                    //Si la celda tiene un bonus uselo y marquelo como usado
                    if (Aux.Celda.SigB.Nombre.equals("Aceleracion") && Aux.Celda.SigB.Activo) {
                        this.Velocidad -= 20;
                        Aux.Celda.SigB.setActivo(false);
                    }
                    if (Aux.Celda.SigB.Nombre.equals("Esperarsegundos") && Aux.Celda.SigB.Activo) {
                        int Segundos = 5 + (int) (Math.random() * 111);
                        int x = 0;
                        while (x <= Segundos) {
                            x += 1;
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        Aux.Celda.SigB.setActivo(false);
                    }
                    if (Aux.Celda.SigB.Nombre.equals("Relentizar") && Aux.Celda.SigB.Activo) {
                        if (Pintar.Persona1 != null) {
                            if (Pintar.Persona1.Nombre.equals(this.Nombre)) {
                                Pintar.Persona2.setVelocidad(63);
                                Pintar.Persona3.setVelocidad(63);
                            }
                        }
                        if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 == null) {
                            if (Pintar.Persona2.Nombre.equals(this.Nombre)) {
                                Pintar.Persona3.setVelocidad(63);
                            }
                        }
                        if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 != null) {
                            if (Pintar.Persona2.Nombre.equals(this.Nombre)) {
                                Pintar.Persona1.setVelocidad(63);
                                Pintar.Persona3.setVelocidad(63);
                            }
                        }
                        if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 == null) {
                            if (Pintar.Persona3.Nombre.equals(this.Nombre)) {
                                Pintar.Persona2.setVelocidad(63);
                            }
                        }
                        if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 != null) {
                            if (Pintar.Persona3.Nombre.equals(this.Nombre)) {
                                Pintar.Persona1.setVelocidad(63);
                                Pintar.Persona2.setVelocidad(63);
                            }
                        }
                        Aux.Celda.SigB.setActivo(false);
                    }
                    if (Aux.Celda.SigB.Nombre.equals("Teletransporte") && Aux.Celda.SigB.Activo) {
                        this.SigC = null;
                        Celda Temp = metodos.Inicio;
                        Celda Destino = null;
                        while (Temp != null) {
                            if (Temp.Destino) {
                                Destino = Temp;
                            }
                            Temp = Temp.Sig;
                        }
                        Celda AuxT = metodos.TInicio();
                        while (AuxT == null) {
                            AuxT = metodos.TInicio();
                        }
                        metodos.CaminoCorto(Destino, AuxT, "", 0);
                        metodos.AgregarCamino(this);
                        metodos.RutaMenor = "";
                        metodos.DistanciaMenor = 10000;
                        Aux.Celda.SigB.Activo = false;
                        this.run();
                    }
                    if (Aux.Celda.SigB.Nombre.equals("Cambiarobjetivo") && Aux.Celda.SigB.Activo) {
                        if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 == null) {
                            Aux.Celda.SigB.Activo = false;
                            Pintar.Persona2.CambiarDestino();
                            Pintar.Persona3.CambiarDestino();
                        } else {
                            Aux.Celda.SigB.Activo = false;
                            Pintar.Persona1.CambiarDestino();
                            Pintar.Persona2.CambiarDestino();
                            Pintar.Persona3.CambiarDestino();
                        }

                    }
                    if (Aux.Celda.SigB.Nombre.equals("Aleatorio") && Aux.Celda.SigB.Activo) {
                        int Bonus = 1 + (int) (Math.random() * 5);

                        if (Bonus == 1) {
                            System.out.println("Bonus Aleatorio(ACELERACION)--->" + this.Nombre);
                            this.Velocidad -= 20;
                            Aux.Celda.SigB.setActivo(false);
                        }
                        if (Bonus == 2) {
                            System.out.println("Bonus Aleatorio(RELENTIZAR PERSONAJES)--->" + this.Nombre);
                            if (Pintar.Persona1 != null) {
                                if (Pintar.Persona1.Nombre.equals(this.Nombre)) {
                                    Pintar.Persona2.setVelocidad(63);
                                    Pintar.Persona3.setVelocidad(63);
                                    Aux.Celda.SigB.setActivo(false);
                                }
                            }
                            if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 == null) {
                                if (Pintar.Persona2.Nombre.equals(this.Nombre)) {
                                    Pintar.Persona3.setVelocidad(63);
                                    Aux.Celda.SigB.setActivo(false);
                                }
                            }
                            if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 != null) {
                                if (Pintar.Persona2.Nombre.equals(this.Nombre)) {
                                    Pintar.Persona1.setVelocidad(63);
                                    Pintar.Persona3.setVelocidad(63);
                                    Aux.Celda.SigB.setActivo(false);
                                }
                            }
                            if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 == null) {
                                if (Pintar.Persona3.Nombre.equals(this.Nombre)) {
                                    Pintar.Persona2.setVelocidad(63);
                                    Aux.Celda.SigB.setActivo(false);
                                }
                            }
                            if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 != null) {
                                if (Pintar.Persona3.Nombre.equals(this.Nombre)) {
                                    Pintar.Persona1.setVelocidad(63);
                                    Pintar.Persona2.setVelocidad(63);
                                    Aux.Celda.SigB.setActivo(false);
                                }
                            }
                        }
                        if (Bonus == 3) {
                            System.out.println("Bonus Aleatorio(TELETRANSPORTE)--->" + this.Nombre);
                            this.SigC = null;
                            Celda Temp = metodos.Inicio;
                            Celda Destino = null;
                            while (Temp != null) {
                                if (Temp.Destino) {
                                    Destino = Temp;
                                }
                                Temp = Temp.Sig;
                            }
                            Celda AuxT = metodos.TInicio();
                            while (AuxT == null) {
                                AuxT = metodos.TInicio();
                            }
                            metodos.CaminoCorto(Destino, AuxT, "", 0);
                            metodos.AgregarCamino(this);
                            metodos.RutaMenor = "";
                            metodos.DistanciaMenor = 10000;
                            Aux.Celda.SigB.setActivo(false);
                            this.run();
                        }
                        if (Bonus == 4) {
                            System.out.println("Bonus Aleatorio(ESPERAR SEGUNDOS)--->" + this.Nombre);
                            int Segundos = 5 + (int) (Math.random() * 111);
                            int x = 0;
                            while (x <= Segundos) {
                                x += 1;
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            Aux.Celda.SigB.setActivo(false);
                        }
                        if (Bonus == 5) {
                            System.out.println("Bonus Aleatorio(CAMBIAR OBJETIVO)--->" + this.Nombre);
                            Destino2 = metodos.DestinoRandom();
                            while (Destino2 == null) {
                                Destino2 = metodos.DestinoRandom();
                            }

                            if (Pintar.Persona2 != null && Pintar.Persona3 != null && Pintar.Persona1 == null) {
                                Aux.Celda.SigB.Activo = false;
                                Pintar.Persona2.CambiarDestino();
                                Pintar.Persona3.CambiarDestino();
                            } else {
                                Aux.Celda.SigB.Activo = false;
                                Pintar.Persona1.CambiarDestino();
                                Pintar.Persona2.CambiarDestino();
                                Pintar.Persona3.CambiarDestino();
                            }
                        }
                    }
                }
                //Pregunta si la x se mantiene o aumenta o disminuye o la y se matiene o aumenta o disminuye
                if (Aux.Sig.Celda.PosX == this.PosX && Aux.Sig.Celda.PosY > this.PosY) {
                    for (int i = this.PosY; i < Aux.Sig.Celda.PosY; i++) {
                        JLabel.setLocation(this.PosX + 15, i + 10);
                        try {
                            Thread.sleep(Velocidad);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (Aux.Sig.Celda.PosX == this.PosX && Aux.Sig.Celda.PosY < this.PosY) {
                    for (int i = this.PosY; i > Aux.Sig.Celda.PosY; i--) {
                        JLabel.setLocation(this.PosX + 15, i + 10);
                        try {
                            Thread.sleep(Velocidad);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (Aux.Sig.Celda.PosX > this.PosX && Aux.Sig.Celda.PosY == this.PosY) {
                    for (int i = this.PosX; i < Aux.Sig.Celda.PosX; i++) {
                        JLabel.setLocation(i + 15, this.PosY + 10);
                        try {
                            Thread.sleep(Velocidad);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (Aux.Sig.Celda.PosX < this.PosX && Aux.Sig.Celda.PosY == this.PosY) {
                    for (int i = this.PosX; i > Aux.Sig.Celda.PosX; i--) {
                        JLabel.setLocation(i + 15, this.PosY + 10);
                        try {
                            Thread.sleep(Velocidad);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                Aux = Aux.Sig;
            }
        }
        this.Activo = true;
        this.run();
    }

    public int getVelocidad() {
        return Velocidad;
    }

    public void setVelocidad(int Velocidad) {
        this.Velocidad = Velocidad;
    }

    public void Detener() {
        this.Activo = false;
    }
//Cambia el destino del personaje

    public void CambiarDestino() {
        this.Detener();
        metodos.RutaMenor = "";
        metodos.DistanciaMenor = 10000;
        metodos.QuitarDestino();
        metodos.CaminoCorto(Pintar.Destino2, metodos.EncontrarCelda(this.Fila, this.Columna), "", 0);
        this.SigC = null;
        metodos.AgregarCamino(this);
        Pintar.CambiarDestino = true;
    }
}
