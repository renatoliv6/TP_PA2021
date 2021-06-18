package jogo.logica.estados;

import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Jogador.Jogador;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Renato Oliveira
 */

public abstract class EstadoAdapter implements IEstado, Serializable
{

    private DadosJogo g;

    public EstadoAdapter(DadosJogo dadosJogo) { this.g = dadosJogo; }

    public DadosJogo getDadosJogo() { return g; }

    @Override
    public IEstado iniciar(){ return this; }

    @Override
    public IEstado iniciarMiniJogo(){ return this; }

    @Override
    public IEstado terminarJogo(){ return this; }

    @Override
    public IEstado jogar(int coluna){ return this; }

    @Override
    public IEstado passaJogada(boolean res){ return this; }

    @Override
    public IEstado definirModoJogo(){ return this; }

    @Override
    public IEstado mostrarReplay()
    {
        return new AguardaFimReplay(getDadosJogo());
    }

    @Override
    public IEstado inserirJogadores(List<Jogador> jogadores){ return this; }

    @Override
    public IEstado inserirRespostaMiniJogo(String resposta){ return this; }

    @Override
    public IEstado usarPecaEspecial(int coluna) {return this; }

}
