package jogo.logica;

import jogo.logica.Jogo;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.Jogador.Virtual;
import jogo.logica.dados.MiniJogo.MiniJogo;
import jogo.logica.dados.Peca;
import jogo.logica.dados.Tabuleiro;
import jogo.logica.dados.memento.Memento;
import jogo.logica.estados.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JogoCareTaker implements Serializable
{
    public static final long serialVersionID = 1;

    private Jogo jogo;

    private List<Memento> historicoMementosJogadas = new ArrayList<>();
    private List<Memento> historicoMementosReplay = new ArrayList<>();

    public JogoCareTaker() { this.jogo = new Jogo(); }

    //------------------------------ Methods that enable accessing the data/status of the game ----------------------------

    public Jogador getAtualmenteAJogar() { return jogo.getAtualmenteAJogar(); }

    public Jogador getProximoAJogar() { return jogo.getProximoAJogar(); }

    public List<String> getMsgLog()
    {
        return jogo.getMsgLog();
    }

    public MiniJogo getMiniJogoAtual() { return jogo.getMiniJogoAtual(); }

    public Tabuleiro getTabuleiro() { return jogo.getTabuleiro(); }

    public int getLarguraTabuleiro() { return jogo.getLarguraTabuleiro(); }

    public int getAlturaTabuleiro() { return jogo.getAlturaTabuleiro(); }

    public boolean getMiniJogoADecorrer(){ return jogo.getMiniJogoADecorrer(); }

    public String getNomeJogo() { return jogo.getNomeJogo(); }

    public boolean isJogoConcluido() { return jogo.isJogoConcluido(); }

    public boolean jogoTerminou() { return jogo.jogoTerminou();}

    @Override
    public String toString() { return jogo.toString(); }

    //--------------------- Methods that trigger events/actions in the finite state machine  -----------------------

    public void definirModoJogo() { jogo.definirModoJogo(); }

    public void inserirJogadores(List<Jogador> jogadores)
    {
        jogo.inserirJogadores(jogadores);
        gravaMementoReplay();
    }

    public void iniciar()
    {
        jogo.iniciar();
        gravaMementoReplay();
    }

    public void iniciarMiniJogo()
    {
        jogo.iniciarMiniJogo();
        gravaMementoReplay();
    }

    public void inserirRespostaMiniJogo(String resposta)
    {
        jogo.inserirRespostaMiniJogo(resposta);
        gravaMementoReplay();
    }

    public void terminarJogo()
    {
        gravaMementoReplay();
        jogo.terminarJogo();
    }

    public void jogar(int nColuna)
    {
        gravaMementoJogadas();
        jogo.jogar(nColuna);
        gravaMementoReplay();
    }

    public void passaJogada(boolean res)
    {
        gravaMementoJogadas();
        jogo.passaJogada(res);
        gravaMementoReplay();
    }

    public void usarPecaEspecial(int ncoluna)
    {
        jogo.usarPecaEspecial(ncoluna);
        gravaMementoReplay();
    }

    public boolean verificaPossivelVoltarAtras(int nCreditos) { return jogo.verificaPossivelVoltarAtras(nCreditos); }

    public boolean verificaInicioMiniJogo() { return jogo.verificaInicioMiniJogo(); }

    public boolean mostrarReplay() throws IOException, ClassNotFoundException {

        if(historicoMementosReplay.size() > 0)
        {
            if(jogo.getMiniJogoADecorrer())
            {
                while(jogo.getMiniJogoADecorrer())
                {
                    historicoMementosReplay.remove(0);
                    jogo.restoreMemento(historicoMementosReplay.get(0), true);
                }
            }
            else
            {
                jogo.restoreMemento(historicoMementosReplay.get(0), true);
                historicoMementosReplay.remove(0);
            }

            jogo.mostrarReplay();
            return true;
        }

        jogo.terminarJogo();

        return false;
    }

    private void gravaMementoJogadas()
    {
        try {
            historicoMementosJogadas.add(jogo.createMemento(false));
        }catch (IOException ex)
        {
            System.out.println("gravaMemento: " + ex);
            historicoMementosJogadas.clear();
        }

    }

    private void gravaMementoReplay()
    {
        try {
            historicoMementosReplay.add(jogo.createMemento(true));
        }catch (IOException ex)
        {
            System.out.println("gravaMemento: " + ex);
            historicoMementosReplay.clear();
        }

    }

    public void undo(int qtd)
    {
        if(historicoMementosJogadas.isEmpty())
            return;

        try
        {
            Jogador jRemoverCreditos = jogo.getAtualmenteAJogar();

            Memento anterior = historicoMementosJogadas.get(historicoMementosJogadas.size()-qtd);
            jogo.restoreMemento(anterior, false);

            for (int i=1; i<=qtd; i++)
                historicoMementosJogadas.remove(historicoMementosJogadas.size()-1);

            jogo.voltarAtras(qtd, jRemoverCreditos);

        }catch (IOException | ClassNotFoundException ex)
        {
            System.out.println("voltar atrás: "+ ex);
            historicoMementosJogadas.clear();
        }

    }

    public Situacao getEstado() {
        return jogo.getEstadoAtual();
    }

}
