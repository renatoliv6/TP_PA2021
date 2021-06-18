package jogo.logica.dados;

import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.Jogador.Virtual;

import java.io.Serializable;

public class Tabuleiro implements Serializable
{
    public final static int LARGURA = 7;
    public final static int ALTURA = 6;

    Peca[][] pecas;

    public Tabuleiro() { pecas = new Peca[ALTURA][LARGURA]; }

    public Peca getPeca(int linha, int coluna) { return pecas[linha][coluna]; }

    public int addPeca(int nColuna, Jogador j) {
        int linha = 0;

        if(j instanceof Virtual)
            nColuna = j.calcularProximaJogada(this);

        if(!isColunaLivre(nColuna))
            return -1;

        linha = verificarLinhaInserir(nColuna);
        if(linha != -1 )
        {
            pecas[linha][nColuna - 1] = new Peca(linha, nColuna, j.getIdentificador());
        }
        else
            return -1;

        return nColuna;
    }

    public boolean removerColuna(int nColuna)
    {
        if(nColuna <0 || nColuna > LARGURA)
            return false;

        for (int i = 0; i<ALTURA;i++)
            pecas[i][nColuna-1] = null;

        return true;
    }

    public boolean isColunaLivre(int nColuna)
    {
        if(nColuna > LARGURA) return false;

        for (int i = 0; i<ALTURA;i++)
        {
            if(pecas[i][nColuna-1] == null)
                return true;
        }
        return false;
    }

    public boolean isTabuleiroCheio()
    {

        for (int i = 0; i< Tabuleiro.ALTURA ;i++)
        {
            for(int j= 0; j< Tabuleiro.LARGURA; j++)
            {
                if(getPeca(i, j) == null)
                    return false;
            }
        }

        return true;
    }

    public int verificarLinhaInserir(int ncoluna)
    {
        int poslinha = -1;

        for (int i = 0; i< Tabuleiro.ALTURA;i++)
        {
            for(int j= Tabuleiro.LARGURA-1; j>=0 ; j--)
            {
                if(j == ncoluna - 1)
                {
                    if(getPeca(i, j) == null)
                        poslinha = i;
                }
            }
        }

        return poslinha;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        s.append("Tabuleiro: ");
        s.append("\n");

        for(int j= 0; j< LARGURA; j++)
        {
            s.append(String.format("%3d ",j+1));
        }

        s.append("\n");
        for (int i = 0; i < ALTURA ;i++)
        {
            for(int j= 0; j< LARGURA; j++)
            {
                if(j == 0)
                    s.append(String.format("%c", '|'));

                Peca peca = getPeca(i,j);
                if(peca != null)
                {
                    s.append(String.format(" %s",peca));
                    s.append(String.format("%2c", '|'));
                }
                else
                    s.append(String.format("%4c", '|'));
            }

            s.append(System.lineSeparator());

        }

        return s.toString();
    }
}

