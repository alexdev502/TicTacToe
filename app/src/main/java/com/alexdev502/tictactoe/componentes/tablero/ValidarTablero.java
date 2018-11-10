package com.alexdev502.tictactoe.componentes.tablero;

/**
 * Autor: Alexander Gutierrez alex.dev502@gmail.com
 */
public class ValidarTablero {

    private static final int NUM_FILAS = 3;
    private static final int NUM_COLUMNAS = 3;

    private static ValidarTablero instance;


    private ValidarTablero() {
    }

    public static ValidarTablero getInstance() {
        if (instance == null) {
            instance = new ValidarTablero();
        }

        return instance;
    }

    public boolean validar(int jugadorActual, Integer[][] tablero) {
        if(validarTransversalIzqSupDerInf(jugadorActual, tablero)){
            return true;
        }else if(validarTransversalIzqInfDerSup(jugadorActual, tablero)){
            return true;
        }else{
            //validar filas
            for(int fila = 0; fila < NUM_FILAS; fila++){
                if(validarFila(jugadorActual, fila, tablero)) return true;
            }

            //validar columnas
            for(int columna = 0; columna <NUM_COLUMNAS; columna++){
                if(validarColumna(jugadorActual, columna, tablero)) return true;
            }
        }

        return false;
    }

    private boolean validarFila(int jugadorActual, int fila, Integer[][] tablero) {
        for (int columna = 0; columna < NUM_COLUMNAS; columna++) {
            Integer valorCelda = tablero[columna][fila];
            if (valorCelda == null || valorCelda != jugadorActual) {
                return false;
            }
        }

        return true;
    }

    private boolean validarColumna(int jugadorActual, int columna, Integer[][] tablero) {
        for (int fila = 0; fila < NUM_FILAS; fila++) {
            Integer valorCelda = tablero[columna][fila];
            if (valorCelda == null || valorCelda != jugadorActual) {
                return false;
            }
        }

        return true;
    }

    private boolean validarTransversalIzqSupDerInf(int jugadorActual, Integer[][] tablero) {
        int columna = 0;
        int fila = 0;
        Integer valorIzqSup = tablero[columna][fila];

        columna = 1;
        fila = 1;
        Integer valorCentro = tablero[columna][fila];

        columna = 2;
        fila = 2;
        Integer valorDerInf = tablero[columna][fila];

        return (valorIzqSup != null && valorIzqSup == jugadorActual) &&
                (valorCentro != null && valorCentro == jugadorActual) &&
                (valorDerInf != null && valorDerInf == jugadorActual);
    }

    private boolean validarTransversalIzqInfDerSup(int jugadorActual, Integer[][] tablero) {
        int columna = 0;
        int fila = 2;
        Integer valorIzqInf = tablero[columna][fila];

        columna = 1;
        fila = 1;
        Integer valorCentro = tablero[columna][fila];

        columna = 2;
        fila = 0;
        Integer valorDerSup = tablero[columna][fila];

        return (valorIzqInf != null && valorIzqInf == jugadorActual) &&
                (valorCentro != null && valorCentro == jugadorActual) &&
                (valorDerSup != null && valorDerSup == jugadorActual);
    }
}
