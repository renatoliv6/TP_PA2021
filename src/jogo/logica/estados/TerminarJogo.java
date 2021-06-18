package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;

public class TerminarJogo extends EstadoAdapter
{
    public TerminarJogo(DadosJogo dadosJogo) { super(dadosJogo); }

    @Override
    public IEstado iniciar()
    {
        getDadosJogo().initialize();
        return new AguardaInicio(getDadosJogo());
    }

    @Override
    public String toString() { return "Jogo Terminou"; }

    @Override
    public Situacao getSituacao()
    {
        return Situacao.AGUARDAR_TERMINAR;
    }
}
