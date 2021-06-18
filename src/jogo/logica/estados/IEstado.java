package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.DadosJogo;
import jogo.logica.dados.Jogador.Jogador;

import java.io.IOException;
import java.util.List;

public interface IEstado
{
    IEstado iniciar();
    IEstado iniciarMiniJogo();
    IEstado terminarJogo();
    IEstado jogar(int coluna);
    IEstado passaJogada(boolean res);
    IEstado definirModoJogo();
    IEstado mostrarReplay();
    IEstado inserirJogadores(List<Jogador> jogadores);
    IEstado inserirRespostaMiniJogo(String resposta);
    IEstado usarPecaEspecial(int coluna);

    DadosJogo getDadosJogo();
    Situacao getSituacao();
}
