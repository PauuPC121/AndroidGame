package com.example.ninja_game_v3;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Grafics {
    private Drawable drawable; //Imatge que dibuixarem
    private double posX, posY; //Posicio
    private double incX, incY; //Velocitat desplacament
    private int angle, rotacio; //Angle i velocitat rotacio
    private int amplada, altura; //Dimensions de la imatge
    private int radiColisio; //Per determinar col.lisio
    //On dibuixem el grafic (utilitzat en view.invalidate)
    private View view;
    // Per a determinar l'espai a esborrar (view.invalidate)
    public static final int MAX_VELOCITAT = 20;

    public Grafics setDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public Grafics setPosX(double posX) {
        this.posX = posX;
        return this;
    }

    public Grafics setPosY(double posY) {
        this.posY = posY;
        return this;
    }

    public Grafics setIncX(double incX) {
        this.incX = incX;
        return this;
    }

    public Grafics setIncY(double incY) {
        this.incY = incY;
        return this;
    }

    public Grafics setAngle(int angle) {
        this.angle = angle;
        return this;
    }

    public Grafics setRotacio(int rotacio) {
        this.rotacio = rotacio;
        return this;
    }

    public Grafics setAmplada(int amplada) {
        this.amplada = amplada;
        return this;
    }

    public Grafics setAltura(int altura) {
        this.altura = altura;
        return this;
    }

    public Grafics setRadiColisio(int radiColisio) {
        this.radiColisio = radiColisio;
        return this;
    }

    public Grafics setView(View view) {
        this.view = view;
        return this;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getIncX() {
        return incX;
    }

    public double getIncY() {
        return incY;
    }

    public int getAngle() {
        return angle;
    }

    public int getRotacio() {
        return rotacio;
    }

    public int getAmplada() {
        return amplada;
    }

    public int getAltura() {
        return altura;
    }

    public int getRadiColisio() {
        return radiColisio;
    }

    public View getView() {
        return view;
    }

    public Grafics(View view, Drawable drawable) {
        this.view = view;
        this.drawable = drawable;
        amplada = drawable.getIntrinsicWidth();
        altura = drawable.getIntrinsicHeight();
        radiColisio = (altura + amplada) / 4;
    }

    public void dibuixaGrafic(Canvas canvas) {
        canvas.save();
        int x = (int) (posX + amplada / 2);
        int y = (int) (posY + altura / 2);
        canvas.rotate((float) angle, (float) x, (float) y);
        drawable.setBounds((int) posX, (int) posY,
                (int) posX + amplada, (int) posY + altura);
        drawable.draw(canvas);
        canvas.restore();
        int rInval = (int) Math.hypot(amplada, altura) / 2 + MAX_VELOCITAT;
        view.invalidate(x - rInval, y - rInval, x + rInval, y + rInval);
    }



    public void incrementaPos(double factor) {
        posX += incX * factor;
        // Si sortim de la pantalla, corregim posició
        if (posX < -amplada / 2) {
            posX = view.getWidth() - amplada / 2;
        }
        if (posX > view.getWidth() - amplada / 2) {
            posX = -amplada / 2;
        }
        posY += incY * factor;
        if (posY < -altura / 2) {
            posY = view.getHeight() - altura / 2;
        }
        if (posY > view.getHeight() - altura / 2) {
            //noinspection IntegerDivisionInFloatingPointContext
            posY = -altura / 2;
        }
        angle += rotacio * factor; //Actualitzem angle
    }

    public double distancia(Grafics g) {
        return Math.hypot(posX - g.posX, posY - g.posY);
    }

    public boolean verificaColision(Grafics g) {
        return (distancia(g) < (radiColisio + g.radiColisio));
    }
}