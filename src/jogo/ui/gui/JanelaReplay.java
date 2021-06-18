package jogo.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jogo.logica.JogoCareTakerObservable;
import jogo.logica.Situacao;
import jogo.logica.dados.Peca;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static jogo.logica.JogoCareTakerObservable.*;

public class JanelaReplay extends BorderPane {

    JogoCareTakerObservable jogo;

    GridPane gpTabuleiro;
    Label lblLOG;

    public JanelaReplay(JogoCareTakerObservable jogo, int width, int height)
    {
        this.jogo = jogo;
        setWidth(width);
        setHeight(height);

        gpTabuleiro = new GridPane();
        dispoeVista();
        registaHandlers();
        relplayAutomatico();
    }

    private void relplayAutomatico() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if(jogo.getEstado() == Situacao.AGUARDAR_FIM_REPLAY)
                {
                    Platform.runLater(() -> {
                        try {
                            jogo.mostrarReplay();
                        } catch (IOException | InterruptedException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }, 0,1000);
    }

    private void dispoeVista()
    {
        desenhaTopo();
        desenhaCentro();
    }

    private void registaHandlers()
    {
        jogo.addPropertyChangeListener(ESTADO_MOSTRAR_REPLAY_MUDOU, ev-> {
            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void desenhaTopo()
    {
        VBox vboxTopo = new VBox();

        Label lbTitulo = new Label("Replay Jogo: " + jogo.getNomeJogo());

        lblLOG = new Label();

        vboxTopo.getChildren().addAll(lbTitulo, lblLOG);

        setTop(vboxTopo);
    }


    private void desenhaCentro()
    {
        VBox vBox = new VBox();

        HBox hBoxGP = new HBox();

        gpTabuleiro.setHgap(jogo.getLarguraTabuleiro());
        gpTabuleiro.setVgap(jogo.getAlturaTabuleiro());
        gpTabuleiro.setMaxSize(350,400);
        gpTabuleiro.setPadding(new Insets(5));
        gpTabuleiro.setAlignment(Pos.CENTER);
        gpTabuleiro.setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(10), null)));

        desenhaTabuleiro();

        hBoxGP.setAlignment(Pos.TOP_CENTER);
        hBoxGP.getChildren().add(gpTabuleiro);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(hBoxGP);
        setCenter(vBox);

    }

    private void desenhaTabuleiro() {
        gpTabuleiro.getChildren().clear();
        for (int i = 0; i < jogo.getAlturaTabuleiro() ;i++)
        {
            for(int j= 0; j< jogo.getLarguraTabuleiro(); j++)
            {
                Peca peca = jogo.getTabuleiro().getPeca(i,j);
                if(peca != null)
                {
                    Circle cr = new Circle();
                    cr.setRadius(20);
                    cr.setFill(new Color(peca.getIdentificador().getR(), peca.getIdentificador().getG(),peca.getIdentificador().getB(), 1));
                    gpTabuleiro.add(cr,j,i);
                }
                else
                {
                    Circle pane = new Circle(0,0,20);
                    pane.setFill(Color.WHITE);
                    gpTabuleiro.add(pane,j,i);
                }
            }
        }
    }

    private void update() throws IOException {
        switch (jogo.getEstado())
        {
            case AGUARDAR_FIM_REPLAY:

                lblLOG.setText(jogo.getMsgLog().get(jogo.getMsgLog().size()-1));
                desenhaTabuleiro();

                break;
            case AGUARDAR_TERMINAR:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("REPLAY TERMINOU");
                alert.setHeaderText("Fim de Replay do " + jogo.getNomeJogo());
                alert.showAndWait();

                jogo.iniciar();
                break;
        }

    }

}
