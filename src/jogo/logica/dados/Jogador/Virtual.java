package jogo.logica.dados.Jogador;

import javafx.scene.shape.Circle;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Identificador;
import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.Peca;
import jogo.logica.dados.Tabuleiro;

import java.util.List;

public class Virtual extends Jogador {
    public Virtual(String nome, Identificador id)
    {
        super(nome, id);
    }

    @Override
    public int calcularProximaJogada(Tabuleiro tabuleiro)
    {
        int coluna = 0;

        coluna = (int)((Math.random() * tabuleiro.LARGURA) + 1);

        return coluna;
    }

    @Override
    public int getnJogada (){ return -1;}

    @Override
    public boolean getPecaEspecial() { return false; }

    @Override
    public int getCreditos() { return 0; }

    @Override
    public void setnJogada(int nJogada) { }

    @Override
    public void setPecaEspecial(boolean ativar) { pecaEspecial = false; }

    @Override
    public void setCreditos(int creditos) { }

}
