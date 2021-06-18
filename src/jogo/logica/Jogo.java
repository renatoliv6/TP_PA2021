package jogo.logica;

import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.MiniJogo.MiniJogo;
import jogo.logica.dados.Tabuleiro;
import jogo.logica.dados.memento.IOriginator;
import jogo.logica.dados.memento.Memento;
import jogo.logica.estados.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Renato Oliveira
 */
public class Jogo implements Serializable, IOriginator
{
    public static final long serialVersionUID =  1L;

    private DadosJogo dadosJogo;
    private IEstado estado;

    public Jogo()
    {
        dadosJogo = new DadosJogo();
        estado = new AguardaInicio(dadosJogo);
    }

    //------------------------------ Methods that enable accessing the data/status of the game ----------------------------

    public Jogador getAtualmenteAJogar() { return dadosJogo.getAtualmenteAJogar(); }

    public Jogador getProximoAJogar() { return dadosJogo.getProximoAJogar(); }

    public List<String> getMsgLog()
    {
        return dadosJogo.getMsgLog();
    }

    public boolean getMiniJogoADecorrer(){ return dadosJogo.getMiniJogoADecorrer(); }

    public Tabuleiro getTabuleiro() { return dadosJogo.getTabuleiro(); }

    public int getLarguraTabuleiro() { return dadosJogo.getTabuleiro().LARGURA; }

    public int getAlturaTabuleiro() { return dadosJogo.getTabuleiro().ALTURA; }

    public String getNomeJogo() { return dadosJogo.nome; }

    public MiniJogo getMiniJogoAtual() { return dadosJogo.getMiniJogoAtual();}

    public Situacao getEstadoAtual() {
        return estado.getSituacao();
    }

    public boolean verificaPossivelVoltarAtras(int nCreditos) { return dadosJogo.verificaPossivelVoltarAtras(nCreditos); }

    public boolean verificaInicioMiniJogo() { return dadosJogo.verificaInicioMinijogo(); }

    public void setDadosJogo(DadosJogo dadosJogo) { this.dadosJogo = dadosJogo;}

    public void voltarAtras(int qtd, Jogador jRemoverCreditos) {
        dadosJogo.voltarAtras(qtd, jRemoverCreditos);
    }

    public boolean isJogoConcluido() { return dadosJogo.isJogoConcluido(); }

    public boolean jogoTerminou() { return dadosJogo.jogoTerminou();}

    @Override
    public String toString() { return dadosJogo.toString(); }

    //--------------------- Methods that trigger events/actions in the finite state machine  -----------------------

    public void definirModoJogo() { estado = estado.definirModoJogo(); }

    public void mostrarReplay() { estado = estado.mostrarReplay(); }

    public void inserirJogadores(List<Jogador> jogadores) { estado = estado.inserirJogadores(jogadores); }

    public void iniciar() { estado = estado.iniciar(); }

    public void iniciarMiniJogo() { estado = estado.iniciarMiniJogo(); }

    public void inserirRespostaMiniJogo(String resposta) { estado = estado.inserirRespostaMiniJogo(resposta); }

    public void terminarJogo() { estado = estado.terminarJogo(); }

    public void jogar(int ncoluna) { estado = estado.jogar(ncoluna); }

    public void passaJogada(boolean res) { estado = estado.passaJogada(res); }

    public void usarPecaEspecial(int ncoluna){ estado = estado.usarPecaEspecial(ncoluna); }

    @Override
    public Memento createMemento(boolean replay) throws IOException {
        if(!replay)
            return new Memento(estado);
        else
            return new Memento(dadosJogo);
    }

    @Override
    public void restoreMemento(Memento memento, boolean replay) throws IOException, ClassNotFoundException
    {
        if(!replay)
        {
            this.estado = ((IEstado) memento.getSnapshot());
            dadosJogo = estado.getDadosJogo();
        }
        else
            dadosJogo = ((DadosJogo) memento.getSnapshot());

    }

}
