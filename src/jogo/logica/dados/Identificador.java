package jogo.logica.dados;

import java.io.Serializable;

public class Identificador implements Serializable
{
    protected double r,g,b;

    public Identificador(double r, double g, double b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public double getR() { return r; }

    public double getG() { return g; }

    public double getB() { return b; }

}
