package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.MiniJogo.Matematica;
import jogo.logica.dados.MiniJogo.MiniJogo;

public class AguardaJogada extends EstadoAdapter
{
    public AguardaJogada(DadosJogo dadosJogo) { super(dadosJogo); }

    @Override
    public IEstado jogar(int nColuna)
    {
        DadosJogo dadosJogo = getDadosJogo();
        int colunaPecaJogada;

        colunaPecaJogada = dadosJogo.addPeca(nColuna);

        if(dadosJogo.jogoTerminou()) {
            return new TerminarJogo(getDadosJogo());
        }

        if(colunaPecaJogada == -1)
        {
            dadosJogo.addMsgLog("Escolha nova coluna para jogar!");
            return this;
        }

        dadosJogo.addMsgLog("Jogador " + dadosJogo.getProximoAJogar().getNome() + " jogou na coluna " + colunaPecaJogada);

        return new AguardaJogada(getDadosJogo());
    }

    @Override
    public IEstado passaJogada(boolean res){
        if (res)
            getDadosJogo().trocaJogadores();

        getDadosJogo().setMiniJogoADecorrer(false);

        return this;
    }

    @Override
    public IEstado usarPecaEspecial(int coluna)
    {
        DadosJogo dadosJogo = getDadosJogo();

        if(dadosJogo.usarPecaEspecial(coluna))
            dadosJogo.addMsgLog("Peça especial utilizada!");
        else
            dadosJogo.addMsgLog("Erro ao usar peça especial!");

        return new AguardaJogada(getDadosJogo());
    }

    @Override
    public IEstado iniciarMiniJogo()
    {
        DadosJogo dadosJogo = getDadosJogo();

        MiniJogo jogo = dadosJogo.ativarMiniJogo();

        dadosJogo.addMsgLog("A iniciar Mini Jogo!");

        if(jogo instanceof Matematica)
            return new AguardaFimMiniJogoMatematica(getDadosJogo());
        else
            return new AguardaFimMiniJogoPalavras(getDadosJogo());
    }

    @Override
    public IEstado terminarJogo() { return new TerminarJogo(getDadosJogo()); }

    @Override
    public Situacao getSituacao()
    {
        return Situacao.AGUARDAR_JOGADA;
    }

}
