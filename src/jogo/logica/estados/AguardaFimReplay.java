package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;

public class AguardaFimReplay extends EstadoAdapter
{
    public AguardaFimReplay(DadosJogo dadosJogo) { super(dadosJogo); }

    @Override
    public IEstado terminarJogo() { return new TerminarJogo(getDadosJogo()); }

    @Override
    public Situacao getSituacao()
    {
        return Situacao.AGUARDAR_FIM_REPLAY;
    }
}
