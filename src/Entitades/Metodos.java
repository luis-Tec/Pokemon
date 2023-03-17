/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitades;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static Entitades.Pintar.Tamano;

/**
 *
 * @author Esteban Rojas
 */
public class Metodos {

    public static Celda Inicio;
    public static Persona InicioP;
    public static Bonus Raiz;

// Agrega los bonus a un arbol
    public void CargarBonus(Bonus Aux, String Nombre, int Peso) {
        Bonus Nuevo = new Bonus(Nombre, Peso);
        if (Raiz == null) {
            Raiz = Nuevo;
        } else {
            if (Peso > Aux.Peso) {
                if (Aux.Der == null) {
                    Aux.Der = Nuevo;
                } else {
                    CargarBonus(Aux.Der, Nombre, Peso);
                }
            }
            if (Peso < Aux.Peso) {
                if (Aux.Izq == null) {
                    Aux.Izq = Nuevo;
                } else {
                    CargarBonus(Aux.Izq, Nombre, Peso);
                }
            }
        }
    }

//Busca en el arbol un Bonus por el peso
    public Bonus Buscar(Bonus Temp, int Peso) {
        if (Temp.Peso == Peso) {
            return Temp;
        } else if (Temp.Der.Peso == Peso) {
            return Temp.Der;
        } else if (Temp.Der.Izq.Peso == Peso) {
            return Temp.Der.Izq;
        } else if (Temp.Der.Izq.Der.Peso == Peso) {
            return Temp.Der.Izq.Der;
        } else if (Temp.Der.Der.Peso == Peso) {
            return Temp.Der.Der;
        } else if (Temp.Der.Der.Izq.Peso == Peso) {
            return Temp.Der.Der.Izq;
        }
        return null;
    }

//Se encarga de leer las dos primeras lineas del archivo.txt en el cual se encuentran los bonus.
    public void LeerBonus() {
        File BonusFile = new File("Bonus.txt");
        try {
            BufferedReader lector = new BufferedReader(new FileReader(BonusFile));
            int temp = 1;
            String bfRead;
            String Nombre = "";
            int Peso = 0;
            while ((bfRead = lector.readLine()) != null) {
                if (temp == 1) {
                    Nombre = bfRead;
                }
                if (temp == 2) {
                    Peso = Integer.parseInt(bfRead);
                    CargarBonus(Raiz, Nombre, Peso);
                    temp = 0;
                }
                temp++;
            }
        } catch (IOException ex) {
        }
    }

//Agrega  nuevos vertices a la lista
    public Celda CrearCelda(boolean Arriba, boolean Izquierda, boolean Abajo, boolean Derecha, int Fila, int Columna,
            int PosX, int PosY, boolean Visitado, boolean Jugador) {
        Celda Nueva = new Celda(Arriba, Izquierda, Abajo, Derecha, Fila, Columna, PosX, PosY, Visitado, Jugador);
        if (Inicio == null) {
            Inicio = Nueva;
            return Nueva;
        } else {
            Celda Temp = Inicio;
            Celda Ant = Temp;
            while (Temp != null) {
                Ant = Temp;
                Temp = Temp.Sig;
            }
            Ant.Sig = Nueva;
            return Nueva;
        }
    }

//Imprime todos los vertices de la lista
    public void Imprimir() {
        Celda Celda = Inicio;
        while (Celda != null) {
            System.out.println(Celda.Columna + " " + " " + Celda.Fila);
            Celda = Celda.Sig;
        }
    }

//Borra paredes aleatorias de la matriz, donde encuentra una X celda y define aleatoriamente que pared sera borrada
    public void BorrarParedesAleatorio(Graphics g, Celda Temp) {
        Graphics g2D = (Graphics2D) g;
        int Numero = 1 + (int) (Math.random() * 23);
        Celda Arriba = EncontrarCelda(Temp.Fila - 1, Temp.Columna);
        Celda Izquierda = EncontrarCelda(Temp.Fila, Temp.Columna - 1);
        Celda Abajo = EncontrarCelda(Temp.Fila + 1, Temp.Columna);
        Celda Derecha = EncontrarCelda(Temp.Fila, Temp.Columna + 1);
        if (Arriba != null && Numero == 1) {
            if (!VerificarEnlaC(Temp, Arriba)) {
                EnlazarCamino(Temp, Arriba);
                EnlazarCamino(Arriba, Temp);
            }
        }
        if (Izquierda != null && Numero == 2) {
            if (!VerificarEnlaC(Temp, Izquierda)) {
                EnlazarCamino(Temp, Izquierda);
                EnlazarCamino(Izquierda, Temp);
            }
        }
        if (Abajo != null && Numero == 3) {
            if (!VerificarEnlaC(Temp, Abajo)) {
                EnlazarCamino(Temp, Abajo);
                EnlazarCamino(Abajo, Temp);
            }
        }
        if (Derecha != null && Numero == 4) {
            if (!VerificarEnlaC(Temp, Derecha)) {
                EnlazarCamino(Temp, Derecha);
                EnlazarCamino(Derecha, Temp);
            }
        }
    }
//Verifica que el enlace no se repita
    public boolean VerificarEnlaC(Celda Actual, Celda Siguiente) {
        Arco Temp = Actual.SigA;

        while (Temp != null) {

            if (Temp.Destino == Siguiente) {
                return true;
            }
            Temp = Temp.Sig;
        }
        return false;
    }

//Recorre toda la lista y pinta las paredes de cada vertice
    public void PintarCeldas(Graphics a, Celda Temp) {
        Graphics2D g2D = (Graphics2D) a;
        while (Temp != null) {
            if (Temp.Arriba == true) {
                g2D.drawLine(Temp.PosX, Temp.PosY, Temp.PosX + Tamano, Temp.PosY);
            }
            if (Temp.Izquierda == true) {
                g2D.drawLine(Temp.PosX, Temp.PosY + Tamano, Temp.PosX, Temp.PosY);
            }
            if (Temp.Abajo == true) {
                g2D.drawLine(Temp.PosX + Tamano, Temp.PosY + Tamano, Temp.PosX, Temp.PosY + Tamano);
            }
            if (Temp.Derecha == true) {
                g2D.drawLine(Temp.PosX + Tamano, Temp.PosY, Temp.PosX + Tamano, Temp.PosY + Tamano);
            }
            Temp = Temp.Sig;
        }
    }

    //Recive un vertice de origen y otro vertice destino, el cual mediante la posicion de origen
    //empieza a recorrer todos los vertices enlazados mediante los arcos calcuando mediante los pesos
    //la ruta con menor peso, para luego retonarla
    int DistanciaMenor = 10000;
    String RutaMenor = "";

    public void CaminoCorto(Celda Origen, Celda Destino, String Ruta, int Distancia) {
        if (Origen == null || Origen.Visitado == true) {
            return;
        }
        if (Origen == Destino) {
            if (Distancia < DistanciaMenor) {
                DistanciaMenor = Distancia;
                RutaMenor = Ruta + Destino.Fila + Destino.Columna;
            }
            return;
        }
        Origen.Visitado = true;
        Arco TempA = Origen.SigA;
        while (TempA != null) {
            CaminoCorto(TempA.Destino, Destino, Ruta + "" + Origen.Fila + "" + Origen.Columna, Distancia + TempA.Peso);
            TempA = TempA.Sig;
        }
        Origen.Visitado = false;
    }

//Recive un tipo Persona en el cual se agregara la ruta mas corta a la sublista, descomponiendo
//una cadena de String y convirtiendolas a tipo int;
    public void AgregarCamino(Persona jugador) {
        for (int i = 0; i < RutaMenor.length(); i++) {
            String fila = "" + RutaMenor.charAt(i);
            i++;
            String columna = "" + RutaMenor.charAt(i);
            Camino nuevo = new Camino();
            nuevo.Celda = EncontrarCelda((Integer.parseInt(fila)), Integer.parseInt(columna));

            if (jugador.SigC == null) {
                jugador.SigC = nuevo;
            } else {
                nuevo.Sig = jugador.SigC;
                jugador.SigC = nuevo;
            }
        }
        Camino aux = jugador.SigC;
        while (aux
                != null) {
            aux = aux.Sig;
        }
    }
    
//Recorre de forma recursiva la lista desde el Inicio, este va marcando
//los vertices visitados y elige un movimiento random mediante el metod
//ElegirX en donde borra una pared y luego enlaza los vecinos con los arcos;
    public void Recursividad(Graphics g, Celda Actual) {
        if (!BuscarVecinos(Actual, g)) {
            Actual.Visitado = true;
            return;
        } else {
            Actual.Visitado = true;
            Celda Siguiente = ElegirX(Actual);
            while (Siguiente != null) {
                if (!Siguiente.Visitado && !VerificarEnlaC(Actual, Siguiente)) {
                    EnlazarCamino(Actual, Siguiente);
                    EnlazarCamino(Siguiente, Actual);
                }
                Recursividad(g, Siguiente);
                Siguiente = ElegirX(Actual);
            }
            return;
        }
    }

//Coloca el un jlabel en la nueva posicion del destino
    public void Meta() {
        Celda Aux = Inicio;
        while (Aux != null) {
            if (Aux.Destino) {
                Pintar.Meta.setLocation(Aux.PosX + 15, Aux.PosY + 10);
            }
            Aux = Aux.Sig;
        }
    }

//Asigna arcos a todos los vertices con todos los posibles vecino;
    public void EnlazarCamino(Celda Origen, Celda Destino) {
        Arco Nuevo = new Arco(8);
        if (Origen.SigB != null) {
            Nuevo.Peso = Origen.SigB.Peso;
        }
        if (Origen.SigA == null) {
            Nuevo.Destino = Destino;
            Origen.SigA = Nuevo;
        } else {

            Nuevo.Destino = Destino;
            Nuevo.Sig = Origen.SigA;
            Origen.SigA = Nuevo;
        }

    }
//Limpia las marcas de los vertices

    public void limpiarMarcasCamino() {
        Celda temp = Inicio;
        while (temp != null) {
            temp.Visitado = false;
            temp = temp.Sig;
        }
    }
//imprime el camino de la lista

    public void ImprimirCamino(Celda temp) {
        while (temp != null) {
            Arco aux = temp.SigA;
            System.out.println("Celda en: " + temp.Fila + " " + temp.Columna);
            while (aux != null) {
                System.out.println(aux.Destino.Fila + " " + aux.Destino.Columna);
                aux = aux.Sig;
            }
            System.out.println("");
            temp = temp.Sig;
        }
    }

//Luego de generar el mapa se llama a este metodo para repintar las paredes.
    public void RePintar(Celda Temp) {
        if (Temp == null) {
            return;
        }
        Celda Arriba = EncontrarCelda(Temp.Fila - 1, Temp.Columna);
        Celda Izquierda = EncontrarCelda(Temp.Fila, Temp.Columna - 1);
        Celda Abajo = EncontrarCelda(Temp.Fila + 1, Temp.Columna);
        Celda Derecha = EncontrarCelda(Temp.Fila, Temp.Columna + 1);
        Arco arco = Temp.SigA;
        while (arco != null) {
            if (arco.Destino == Arriba) {
                Temp.Arriba = false;
            }
            if (arco.Destino == Izquierda) {
                Temp.Izquierda = false;
            }
            if (arco.Destino == Abajo) {
                Temp.Abajo = false;
            }
            if (arco.Destino == Derecha) {
                Temp.Derecha = false;
            }
            arco = arco.Sig;
        }
        RePintar(Temp.Sig);
    }

//Busca los vecinos de un vertice X
    public boolean BuscarVecinos(Celda Temp, Graphics g) {
        Graphics g2D = (Graphics2D) g;
        boolean vecinos = false;
        Celda Arriba = EncontrarCelda(Temp.Fila - 1, Temp.Columna);
        Celda Izquierda = EncontrarCelda(Temp.Fila, Temp.Columna - 1);
        Celda Abajo = EncontrarCelda(Temp.Fila + 1, Temp.Columna);
        Celda Derecha = EncontrarCelda(Temp.Fila, Temp.Columna + 1);
        if (Arriba != null && !Arriba.Visitado) {
            MovCelda Movimiento = new MovCelda("Arriba");
            Movimiento.Movimiento = Arriba;
            if (Temp.SigM == null) {
                Temp.SigM = Movimiento;
            } else {
                Movimiento.Sig = Temp.SigM;
                Temp.SigM = Movimiento;
            }
            vecinos = true;
        }
        if (Izquierda != null && !Izquierda.Visitado) {
            MovCelda Movimiento = new MovCelda("Izquierda");
            Movimiento.Movimiento = Izquierda;
            if (Temp.SigM == null) {
                Temp.SigM = Movimiento;
            } else {
                Movimiento.Sig = Temp.SigM;
                Temp.SigM = Movimiento;
            }
            vecinos = true;
        }
        if (Abajo != null && !Abajo.Visitado) {
            MovCelda Movimiento = new MovCelda("Abajo");
            Movimiento.Movimiento = Abajo;
            if (Temp.SigM == null) {
                Temp.SigM = Movimiento;
            } else {
                Movimiento.Sig = Temp.SigM;
                Temp.SigM = Movimiento;
            }
            vecinos = true;
        }
        if (Derecha != null && !Derecha.Visitado) {
            MovCelda Movimiento = new MovCelda("Derecha");
            Movimiento.Movimiento = Derecha;
            if (Temp.SigM == null) {
                Temp.SigM = Movimiento;
            } else {
                Movimiento.Sig = Temp.SigM;
                Temp.SigM = Movimiento;
            }
            vecinos = true;
        }
        return vecinos;
    }

//Elige un movimiento random, cada movimiento esta relacionado con las paredes,
//los movimientos borras las paredes de un vertice.
    public Celda ElegirX(Celda Temp) {
        MovCelda Aux = Temp.SigM;
        int Contador = 0;
        while (Aux != null) {
            Contador++;
            Aux = Aux.Sig;
        }
        int Numero = 1 + (int) (Math.random() * Contador);
        Contador = 1;
        Aux = Temp.SigM;
        while (Aux != null) {
            if (Contador == Numero) {
                borrarMovimiento(Temp, Aux.Movimiento);
                if (Aux.Mov.equals("Arriba")) {
                }
                if (Aux.Mov.equals("Izquierda")) {
                }
                if (Aux.Mov.equals("Abajo")) {
                }
                if (Aux.Mov.equals("Derecha")) {
                }
                return Aux.Movimiento;
            } else {
                Contador++;
                Aux = Aux.Sig;
            }
        }
        return null;
    }
//Borra los moviemientos de la sublista de la clase MovCelda

    public void borrarMovimiento(Celda Temp, Celda Movimiento) {
        MovCelda Aux = Temp.SigM;
        if (Temp.SigM.Movimiento == Movimiento) {
            Temp.SigM = Temp.SigM.Sig;
            return;
        }
        MovCelda Ant = Aux;
        while (Aux != null) {
            if (Aux.Movimiento == Movimiento) {
                Ant.Sig = Aux.Sig;
                return;
            } else {
                Aux = Aux.Sig;
            }
        }
    }

//Busca un vertice de la lista mediante su posicion(Fila,Columna)
    public Celda EncontrarCelda(int Fila, int Columna) {
        Celda Temp = Inicio;
        while (Temp != null) {
            if (Temp.Columna == Columna && Temp.Fila == Fila) {
                return Temp;
            }
            Temp = Temp.Sig;
        }
        return null;
    }

//El se quita la marca de un vertice como destino
    public void QuitarDestino() {
        Celda Aux = Inicio;
        while (Aux != null) {
            if (Aux.Destino) {
                Aux.Destino = false;
            }
            Aux = Aux.Sig;
        }
    }

//Busca un vertice para luego ser utilizado como destino.
    public Celda DestinoRandom() {
        Celda Destino = null;
        int Fila = (int) (Math.random() * 9);
        int Columna = (int) (Math.random() * 9);
        Destino = EncontrarCelda(Fila, Columna);
        if (Destino.Jugador == false && Destino.SigB == null) {
            Destino.Destino = true;
            return Destino;
        }
        return null;
    }

//Retorna un vertice aleatorio , para luego ser utilizado
//en un personaje a la hora de usar el bonus de teletransporte
    public Celda TInicio() {
        int Fila = (int) (Math.random() * 9);
        int Columna = (int) (Math.random() * 9);
        Celda ITemp = EncontrarCelda(Fila, Columna);
        if (ITemp.Jugador == false && ITemp.SigB == null && !ITemp.Destino) {
            return ITemp;
        }
        return null;
    }

//Agrega una X cantidad de Bonus en la lista
    public void BonusRandom() {
        int Cantidad = 1 + (int) (Math.random() * 1);
        while (Cantidad <= 6) {
            int TipoBonus = 1 + (int) (Math.random() * 6);
            int Fila = (int) (Math.random() * 9);
            int Columna = (int) (Math.random() * 9);
            Bonus Aux = Raiz;
            if (TipoBonus == 1) {
                Bonus Aceleracion = Buscar(Aux, 1);
                Celda Celda = EncontrarCelda(Fila, Columna);
                if (Celda.SigB == null && Celda.Jugador == false && Aceleracion.Activo) {
                    Celda.SigB = Aceleracion;
                    Aceleracion.setActivo(false);
                }
            }
            if (TipoBonus == 2) {
                Bonus Teletransporte = Buscar(Aux, 4);
                Celda Celda = EncontrarCelda(Fila, Columna);
                if (Celda.SigB == null && Celda.Jugador == false && Teletransporte.Activo) {
                    Celda.SigB = Teletransporte;
                    Teletransporte.setActivo(false);
                }
            }
            if (TipoBonus == 3) {
                Bonus Esperarsegundos = Buscar(Aux, 6);
                Celda Celda = EncontrarCelda(Fila, Columna);
                if (Celda.SigB == null && Celda.Jugador == false && Esperarsegundos.isActivo()) {
                    Celda.SigB = Esperarsegundos;
                    Esperarsegundos.setActivo(false);
                }
            }
            if (TipoBonus == 4) {
                Bonus Relentizar = Buscar(Aux, 2);
                Celda Celda = EncontrarCelda(Fila, Columna);
                if (Celda.SigB == null && Celda.Jugador == false && Relentizar.isActivo()) {
                    Celda.SigB = Relentizar;
                    Relentizar.setActivo(false);
                }
            }
            if (TipoBonus == 5) {
                Bonus Cambiarobjetivo = Buscar(Aux, 5);
                Celda Celda = EncontrarCelda(Fila, Columna);
                if (Celda.SigB == null && Celda.Jugador == false && Cambiarobjetivo.isActivo()) {
                    Celda.SigB = Cambiarobjetivo;
                    Cambiarobjetivo.setActivo(false);
                }
            }
            if (TipoBonus == 6) {
                Bonus Aleatorio = Buscar(Aux, 3);
                Celda Celda = EncontrarCelda(Fila, Columna);
                if (Celda.SigB == null && Celda.Jugador == false && Aleatorio.isActivo()) {
                    Celda.SigB = Aleatorio;
                    Aleatorio.setActivo(false);
                }
            }
            Cantidad += 1;
        }
        Celda Aux = Inicio;
        while (Aux != null) {
            if (Aux.SigB != null) {
                Aux.SigB.setActivo(true);
            }
            Aux = Aux.Sig;
        }
    }

//Agrega 2 o 3 jugadores en la lista
    public void JugadoresRandom() {
        int Jugadores = 1 + (int) (Math.random() * 2);
        while (Jugadores <= 3) {
            Celda Temp = Inicio;
            int Jugador1C = (int) (Math.random() * 9);
            int Jugador1F = (int) (Math.random() * 9);
            while (Temp != null) {
                if (Jugador1C == Temp.Columna && Jugador1F == Temp.Fila) {
                    if (!Temp.Jugador) {
                        if (Jugadores == 1) {
                            Persona persona = new Persona("Luis", Jugador1F, Jugador1C, Pintar.Jugador1);
                            Pintar.Persona1 = persona;
                            if (InicioP == null) {
                                InicioP = persona;
                            } else {
                                persona.Sig = InicioP;
                                InicioP = persona;
                            }
                        }
                        if (Jugadores == 2) {
                            Persona persona = new Persona("Esteban", Jugador1F, Jugador1C, Pintar.Jugador2);
                            Pintar.Persona2 = persona;
                            if (InicioP == null) {
                                InicioP = persona;
                            } else {
                                persona.Sig = InicioP;
                                InicioP = persona;
                            }
                        }
                        if (Jugadores == 3) {
                            Persona persona = new Persona("Diego", Jugador1F, Jugador1C, Pintar.Jugador3);
                            Pintar.Persona3 = persona;
                            if (InicioP == null) {
                                InicioP = persona;
                            } else {
                                persona.Sig = InicioP;
                                InicioP = persona;
                            }
                        }
                        Temp.Jugador = true;
                        Jugadores += 1;
                        break;
                    }
                } else {
                    Temp = Temp.Sig;
                }
            }
        }
    }
}
