package jogo.ui.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import jogo.logica.JogoCareTakerObservable;

import java.util.Optional;

import static jogo.logica.JogoCareTakerObservable.ESTADO_JOGO_MUDOU;

public class JanelaPrincipal extends StackPane
{
    JogoCareTakerObservable jogo;
    JanelaInicial janelaInicial;
    JanelaDefinirJogadores janelaDefinirJogadores;
    JanelaJogada janelaJogada;
    JanelaReplay janelaReplay;
    JanelaMiniJogoMatematica janelaMiniJogoMatematica;
    JanelaMiniJogoPortugues janelaMiniJogoPortugues;

    public JanelaPrincipal(JogoCareTakerObservable jogo, int width, int height)
    {
        this.jogo = jogo;
        setWidth(width);
        setHeight(height);

        criaComponentes(width, height);
        dispoeVista();
        registaHandlers();

        update();
    }

    private void criaComponentes(int width, int height) {
        janelaInicial = new JanelaInicial(jogo, width, height);
        janelaDefinirJogadores = new JanelaDefinirJogadores(jogo, width, height);
        janelaJogada = new JanelaJogada(jogo, width, height);
        janelaReplay = new JanelaReplay(jogo, width, height);
        janelaMiniJogoMatematica = new JanelaMiniJogoMatematica(jogo, width, height);
        janelaMiniJogoPortugues = new JanelaMiniJogoPortugues(jogo, width, height);
    }

    private void dispoeVista()
    {
        getChildren().add(janelaInicial);
        getChildren().add(janelaDefinirJogadores);
        getChildren().add(janelaJogada);
        getChildren().add(janelaReplay);
        getChildren().add(janelaMiniJogoMatematica);
        getChildren().add(janelaMiniJogoPortugues);
    }

    private void registaHandlers()
    {
        jogo.addPropertyChangeListener(ESTADO_JOGO_MUDOU, (evt-> update()));
    }

    private void update()
    {
        switch (jogo.getEstado())
        {
            case AGUARDA_INICIO:
                janelaInicial.setVisible(true);
                janelaDefinirJogadores.setVisible(false);
                janelaJogada.setVisible(false);
                janelaReplay.setVisible(false);
                janelaMiniJogoMatematica.setVisible(false);
                janelaMiniJogoPortugues.setVisible(false);
                break;
            case AGUARDAR_DEFINIR_MODO_JOGO:
                janelaInicial.setVisible(false);
                janelaDefinirJogadores.setVisible(true);
                janelaJogada.setVisible(false);
                janelaReplay.setVisible(false);
                janelaMiniJogoMatematica.setVisible(false);
                janelaMiniJogoPortugues.setVisible(false);
                break;
            case AGUARDAR_JOGADA:
                janelaInicial.setVisible(false);
                janelaDefinirJogadores.setVisible(false);
                janelaJogada.setVisible(true);
                janelaReplay.setVisible(false);
                janelaMiniJogoMatematica.setVisible(false);
                janelaMiniJogoPortugues.setVisible(false);
                trataInicioMiniJogo();
                break;
            case AGUARDAR_FIM_REPLAY:
                janelaInicial.setVisible(false);
                janelaDefinirJogadores.setVisible(false);
                janelaJogada.setVisible(false);
                janelaReplay.setVisible(true);
                janelaMiniJogoMatematica.setVisible(false);
                janelaMiniJogoPortugues.setVisible(false);
                break;
            case AGUARDAR_FIM_MINI_JOGO_MAT:
                janelaInicial.setVisible(false);
                janelaDefinirJogadores.setVisible(false);
                janelaJogada.setVisible(false);
                janelaReplay.setVisible(false);
                janelaMiniJogoMatematica.setVisible(true);
                janelaMiniJogoPortugues.setVisible(false);
                break;
            case AGUARDAR_FIM_MINI_JOGO_PT:
                janelaInicial.setVisible(false);
                janelaDefinirJogadores.setVisible(false);
                janelaJogada.setVisible(false);
                janelaReplay.setVisible(false);
                janelaMiniJogoMatematica.setVisible(false);
                janelaMiniJogoPortugues.setVisible(true);
                break;
            case AGUARDAR_TERMINAR:
                jogo.iniciar();
                janelaInicial.setVisible(true);
                janelaDefinirJogadores.setVisible(false);
                janelaJogada.setVisible(false);
                janelaReplay.setVisible(false);
                break;
        }
    }

    private void trataInicioMiniJogo()
    {
        if(jogo.verificaInicioMiniJogo()) {
            this.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("MINI JOGO");
            alert.setHeaderText("Jogador " + jogo.getAtualmenteAJogar().getNome() + " deseja jogar Mini Jogo?");

            ButtonType buttonTypeOne = new ButtonType("Sim");
            alert.initModality(Modality.NONE);
            ButtonType buttonTypeCancel = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Button b = new Button("close alert");
            b.setOnAction(event ->
            {
                for (ButtonType bt : alert.getDialogPane().getButtonTypes()) {
                    if (bt.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                        Button cancelButton = (Button) alert.getDialogPane().lookupButton(bt);
                        cancelButton.fire();
                        break;
                    }
                }
            });

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                jogo.iniciarMiniJogo();
                this.setDisable(false);
            } else if (result.get() == buttonTypeCancel) {
                this.setDisable(false);
            }

        }
    }

}
