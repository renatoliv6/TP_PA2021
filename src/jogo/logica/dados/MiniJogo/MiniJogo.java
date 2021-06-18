package jogo.logica.dados.MiniJogo;

import jogo.logica.dados.DadosJogo;

import java.io.Serializable;

public abstract class MiniJogo implements Serializable
{
    public static final long serialVersionID = 1;

    protected DadosJogo dadosJogo;
    protected boolean resultado;

    long tempoInicial;
    long tempoFinal;

    protected int tempoPassado;

    public MiniJogo(DadosJogo dadosJogo, boolean ganhou) {
        this.dadosJogo = dadosJogo;
        tempoInicial = 0;
        tempoFinal = 0;
        tempoPassado = 0;
        resultado = false;
    }

    public abstract boolean getGanhou();

    // Tempo que o jogador tem até terminar o jogo
    public abstract int getTempo();

    public abstract int getnRespostas();

    public abstract int getnPerguntas();

    public boolean getResultado() { return resultado; }

    public abstract void gerarInputJogo();

    public abstract void insereResposta(String resposta);

    public void setInicioTemporizador() { tempoInicial = System.currentTimeMillis(); }

    public void setFinalTemporizador() {
        tempoFinal = System.currentTimeMillis();
        tempoPassado = (int)((tempoFinal - tempoInicial) / 1000);
    }

    public int getTempoPassado() { return tempoPassado; }

    public void setResultado(boolean b) {
        resultado = b;
    }
}
