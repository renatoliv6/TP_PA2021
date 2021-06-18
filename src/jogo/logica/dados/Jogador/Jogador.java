package jogo.logica.dados.Jogador;

import javafx.scene.shape.Circle;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Identificador;
import jogo.logica.dados.Peca;
import jogo.logica.dados.Tabuleiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Jogador implements Serializable
{
    public static final long serialVersionID = 1;

    String nome;
    Identificador identificador;
    protected int creditos;
    protected int nJogada;
    protected boolean pecaEspecial;

    public Jogador(String nome, Identificador identificador)
    {
        this.nome = nome;
        this.identificador = identificador;
        pecaEspecial = false;
        creditos = 5;
        nJogada = 0;
    }

    public String getNome() { return nome; }

    public Identificador getIdentificador() { return identificador; }

    public abstract int getnJogada();

    public abstract boolean getPecaEspecial();

    public abstract int getCreditos();

    public abstract void setnJogada(int nJogada);

    public abstract void setPecaEspecial(boolean ativar);

    public abstract void setCreditos(int credito);

    public abstract int calcularProximaJogada(Tabuleiro tabuleiro);

}
