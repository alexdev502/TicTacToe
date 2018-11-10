package com.alexdev502.tictactoe.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alexdev502.tictactoe.controlador.JuegoData;
import com.alexdev502.tictactoe.R;
import com.alexdev502.tictactoe.componentes.tablero.TableroTicTacToe;

/**
 * Autor: Alexander Gutierrez alex.dev502@gmail.com
 */
public class JuegoActivity extends AppCompatActivity implements TableroTicTacToe.OnFinDelJuegoListener, TableroTicTacToe.OnJugadorChangedListener {

    private JuegoData juegoData;

    private TableroTicTacToe tableroTicTacToe;
    private TextView tvJugadorActual;
    private TextView tvVictoriasJ1;
    private TextView tvVictoriasJ2;
    private TextView tvEmpate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        juegoData = new JuegoData(this);

        tvJugadorActual = findViewById(R.id.tvJugadorActual);
        tvVictoriasJ1 = findViewById(R.id.tvVictoriasJ1);
        tvVictoriasJ2 = findViewById(R.id.tvVictoriasJ2);
        tvEmpate = findViewById(R.id.tvEmpate);
        tableroTicTacToe = findViewById(R.id.tableroTicTacToeView);

        tableroTicTacToe.setOnFinDelJuegoListener(this);
        tableroTicTacToe.setOnJugadorChangedListener(this);

        findViewById(R.id.btnLimpiar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableroTicTacToe.limpiar();
            }
        });

        findViewById(R.id.ivEliminarInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juegoData.limpiar();
                mostrarMarcadores();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mostrarJugadorActual(tableroTicTacToe.getJugadorActual());
        mostrarMarcadores();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        juegoData.onDestroy();
    }

    @Override
    public void onFinDelJuego(int resultadoJuego) {
        String mensaje = "";
        switch (resultadoJuego) {
            case TableroTicTacToe.GANADOR_JUGADOR1:
            case TableroTicTacToe.GANADOR_JUGADOR2:
                juegoData.setJuegoGanado(resultadoJuego);
                mensaje = getString(R.string.jugador_gana, resultadoJuego);
                break;
            case TableroTicTacToe.EMPATE:
                juegoData.juegoEmpatado();
                mensaje = getString(R.string.juego_empatado);
                break;
        }

        mostrarMarcadores();
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onJugadorChanged(int jugadorActual) {
        mostrarJugadorActual(jugadorActual);
    }

    private void mostrarJugadorActual(int jugadorActual) {
        String sJugadorActual = getString(R.string.jugador, jugadorActual);
        tvJugadorActual.setText(sJugadorActual);
    }

    private void mostrarMarcadores() {
        int victoriasJugador1 = juegoData.getJuegosGanados(TableroTicTacToe.JUGADOR1);
        int victoriasJugador2 = juegoData.getJuegosGanados(TableroTicTacToe.JUGADOR2);
        int empate = juegoData.getJuegosEmpatados();

        tvVictoriasJ1.setText(String.valueOf(victoriasJugador1));
        tvVictoriasJ2.setText(String.valueOf(victoriasJugador2));
        tvEmpate.setText(String.valueOf(empate));
    }


}
