package jogo.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import jogo.logica.JogoCareTakerObservable;
import jogo.logica.Situacao;
import jogo.logica.dados.Jogador.Humano;
import jogo.logica.dados.Jogador.Virtual;
import jogo.logica.dados.Peca;
import jogo.ui.gui.resources.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static jogo.logica.JogoCareTakerObservable.*;

public class JanelaJogada extends BorderPane {

    JogoCareTakerObservable jogo;

    Button btnGravar;
    Button btnTerminarJogo;

    RadioButton rbSelecionar = new RadioButton("Usar Peça Especial");
    RadioButton rBVoltarAtras = new RadioButton("Voltar Atrás");
    Button btnVoltarAtras = new Button();
    Slider slVoltarAtras = new Slider();

    Label lblJogAtual;
    Label lblCreditos;
    Circle crID;

    EscolhaCorPecas escolhaColuna;
    HBox hboxPETopo;
    HBox hBoxEscolha;
    HBox hBoxPEinferior;
    HBox hboxVoltarAtras;
    GridPane gpTabuleiro = new GridPane();

    public JanelaJogada(JogoCareTakerObservable jogo, int width, int height)
    {
        this.jogo = jogo;
        setWidth(width);
        setHeight(height);

        escolhaColuna = new EscolhaCorPecas(jogo, true);
        criaObjetosGraficos();
        dispoeVista();
        registaHandlers();

        jogadaAutomaticaVirtual();
    }


    private void criaObjetosGraficos()
    {
        btnGravar = new Button("Gravar Jogo");
        btnTerminarJogo = new Button("Terminar Jogo");
    }

    private void dispoeVista()
    {
        desenhaTopo();
        desenhaCentro();
        desenhaInferior();
    }

    private void registaHandlers()
    {
        jogo.addPropertyChangeListener(ESTADO_JOGO_MUDOU, ev-> {
            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        jogo.addPropertyChangeListener(ESTADO_USAR_PE_MUDOU, ev-> updatePE());

        btnGravar.setOnAction(ev-> {
            FileChooser fChooser = new FileChooser();
            File f = fChooser.showSaveDialog(null);

            if(f==null)
                return;

            try{
                jogo.gravarJogo(f.getCanonicalPath());
            }catch (IOException e){
                System.err.println("Save: " + e);
            }
        });

        btnTerminarJogo.setOnAction(ev-> {
            jogo.terminarJogo();
        });

    }

    private void desenhaTopo()
    {
        // caixas horizontais topo
        HBox hbox = new HBox();

        VBox vboxJog = new VBox();

        HBox hBoxIDJog = new HBox();
        lblJogAtual = new Label("Jogador Atual: " + jogo.getAtualmenteAJogar());

        crID = new Circle();
        crID.setRadius(4);
        hBoxIDJog.setSpacing(2);
        hBoxIDJog.setAlignment(Pos.TOP_LEFT);
        hBoxIDJog.setMinWidth(200);
        hBoxIDJog.getChildren().addAll(lblJogAtual, crID);

        lblCreditos = new Label();

        hboxVoltarAtras = new HBox();

        rBVoltarAtras = new RadioButton("Voltar Atrás");
        btnVoltarAtras = new Button();
        slVoltarAtras = new Slider();

        rBVoltarAtras.setOnAction(ev-> {
            slVoltarAtras.setVisible(!slVoltarAtras.isVisible());
            btnVoltarAtras.setVisible(!btnVoltarAtras.isVisible());
        });

        slVoltarAtras.setMin(0);
        slVoltarAtras.setMax(5);
        slVoltarAtras.setShowTickLabels(true);
        slVoltarAtras.setShowTickMarks(true);
        slVoltarAtras.setMaxWidth(100);
        slVoltarAtras.setMajorTickUnit(1);
        slVoltarAtras.setMinorTickCount(0);
        slVoltarAtras.setBlockIncrement(1);
        slVoltarAtras.setSnapToTicks(true);
        slVoltarAtras.setVisible(false);

        btnVoltarAtras.setOnAction(ev->{
            if((int)slVoltarAtras.getValue() > 0)
            {
                if(jogo.verificaPossivelVoltarAtras((int)slVoltarAtras.getValue()))
                {
                    jogo.undo((int)slVoltarAtras.getValue());
                    if(slVoltarAtras.getMax() == 0) {
                        slVoltarAtras.setVisible(false);
                        btnVoltarAtras.setVisible(false);
                    }
                    else {
                        if (jogo.getAtualmenteAJogar() instanceof Virtual)
                            slVoltarAtras.setMax(jogo.getProximoAJogar().getCreditos());
                        else
                            slVoltarAtras.setMax(jogo.getAtualmenteAJogar().getCreditos());
                    }
                    desenhaTabuleiro();
                }
            }
        });

        setButtonIcon(btnVoltarAtras, "voltar.png");
        btnVoltarAtras.setVisible(false);

        hboxVoltarAtras.setSpacing(5);
        hboxVoltarAtras.getChildren().addAll(rBVoltarAtras,slVoltarAtras, btnVoltarAtras);

        vboxJog.setAlignment(Pos.TOP_LEFT);
        vboxJog.setSpacing(5);
        vboxJog.setPadding(new Insets(20, 0, 0, 30));
        vboxJog.getChildren().addAll(hBoxIDJog, lblCreditos, hboxVoltarAtras);

        //vboxJog.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        hboxPETopo = new HBox();

        Label lblPE = new Label("Peça Especial");
        Circle cPE = new Circle();
        cPE.setFill(Color.RED);
        cPE.setRadius(8);

        hboxPETopo.setAlignment(Pos.TOP_RIGHT);
        hboxPETopo.setSpacing(5);
        hboxPETopo.setPadding(new Insets(20,0,0,140));
        hboxPETopo.getChildren().addAll(lblPE, cPE);

        hbox.getChildren().addAll(vboxJog,hboxPETopo);
        setTop(hbox);
    }

    private void desenhaCentro()
    {
        VBox vBox = new VBox();

        HBox hBoxGP = new HBox();
        hBoxEscolha = new HBox();

        gpTabuleiro.setHgap(jogo.getLarguraTabuleiro());
        gpTabuleiro.setVgap(jogo.getAlturaTabuleiro());
        gpTabuleiro.setMaxSize(350,400);
        gpTabuleiro.setPadding(new Insets(5));
        gpTabuleiro.setAlignment(Pos.CENTER);
        gpTabuleiro.setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(10), null)));

        desenhaTabuleiro();

        hBoxGP.setAlignment(Pos.TOP_CENTER);
        hBoxGP.getChildren().add(gpTabuleiro);

        hBoxEscolha.setAlignment(Pos.TOP_CENTER);
        hBoxEscolha.setPadding(new Insets(0,0,10,0));

        hBoxEscolha.getChildren().add(escolhaColuna);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(hBoxEscolha);
        vBox.getChildren().add(hBoxGP);
        setCenter(vBox);

    }

    private void desenhaInferior()
    {
        HBox hBox = new HBox();

        hBoxPEinferior = new HBox();

        rbSelecionar.setOnAction(ev -> {
            if(rbSelecionar.isSelected())
                escolhaColuna.setCor(Color.RED);
            else
                escolhaColuna.setCor(Color.DARKGRAY);
        });

        hBoxPEinferior.setSpacing(5);
        hBoxPEinferior.setPadding(new Insets(0, 0,30,30));
        hBoxPEinferior.getChildren().addAll(rbSelecionar);
        hBoxPEinferior.setVisible(false);

        HBox hboxGravarJogo = new HBox();

        setButtonIcon(btnGravar, "gravar.png");

        hboxGravarJogo.setAlignment(Pos.CENTER);
        hboxGravarJogo.setPadding(new Insets(0, 50,30,50));
        hboxGravarJogo.getChildren().add(btnGravar);

        HBox hboxTerminarJogo = new HBox();

        setButtonIcon(btnTerminarJogo, "sair.png");

        hboxTerminarJogo.setAlignment(Pos.CENTER_RIGHT);
        hboxTerminarJogo.setPadding(new Insets(0, 0,30,0));
        hboxTerminarJogo.getChildren().add(btnTerminarJogo);


        hBox.getChildren().addAll(hBoxPEinferior, hboxGravarJogo, hboxTerminarJogo);
        setBottom(hBox);
    }

    private void jogadaAutomaticaVirtual() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if(jogo.getAtualmenteAJogar() instanceof Virtual && jogo.getEstado() == Situacao.AGUARDAR_JOGADA)
                {
                    Platform.runLater(() -> jogo.jogar(0));
                }
            }
        }, 0,1000);
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

        Circle cr = (Circle) hboxPETopo.getChildren().get(1);
        switch (jogo.getEstado())
        {
            case AGUARDAR_JOGADA:
                lblJogAtual.setText("Jogador Atual: " + jogo.getAtualmenteAJogar().getNome());
                crID.setFill(Color.color(jogo.getAtualmenteAJogar().getIdentificador().getR(), jogo.getAtualmenteAJogar().getIdentificador().getG(), jogo.getAtualmenteAJogar().getIdentificador().getB()));

                desenhaTabuleiro();

                if(jogo.getAtualmenteAJogar() instanceof Humano)
                {
                    lblCreditos.setVisible(true);
                    hboxPETopo.setVisible(true);
                    lblCreditos.setText("Créditos: " + jogo.getAtualmenteAJogar().getCreditos());

                    btnGravar.setVisible(true);
                    btnTerminarJogo.setVisible(true);
                    hBoxEscolha.setVisible(true);
                    hboxVoltarAtras.setVisible(true);

                    if(jogo.getMiniJogoADecorrer())
                        mostraResultadoMiniJogo();

                    if(jogo.getAtualmenteAJogar().getPecaEspecial())
                    {
                        if(cr != null)
                        {
                            cr.setFill(Color.GREEN);
                            hBoxPEinferior.setVisible(true);
                        }
                    }
                    else
                    {
                        if(cr != null)
                        {
                            cr.setFill(Color.RED);
                            hBoxPEinferior.setVisible(false);
                        }

                    }

                }
                else
                {
                    hboxPETopo.setVisible(false);
                    lblCreditos.setVisible(false);
                    lblCreditos.setText(null);
                    btnGravar.setVisible(false);
                    btnTerminarJogo.setVisible(false);
                    hBoxEscolha.setVisible(false);
                    hboxVoltarAtras.setVisible(false);
                }
                break;
            case AGUARDAR_TERMINAR:
                if(jogo.jogoTerminou()) {
                    desenhaTabuleiro();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("JOGO TERMINOU");
                    alert.setHeaderText("JOGO TERMINOU");
                    alert.setContentText("O vencedor é " + jogo.getAtualmenteAJogar().getNome());

                    alert.showAndWait();

                    jogo.gravarJogo(jogo.getNomeJogo());
                }
                else
                {
                    if(rBVoltarAtras.isSelected())
                        rBVoltarAtras.fire();
                    slVoltarAtras.setMax(5);
                }
                break;
        }

    }

    private void updatePE() {

        if(jogo.getAtualmenteAJogar().getPecaEspecial())
            hBoxPEinferior.setVisible(true);
        else
            hBoxPEinferior.setVisible(false);

        desenhaTabuleiro();
        rbSelecionar.setSelected(false);
        escolhaColuna.reset();
    }

    private void mostraResultadoMiniJogo() {
        if (jogo.getMiniJogoAtual().getResultado()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Ganhou Peça Especial!");
            alert.showAndWait();
            jogo.passaJogada(false);
        } else if (!jogo.getMiniJogoAtual().getResultado()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText("Não ganhou Peça Especial, perdeu a vez de jogar!");
            alert.showAndWait();
            jogo.passaJogada(true);
        }
    }

    private void setButtonIcon(Button b, String imagePath) {
        Image image = ImageLoader.getImage(imagePath);

        if(image==null)
            return;

        ImageView iv = new ImageView(image);
        iv.setFitHeight(15);
        iv.setPreserveRatio(true);
        b.setGraphic(iv);
    }
}
