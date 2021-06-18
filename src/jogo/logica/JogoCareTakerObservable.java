package jogo.logica;

import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.MiniJogo.MiniJogo;
import jogo.logica.dados.Tabuleiro;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;

public class JogoCareTakerObservable implements Serializable
{
    public static final long serialVersionID = 1;

    private JogoCareTaker jogo;
    private final PropertyChangeSupport propertyChangeSupport;

    public final static String ESTADO_JOGO_MUDOU = "estado jogo mudou";
    public final static String ESTADO_USAR_PE_MUDOU = "estado usar peca especial mudou";
    public final static String ESTADO_MOSTRAR_REPLAY_MUDOU = "estado mostrar replay mudou";
    public final static String ESTADO_JOGAR_MINIJOGO_MUDOU = "estado jogar mini jogo mudou";

    public JogoCareTakerObservable() throws IOException
    {
        jogo = new JogoCareTaker();
        propertyChangeSupport = new PropertyChangeSupport(jogo);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    //------------------------------ Methods that enable accessing the data/status of the game ----------------------------

    public Jogador getAtualmenteAJogar() { return jogo.getAtualmenteAJogar(); }

    public Jogador getProximoAJogar() { return jogo.getProximoAJogar(); }

    public List<String> getMsgLog()
    {
        return jogo.getMsgLog();
    }

    public MiniJogo getMiniJogoAtual() { return jogo.getMiniJogoAtual(); }

    public Tabuleiro getTabuleiro() { return jogo.getTabuleiro(); }

    public int getLarguraTabuleiro() { return jogo.getLarguraTabuleiro(); }

    public int getAlturaTabuleiro() { return jogo.getAlturaTabuleiro(); }

    public boolean getMiniJogoADecorrer(){ return jogo.getMiniJogoADecorrer(); }

    public String getNomeJogo() { return jogo.getNomeJogo(); }

    public boolean jogoTerminou() { return jogo.jogoTerminou();}

    @Override
    public String toString() { return jogo.toString(); }

    //--------------------- Methods that trigger events/actions in the finite state machine  -----------------------

    public void iniciar()
    {
        jogo = new JogoCareTaker();
        jogo.iniciar();
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public void definirModoJogo()
    {
        jogo.definirModoJogo();
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public void inserirJogadores(List<Jogador> jogadores)
    {
        jogo.inserirJogadores(jogadores);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public void iniciarMiniJogo()
    {
        jogo.iniciarMiniJogo();
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGAR_MINIJOGO_MUDOU,null,null);
    }

    public void inserirRespostaMiniJogo(String resposta)
    {
        jogo.inserirRespostaMiniJogo(resposta);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGAR_MINIJOGO_MUDOU,null,null);
    }

    public void terminarJogo()
    {
        jogo.terminarJogo();
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public void jogar(int nColuna)
    {
        jogo.jogar(nColuna);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public void passaJogada(boolean res) {
        jogo.passaJogada(res);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public void usarPecaEspecial(int ncoluna)
    {
        jogo.usarPecaEspecial(ncoluna);
        propertyChangeSupport.firePropertyChange(ESTADO_USAR_PE_MUDOU,null,null);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);

    }

    public boolean verificaPossivelVoltarAtras(int nCreditos) { return jogo.verificaPossivelVoltarAtras(nCreditos); }

    public boolean verificaInicioMiniJogo() { return jogo.verificaInicioMiniJogo(); }

    public void undo(int qtd)
    {
        jogo.undo(qtd);
        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public Situacao getEstado() {
        return jogo.getEstado();
    }

    public void mostrarReplay() throws IOException, ClassNotFoundException, InterruptedException {
        jogo.mostrarReplay();
        propertyChangeSupport.firePropertyChange(ESTADO_MOSTRAR_REPLAY_MUDOU, null, null);
    }

    public void gravarJogo(String filePath) throws IOException {
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(filePath + ".bin"));
        oout.writeObject(jogo);
        oout.flush();
        oout.close();
    }

    public void carregarJogo(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(filePath));
        jogo = (JogoCareTaker) oin.readObject();
        oin.close();

        propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
    }

    public boolean carregarJogoReplay(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(filePath));
        JogoCareTaker jogoAux = (JogoCareTaker) oin.readObject();

        oin.close();
        if(jogoAux.isJogoConcluido()) {
            jogo = jogoAux;
            jogo.mostrarReplay();
            propertyChangeSupport.firePropertyChange(ESTADO_JOGO_MUDOU,null,null);
            return true;
        }

        jogo.terminarJogo();
        return false;

    }

}
