package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.Peca;

import java.util.Collections;
import java.util.List;

public class AguardaInicio extends EstadoAdapter
{
    public AguardaInicio(DadosJogo dadosJogo) { super(dadosJogo); }

    @Override
    public IEstado definirModoJogo()
    {
        return new AguardaDefinirModoJogo(getDadosJogo());
    }

    @Override
    public IEstado mostrarReplay()
    {
        return new AguardaFimReplay(getDadosJogo());
    }

    @Override
    public IEstado terminarJogo() { return new TerminarJogo(getDadosJogo()); }

    @Override
    public Situacao getSituacao()
    {
        return Situacao.AGUARDA_INICIO;
    }
}
