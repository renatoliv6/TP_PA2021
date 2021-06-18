package jogo.logica.dados.Jogador;

import jogo.logica.dados.Identificador;
import jogo.logica.dados.Tabuleiro;

public class Humano extends Jogador {
    public Humano(String nome, Identificador id) { super(nome, id); }

    @Override
    public int getnJogada (){ return nJogada;}

    @Override
    public boolean getPecaEspecial() { return pecaEspecial; }

    @Override
    public int getCreditos() { return creditos; }

    @Override
    public void setnJogada(int nJogada) { super.nJogada = nJogada; }

    @Override
    public void setPecaEspecial(boolean ativar) { pecaEspecial = ativar; }

    @Override
    public void setCreditos(int creditos) { this.creditos = creditos; }


    @Override
    public int calcularProximaJogada(Tabuleiro tabuleiro){ return -1; };
}
