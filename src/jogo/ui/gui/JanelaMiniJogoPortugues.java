package jogo.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jogo.logica.JogoCareTakerObservable;

import static jogo.logica.JogoCareTakerObservable.*;

public class JanelaMiniJogoPortugues extends BorderPane
{
    JogoCareTakerObservable jogo;

    Button btnInserirResposta;
    TextField tfResposta;
    Label lblInput;
    Label lblDescricao;

    public JanelaMiniJogoPortugues(JogoCareTakerObservable jogo, int width, int height)
    {
        this.jogo = jogo;
        setWidth(width);
        setHeight(height);

        criaObjetosGraficos();
        dispoeVista();
        registaHandlers();

    }

    private void criaObjetosGraficos() {
        btnInserirResposta = new Button("Responder");
    }

    private void dispoeVista()
    {
        VBox vBox = new VBox();
        String family = "Helvetica";
        double sizeTitulo = 20;
        double sizeDescricao = 16;
        double sizeInput = 14;

        Label lblTitulo = new Label("MINI JOGO PORTUGUÊS");
        lblTitulo.setFont(Font.font(family, FontWeight.BOLD, sizeTitulo));
        lblTitulo.setAlignment(Pos.CENTER);

        lblDescricao = new Label();
        lblDescricao.setFont(Font.font(family, FontWeight.NORMAL, sizeDescricao));
        lblDescricao.setWrapText(true);
        lblDescricao.setAlignment(Pos.CENTER);

        lblInput = new Label();
        lblInput.setAlignment(Pos.CENTER);
        lblInput.setFont(Font.font(family, FontWeight.SEMI_BOLD, sizeInput));
        tfResposta = new TextField();

        vBox.setMaxWidth(350);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(lblTitulo, lblDescricao, lblInput, tfResposta, btnInserirResposta);

        setCenter(vBox);
    }

    private void registaHandlers()
    {
        jogo.addPropertyChangeListener(ESTADO_JOGAR_MINIJOGO_MUDOU, (evt-> update()));

        tfResposta.setOnKeyPressed(ev-> {
            if( ev.getCode() == KeyCode.ENTER ) {
                if(tfResposta.getText() != "" && tfResposta.getText() != null)
                    jogo.inserirRespostaMiniJogo(tfResposta.getText());
            }
        });

        btnInserirResposta.setOnAction(ev-> {
            if(tfResposta.getText() != "")
                jogo.inserirRespostaMiniJogo(tfResposta.getText());
        });
    }

    private void update()
    {
        lblInput.setText(jogo.getMiniJogoAtual().toString());
        lblDescricao.setText("Escreva as palavras em menos de " + jogo.getMiniJogoAtual().getTempo() + " para ganhar a peça especial");
        tfResposta.setText(null);
    }
}
