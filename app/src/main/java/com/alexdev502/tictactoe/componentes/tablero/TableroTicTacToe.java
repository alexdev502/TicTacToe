package com.alexdev502.tictactoe.componentes.tablero;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alexdev502.tictactoe.R;

/**
 * Autor: Alexander Gutierrez alex.dev502@gmail.com
 */
public class TableroTicTacToe extends RelativeLayout implements View.OnClickListener {

    private static final int NUM_FILAS = 3;
    private static final int NUM_COLUMNAS = 3;
    private Integer[][] tablero;

    public static final int JUGADOR1 = 1;
    public static final int JUGADOR2 = 2;

    public static final int GANADOR_JUGADOR1 = JUGADOR1;
    public static final int GANADOR_JUGADOR2 = JUGADOR2;
    public static final int EMPATE = 3;

    private Drawable drawableJugador1 = null;
    private Drawable drawableJugador2 = null;

    private int jugadorActual;

    private boolean juegoTerminado;

    private View viewTablero;

    private OnFinDelJuegoListener onFinDelJuegoListener;
    private OnJugadorChangedListener onJugadorChangedListener;

    public TableroTicTacToe(Context context) {
        super(context);

        inicializar(null);
    }

    public TableroTicTacToe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inicializar(attrs);
    }

    private void inicializar(AttributeSet attributeSet) {
        viewTablero = View.inflate(getContext(), R.layout.tablero_tic_tac_toe, this);
        inicializarCeldas();

        tablero = new Integer[3][3];
        jugadorActual = JUGADOR1;
        juegoTerminado = false;


        if (attributeSet != null) {
            inicializarAttrs(attributeSet);
        }
    }

    private void inicializarAttrs(AttributeSet attrs) {
        TypedArray ta = this.getContext().obtainStyledAttributes(attrs, R.styleable.TableroTicTacToe);

        this.drawableJugador1 = ta.getDrawable(R.styleable.TableroTicTacToe_imagen_jugador1);
        this.drawableJugador2 = ta.getDrawable(R.styleable.TableroTicTacToe_imagen_jugador2);

        ta.recycle();

    }

    private void inicializarCeldas() {
        ((ImageView) viewTablero.findViewById(R.id.iv_f1_c1)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f1_c2)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f1_c3)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f2_c1)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f2_c2)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f2_c3)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f3_c1)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f3_c2)).setImageDrawable(null);
        ((ImageView) viewTablero.findViewById(R.id.iv_f3_c3)).setImageDrawable(null);

        viewTablero.findViewById(R.id.iv_f1_c1).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f1_c2).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f1_c3).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f2_c1).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f2_c2).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f2_c3).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f3_c1).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f3_c2).setOnClickListener(this);
        viewTablero.findViewById(R.id.iv_f3_c3).setOnClickListener(this);

        invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_f1_c1:
                clickCelda((ImageView) view, 1, 1);
                break;
            case R.id.iv_f1_c2:
                clickCelda((ImageView) view, 1, 2);
                break;
            case R.id.iv_f1_c3:
                clickCelda((ImageView) view, 1, 3);
                break;
            case R.id.iv_f2_c1:
                clickCelda((ImageView) view, 2, 1);
                break;
            case R.id.iv_f2_c2:
                clickCelda((ImageView) view, 2, 2);
                break;
            case R.id.iv_f2_c3:
                clickCelda((ImageView) view, 2, 3);
                break;
            case R.id.iv_f3_c1:
                clickCelda((ImageView) view, 3, 1);
                break;
            case R.id.iv_f3_c2:
                clickCelda((ImageView) view, 3, 2);
                break;
            case R.id.iv_f3_c3:
                clickCelda((ImageView) view, 3, 3);
                break;
        }
    }

    private void clickCelda(ImageView ivCelda, int fila, int columna) {
        if (this.juegoTerminado) return;

        int tempJugadorSiguiente;
        Drawable drawableJugadorActual;

        switch (this.jugadorActual) {
            case JUGADOR1:
                drawableJugadorActual = getDrawableJugador1();
                tempJugadorSiguiente = JUGADOR2;
                break;
            case JUGADOR2:
                drawableJugadorActual = getDrawableJugador2();
                tempJugadorSiguiente = JUGADOR1;
                break;
            default:
                //logica del negocio si se pierde el jugador actual
                return;
        }

        tablero[columna - 1][fila - 1] = this.jugadorActual;
        ivCelda.setImageDrawable(drawableJugadorActual);
        ivCelda.setOnClickListener(null);

        if (ValidarTablero.getInstance().validar(this.jugadorActual, tablero)) {
            callOnFinDelJuegoListener(this.jugadorActual);
            juegoTerminado = true;
            return;
        } else {
            if (tableroLleno()) {
                callOnFinDelJuegoListener(EMPATE);
                juegoTerminado = true;
                return;
            }
        }

        this.jugadorActual = tempJugadorSiguiente;
        callOnJugadorChangedListener(this.jugadorActual);
    }

    private boolean tableroLleno() {
        for (int columna = 0; columna < NUM_COLUMNAS; columna++) {
            for (int fila = 0; fila < NUM_FILAS; fila++) {
                if (tablero[columna][fila] == null) {
                    return false;
                }
            }
        }

        return true;
    }


    public Drawable getDrawableJugador1() {
        return (this.drawableJugador1 != null) ? drawableJugador1 : ContextCompat.getDrawable(getContext(), R.drawable.ic_jugador1_default);
    }

    public Drawable getDrawableJugador2() {
        return (this.drawableJugador2 != null) ? drawableJugador2 : ContextCompat.getDrawable(getContext(), R.drawable.ic_jugador2_default);
    }

    public void limpiar() {
        inicializarCeldas();

        for (int columna = 0; columna < NUM_COLUMNAS; columna++) {
            for (int fila = 0; fila < NUM_FILAS; fila++) {
                tablero[columna][fila] = null;
            }
        }

        jugadorActual = JUGADOR1;
        juegoTerminado = false;

        callOnJugadorChangedListener(this.jugadorActual);
    }

    public int getJugadorActual() {
        return jugadorActual;
    }

    public void setOnFinDelJuegoListener(OnFinDelJuegoListener onFinDelJuegoListener) {
        this.onFinDelJuegoListener = onFinDelJuegoListener;
    }

    private void callOnFinDelJuegoListener(int resultadoJuego) {
        if (onFinDelJuegoListener == null) return;

        onFinDelJuegoListener.onFinDelJuego(resultadoJuego);

    }

    public void setOnJugadorChangedListener(OnJugadorChangedListener onJugadorChangedListener) {
        this.onJugadorChangedListener = onJugadorChangedListener;
    }

    private void callOnJugadorChangedListener(int jugadorActual) {
        if (onJugadorChangedListener == null) return;

        onJugadorChangedListener.onJugadorChanged(jugadorActual);
    }

    public interface OnFinDelJuegoListener {
        void onFinDelJuego(int resultadoJuego);
    }

    public interface OnJugadorChangedListener {
        void onJugadorChanged(int jugadorActual);
    }
}
