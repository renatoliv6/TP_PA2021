package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.MiniJogo.MiniJogo;

public class AguardaFimMiniJogoPalavras extends EstadoAdapter
{
    public AguardaFimMiniJogoPalavras(DadosJogo dadosJogo) { super(dadosJogo); }

    @Override
    public IEstado inserirRespostaMiniJogo(String resposta)
    {
        DadosJogo dadosJogo = getDadosJogo();

        MiniJogo jogo = dadosJogo.getMiniJogoAtual();

        jogo.insereResposta(resposta);

        if(jogo.getGanhou())
        {
            dadosJogo.addMsgLog("Ganhou a peça especial!!");
            dadosJogo.getAtualmenteAJogar().setPecaEspecial(true);
            return new AguardaJogada(getDadosJogo());
        }
        else
        {
            dadosJogo.addMsgLog("Perdeu a sua vez de jogar!!");
            return new AguardaJogada(getDadosJogo());
        }

    }

    @Override
    public Situacao getSituacao()
    {
        return Situacao.AGUARDAR_FIM_MINI_JOGO_PT;
    }
}
