package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Jogador.Jogador;

import java.util.List;

public class AguardaDefinirModoJogo extends EstadoAdapter
{
    public AguardaDefinirModoJogo(DadosJogo dadosJogo) { super(dadosJogo); }

    @Override
    public IEstado iniciar()
    {
        getDadosJogo().initialize();
        return new AguardaInicio(getDadosJogo());
    }

    @Override
    public IEstado inserirJogadores(List<Jogador> jogadores)
    {
        DadosJogo dadosJogo = getDadosJogo();
        dadosJogo.addJogadores(jogadores);

        dadosJogo.addMsgLog("Jogadores sorteados, o primeiro a jogar é: " + dadosJogo.getAtualmenteAJogar().getNome());

        return new AguardaJogada(getDadosJogo());
    }

    @Override
    public IEstado terminarJogo() { return new TerminarJogo(getDadosJogo()); }

    @Override
    public Situacao getSituacao()
    {
        return Situacao.AGUARDAR_DEFINIR_MODO_JOGO;
    }
}
