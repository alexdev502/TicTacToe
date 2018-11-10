package com.alexdev502.tictactoe.modelo;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexdev502.tictactoe.R;

/**
 * Autor: Alexander Gutierrez alex.dev502@gmail.com
 */
public class JuegoData {

    private Context context;

    public JuegoData(Context context) {
        this.context = context;
    }

    public void onDestroy() {
        this.context = null;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(getString(R.string.file_preference_key), Context.MODE_PRIVATE);
    }

    public void setJuegoGanado(int jugador) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String llave = getString(R.string.sp_key_jugador, jugador);

        int marcador = sharedPreferences.getInt(llave, 0);
        marcador++;


        sharedPreferences
                .edit()
                .putInt(llave, marcador)
                .apply();
    }

    public void juegoEmpatado() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String llave = getString(R.string.sp_key_empate);
        int empate = sharedPreferences.getInt(llave, 0);
        empate++;

        sharedPreferences
                .edit()
                .putInt(llave, empate)
                .apply();
    }

    public int getJuegosGanados(int jugador) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String llave = getString(R.string.sp_key_jugador, jugador);

        return sharedPreferences.getInt(llave, 0);
    }

    public int getJuegosEmpatados() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String llave = getString(R.string.sp_key_empate);

        return sharedPreferences.getInt(llave, 0);
    }

    public void limpiar(){
        getSharedPreferences().edit().clear().commit();
    }

    /**
     * Obteiene el string asociado al parametro resId y que se encuentre en el archivo values/Strings
     *
     * @param resId Id del recurso en el archvio values/Strings
     * @return Cadena equivalente al id buscado
     */
    private String getString(int resId) {
        return context.getString(resId);
    }

    private String getString(int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }
}
