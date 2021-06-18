package jogo.logica.dados;

import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.Jogador.Virtual;
import jogo.logica.dados.MiniJogo.Matematica;
import jogo.logica.dados.MiniJogo.MiniJogo;
import jogo.logica.dados.MiniJogo.Palavras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class DadosJogo implements Serializable
{
    public static final long serialVersionID = 1;

    static int nextId = 1;
    int id;

    public String nome;

    private Tabuleiro tabuleiro;
    private List<String> msgLog;

    MiniJogo miniJogoAtual;
    Jogador jogadorAtual;
    Jogador proximoJogar;

    boolean miniJogoaDecorrer;
    boolean jogoConcluido;

    public DadosJogo() { initialize(); }

    public void initialize()
    {
        id = nextId++;
        nome = "Jogo"+id;
        tabuleiro = new Tabuleiro();
        msgLog = new ArrayList<>();
        miniJogoaDecorrer = false;
        msgLog.clear();
        jogoConcluido = false;
    }

    public void trocaJogadores() {
        Jogador jogadoraux = jogadorAtual;
        jogadorAtual = proximoJogar;
        proximoJogar = jogadoraux;
    }

    protected void setJogadorAtual(List<Jogador> jogadores) { jogadorAtual = jogadores.get(0); }

    protected void setProximoJogar(List<Jogador> jogadores) { proximoJogar = jogadores.get(1); }

    public void setMiniJogoADecorrer(boolean ativo){ miniJogoaDecorrer = ativo; }

    public void addJogadores(List<Jogador> jogadores) {
        Collections.shuffle(jogadores);
        setJogadorAtual(jogadores);
        setProximoJogar(jogadores);
    }

    public int addPeca(int nColuna)
    {
        int resultado = tabuleiro.addPeca(nColuna, jogadorAtual);
        if(resultado != -1) {
            jogadorAtual.setnJogada((jogadorAtual.getnJogada() + 1));
        }
        return resultado;
    }

    public boolean usarPecaEspecial(int coluna) {
        if(coluna != -1)
        {
            jogadorAtual.setPecaEspecial(false);
            getMiniJogoAtual().setResultado(false);
            trocaJogadores();
            return tabuleiro.removerColuna(coluna);
        }

        return false;
    }

    public void addMsgLog(String msg) { msgLog.add(msg); }

    public MiniJogo ativarMiniJogo()
    {
        int rand = new Random().nextInt(2);

        if(rand == 0)
            miniJogoAtual = new Matematica(this, 5);
        else
            miniJogoAtual = new Palavras(this, 5);

        miniJogoAtual.setInicioTemporizador();
        miniJogoAtual.gerarInputJogo();
        miniJogoaDecorrer = true;

        return miniJogoAtual;

    }

    public List<String> getMsgLog() { return msgLog; }

    public Tabuleiro getTabuleiro() { return tabuleiro; }

    public Jogador getAtualmenteAJogar() { return jogadorAtual; }

    public Jogador getProximoAJogar() { return proximoJogar; }

    public MiniJogo getMiniJogoAtual() { return miniJogoAtual; }

    public boolean getMiniJogoADecorrer(){ return miniJogoaDecorrer; }

    public boolean isJogoConcluido() { return jogoConcluido; }

    public boolean verificaPossivelVoltarAtras(int creditos)
    {
        if(creditos > jogadorAtual.getCreditos())
        {
            addMsgLog("Não foi possivel voltar " + creditos + " para trás.");
            return false;
        }

        return true;
    }

    public boolean verificaInicioMinijogo()
    {
        if(jogadorAtual.getnJogada()+1 != 4 || jogadorAtual instanceof Virtual)
            return false;

        //removerUltimaPecaJogada();
        jogadorAtual.setnJogada(0);
        return true;

    }

    public boolean isDiagonalWin(Jogador jogador)
    {

        // Verificação diagonal ascendente
        for (int i=3; i<Tabuleiro.ALTURA; i++){
            for (int j=0; j<Tabuleiro.LARGURA-3; j++){
                if(tabuleiro.getPeca(i,j) != null &&
                        tabuleiro.getPeca(i-1,j+1) != null &&
                        tabuleiro.getPeca(i-2,j+2) != null &&
                        tabuleiro.getPeca(i-3,j+3) != null)
                {
                    if (tabuleiro.getPeca(i,j).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i-1,j+1).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i-2,j+2).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i-3,j+3).getIdentificador() == jogador.getIdentificador())
                        return true;
                }
            }
        }

        // Verificação diagonal descendente
        for (int i=3; i<Tabuleiro.ALTURA; i++){
            for (int j=3; j<Tabuleiro.LARGURA; j++){
                if ( tabuleiro.getPeca(i,j) != null &&
                        tabuleiro.getPeca(i-1,j-1) != null &&
                        tabuleiro.getPeca(i-2,j-2) != null &&
                        tabuleiro.getPeca(i-3,j-3) != null)
                {
                    if (tabuleiro.getPeca(i,j).getIdentificador() == jogador.getIdentificador() &&
                        tabuleiro.getPeca(i-1,j-1).getIdentificador() == jogador.getIdentificador() &&
                        tabuleiro.getPeca(i-2,j-2).getIdentificador() == jogador.getIdentificador() &&
                        tabuleiro.getPeca(i-3,j-3).getIdentificador() == jogador.getIdentificador())
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isHorizontalWin(Jogador jogador)
    {
        // Verificação Horizontal
        for (int j = 0; j<Tabuleiro.LARGURA-3 ; j++ ){
            for (int i = 0; i<Tabuleiro.ALTURA; i++){
                if (tabuleiro.getPeca(i,j) != null &&
                        tabuleiro.getPeca(i,j+1) != null &&
                        tabuleiro.getPeca(i,j+2) != null &&
                        tabuleiro.getPeca(i,j+3) != null) {
                    if (tabuleiro.getPeca(i,j).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i,j+1).getIdentificador() == jogador.getIdentificador()&&
                            tabuleiro.getPeca(i,j+2).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i,j+3).getIdentificador() == jogador.getIdentificador()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isVerticalWin(Jogador jogador)
    {
        //Verificação vertical
        for (int i = 0; i<Tabuleiro.ALTURA-3 ; i++ ){
            for (int j = 0; j<Tabuleiro.LARGURA; j++){
                if (tabuleiro.getPeca(i,j) != null &&
                        tabuleiro.getPeca(i+1,j) != null &&
                        tabuleiro.getPeca(i+2,j) != null &&
                        tabuleiro.getPeca(i+3,j) != null) {
                    if (tabuleiro.getPeca(i,j).identificador == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i+1,j).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i+2,j).getIdentificador() == jogador.getIdentificador() &&
                            tabuleiro.getPeca(i+3,j).getIdentificador() == jogador.getIdentificador()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isTabuleiroCheio(){ return tabuleiro.isTabuleiroCheio(); }

    public boolean jogoTerminou()
    {
        if(isHorizontalWin(jogadorAtual) || isVerticalWin(jogadorAtual) || isDiagonalWin(jogadorAtual))
        {
            msgLog.add("Jogo Terminou! O vencedor é " + jogadorAtual.getNome());
            jogoConcluido = true;
            return true;
        }
        else if(isTabuleiroCheio())
        {
            msgLog.add("Jogo Terminou empatado!! ");
            jogoConcluido = true;
            return true;
        }

        trocaJogadores();
        return false;

    }

    @Override
    public String toString(){ return tabuleiro.toString(); }

    public void voltarAtras(int qtd, Jogador jRemoverCreditos) {

        if(!jogadorAtual.getNome().equals(jRemoverCreditos.getNome()))
        {
            proximoJogar.setCreditos(jRemoverCreditos.getCreditos()-qtd);
            proximoJogar.setnJogada(0);
            proximoJogar.setPecaEspecial(false);
        }
        else
        {
            jogadorAtual.setCreditos(jRemoverCreditos.getCreditos()-qtd);
            jogadorAtual.setnJogada(0);
            jogadorAtual.setPecaEspecial(false);

        }
        miniJogoAtual = null;
        miniJogoaDecorrer = false;
    }
}
