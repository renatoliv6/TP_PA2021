package jogo.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jogo.logica.JogoCareTakerObservable;
import jogo.logica.Situacao;
import jogo.logica.dados.Identificador;
import jogo.logica.dados.Jogador.Humano;
import jogo.logica.dados.Jogador.Jogador;
import jogo.logica.dados.Jogador.Virtual;
import jogo.ui.gui.resources.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jogo.logica.JogoCareTakerObservable.*;

public class JanelaDefinirJogadores extends BorderPane {

    JogoCareTakerObservable jogo;
    List<Jogador> jogadores;
    EscolhaCorPecas pecas_ID_Jog1vsCPU;
    EscolhaCorPecas pecas_ID_Jog1;
    EscolhaCorPecas pecas_ID_Jog2;

    Button btnCPUvsCPU;
    Button btnCPUvsJOG;
    Button btnJOGvsJOG;
    Button btnInsereJogador;
    Button btnInsereJogadores;

    Button btnvoltar;

    HBox hboxCenterEscolhaJogo = new HBox();
    VBox vBoxInsereDadosJog1 = new VBox();
    VBox vBoxInsereDadosJog2= new VBox();
    TextField tfNomeJogVsCPU = new TextField();
    TextField tfNomeJog1 = new TextField();
    TextField tfNomeJog2 = new TextField();

    public JanelaDefinirJogadores(JogoCareTakerObservable jogo, int width, int height)
    {
        this.jogo = jogo;
        jogadores = new ArrayList<>();

        setWidth(width);
        setHeight(height);

        adicionarPecas();
        criaObjetosGraficos();
        dispoeVista();
        registaHandlers();

    }

    private void criaObjetosGraficos()
    {
        btnCPUvsCPU = new Button("CPUvsCPU");
        btnCPUvsJOG = new Button("CPUvsJOG");
        btnJOGvsJOG = new Button("JOGvsJOG");
        btnInsereJogador = new Button("Inserir Jogador");
        btnInsereJogadores = new Button("Inserir Jogadores");
        btnvoltar = new Button();

        adicionarPecas();

    }

    private void dispoeVista()
    {
        desenhaTopo();
        desenhaCentro();
        desenhaCentroDadosJogador1();
        desenhaCentroDadosJogador2();
    }

    private void registaHandlers()
    {
        jogo.addPropertyChangeListener(ESTADO_JOGO_MUDOU, (evt-> update()));

        btnvoltar.setOnAction(ev-> {
            if(vBoxInsereDadosJog1.isVisible() || vBoxInsereDadosJog2.isVisible())
            {
                setCenter(hboxCenterEscolhaJogo);
                vBoxInsereDadosJog1.setVisible(false);
                vBoxInsereDadosJog2.setVisible(false);
            }
            else if(hboxCenterEscolhaJogo.isVisible())
                jogo.iniciar();
        });

        btnCPUvsCPU.setOnAction(ev-> {
            inserirCPUvsCPU();
            jogo.inserirJogadores(jogadores);
        });

        btnCPUvsJOG.setOnAction(ev-> {
            Collections.shuffle(pecas_ID_Jog1.getPecas());
            vBoxInsereDadosJog1.setVisible(true);
            setCenter(vBoxInsereDadosJog1);
        });

        btnJOGvsJOG.setOnAction(ev-> {
            vBoxInsereDadosJog2.setVisible(true);
            setCenter(vBoxInsereDadosJog2);
        });

        btnInsereJogador.setOnAction(ev -> {
            if(inserirJOGvsCPU()) {
                jogo.inserirJogadores(jogadores);
            }

        });

        btnInsereJogadores.setOnAction(ev -> {
            if(inserirJOGvsJOG()) {
                jogo.inserirJogadores(jogadores);
            }
        });

    }

    private void desenhaCentroDadosJogador1() {

        HBox hboxTitulo = new HBox();
        Label lblTitulo = new Label("Jogador");
        String family = "Helvetica";
        double size = 15;
        lblTitulo.setFont(Font.font(family, FontWeight.BOLD, size));

        hboxTitulo.setAlignment(Pos.TOP_CENTER);
        hboxTitulo.setPadding(new Insets(5,0,10,0));
        hboxTitulo.getChildren().add(lblTitulo);

        HBox hboxNome = new HBox();
        Label label1 = new Label("Nome:");
        label1.setPadding(new Insets(3,0,0,0));

        hboxNome.getChildren().addAll(label1, tfNomeJogVsCPU);
        hboxNome.setSpacing(10);
        hboxNome.setAlignment(Pos.TOP_CENTER);

        HBox hboxIdentificador = new HBox();

        Label label2 = new Label("Escolha a peça pretendida:");
        label2.setPadding(new Insets(3,0,0,0));

        hboxIdentificador.getChildren().addAll(label2, pecas_ID_Jog1vsCPU);
        hboxIdentificador.setAlignment(Pos.CENTER);
        hboxIdentificador.setPadding(new Insets(30));
        hboxIdentificador.setSpacing(10);

        HBox hboxBotao = new HBox();
        hboxBotao.setAlignment(Pos.CENTER);
        btnInsereJogador.setAlignment(Pos.CENTER);
        hboxBotao.getChildren().add(btnInsereJogador);

        vBoxInsereDadosJog1.getChildren().addAll(hboxTitulo, hboxNome, hboxIdentificador, hboxBotao);
        vBoxInsereDadosJog1.setVisible(false);

    }

    private void desenhaCentroDadosJogador2()
    {
        double size = 15;
        String family = "Helvetica";
        Font font_lbl = Font.font(family, FontWeight.BOLD, size);

        HBox hboxTituloJog1 = new HBox();
        Label lblTituloJog1 = new Label("Jogador 1");

        lblTituloJog1.setFont(font_lbl);
        hboxTituloJog1.setAlignment(Pos.TOP_CENTER);
        hboxTituloJog1.setPadding(new Insets(5,0,10,0));
        hboxTituloJog1.getChildren().add(lblTituloJog1);

        HBox hboxNomeJog1 = new HBox();
        Label lblNomeJog1 = new Label("Nome:");
        lblNomeJog1.setPadding(new Insets(3,0,0,0));

        tfNomeJog1 = new TextField ();

        hboxNomeJog1.getChildren().addAll(lblNomeJog1, tfNomeJog1);
        hboxNomeJog1.setSpacing(10);
        hboxNomeJog1.setAlignment(Pos.TOP_CENTER);

        HBox hboxIdJog1 = new HBox();

        Label lblEscPecaJog1 = new Label("Escolha a peça pretendida:");
        lblEscPecaJog1.setPadding(new Insets(3,0,0,0));

        hboxIdJog1.getChildren().add(lblEscPecaJog1);
        hboxIdJog1.getChildren().addAll(pecas_ID_Jog1);

        hboxIdJog1.setAlignment(Pos.CENTER);
        hboxIdJog1.setPadding(new Insets(30));
        hboxIdJog1.setSpacing(10);


        HBox hboxTituloJog2 = new HBox();
        Label lblTituloJog2 = new Label("Jogador 2");
        lblTituloJog2.setFont(font_lbl);

        hboxTituloJog2.setAlignment(Pos.TOP_CENTER);
        hboxTituloJog2.setPadding(new Insets(5,0,10,0));
        hboxTituloJog2.getChildren().add(lblTituloJog2);

        HBox hboxNomeJog2 = new HBox();
        Label lblNomeJog2 = new Label("Nome:");
        lblNomeJog1.setPadding(new Insets(3,0,0,0));

        tfNomeJog2 = new TextField ();

        hboxNomeJog2.getChildren().addAll(lblNomeJog2, tfNomeJog2);
        hboxNomeJog2.setSpacing(10);
        hboxNomeJog2.setAlignment(Pos.TOP_CENTER);

        HBox hboxIdJog2 = new HBox();

        Label lblEscPecaJog2 = new Label("Escolha a peça pretendida:");
        lblEscPecaJog2.setPadding(new Insets(3,0,0,0));

        hboxIdJog2.getChildren().add(lblEscPecaJog2);
        hboxIdJog2.getChildren().addAll(pecas_ID_Jog2);

        hboxIdJog2.setAlignment(Pos.CENTER);
        hboxIdJog2.setPadding(new Insets(30));
        hboxIdJog2.setSpacing(10);

        HBox hboxBotao = new HBox();
        hboxBotao.setAlignment(Pos.CENTER);
        btnInsereJogadores.setAlignment(Pos.CENTER);
        hboxBotao.getChildren().add(btnInsereJogadores);

        vBoxInsereDadosJog2.getChildren().addAll(hboxTituloJog1, hboxNomeJog1, hboxIdJog1, hboxTituloJog2, hboxNomeJog2, hboxIdJog2, hboxBotao);
        vBoxInsereDadosJog2.setVisible(false);
    }

    private boolean inserirJOGvsCPU()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(tfNomeJogVsCPU.getText() == null || tfNomeJogVsCPU.getText() == "")
        {
            alert.setTitle("Aviso");
            alert.setHeaderText("Inserir nome!");

            alert.showAndWait();
            return false;
        }

        if(pecas_ID_Jog1vsCPU.getPecaSelecionada() == null)
        {
            alert.setTitle("Aviso");
            alert.setHeaderText("Selecionar uma Peça!");

            alert.showAndWait();
            return false;
        }

        do {
            Collections.shuffle(pecas_ID_Jog1.getPecas());
        }while (pecas_ID_Jog1.getPecas().get(0).getFill() == pecas_ID_Jog1vsCPU.getPecaSelecionada().getFill());

        Color cor1 = (Color) pecas_ID_Jog1vsCPU.getPecaSelecionada().getFill();
        jogadores.add(new Humano(tfNomeJogVsCPU.getText(), new Identificador(cor1.getRed(),cor1.getGreen(),cor1.getBlue())));

        Color cor2 = (Color) pecas_ID_Jog1.getPecas().get(0).getFill();
        jogadores.add(new Virtual("Virtual1", new Identificador(cor2.getRed(),cor2.getGreen(),cor2.getBlue())));

        return true;
    }

    private boolean inserirJOGvsJOG()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(tfNomeJog1.getText() == null || tfNomeJog1.getText() == "" || tfNomeJog2.getText() == null || tfNomeJog2.getText() == "")
        {
            alert.setTitle("Aviso");
            alert.setHeaderText("Inserir nome em falta!");

            alert.showAndWait();
            return false;
        }

        if(pecas_ID_Jog1.getPecaSelecionada() == null || pecas_ID_Jog2.getPecaSelecionada() == null)
        {
            alert.setTitle("Aviso");
            alert.setHeaderText("Selecionar uma Peça para cada jogador!");

            alert.showAndWait();
            return false;
        }

        if(pecas_ID_Jog1.getPecaSelecionada().getFill() == pecas_ID_Jog2.getPecaSelecionada().getFill())
        {
            alert.setTitle("Aviso");
            alert.setHeaderText("Cores iguais. Escolher novas cores!");
            alert.showAndWait();
            return false;
        }

        Color cor1 = (Color) pecas_ID_Jog1.getPecaSelecionada().getFill();
        jogadores.add(new Humano(tfNomeJog1.getText(), new Identificador(cor1.getRed(),cor1.getGreen(),cor1.getBlue())));

        Color cor2 = (Color) pecas_ID_Jog2.getPecaSelecionada().getFill();
        jogadores.add(new Humano(tfNomeJog2.getText(), new Identificador(cor2.getRed(),cor2.getGreen(),cor2.getBlue())));


        return true;
    }

    private void inserirCPUvsCPU()
    {
        do {
            Collections.shuffle(pecas_ID_Jog1.getPecas());
            Collections.shuffle(pecas_ID_Jog2.getPecas());
        }while (pecas_ID_Jog1.getPecas().get(0).getFill() == pecas_ID_Jog2.getPecas().get(0).getFill());

        Color cor1 = (Color) pecas_ID_Jog1.getPecas().get(0).getFill();
        jogadores.add(new Virtual("Virtual1", new Identificador(cor1.getRed(),cor1.getGreen(),cor1.getBlue())));

        Color cor2 = (Color) pecas_ID_Jog2.getPecas().get(0).getFill();
        jogadores.add(new Virtual("Virtual2", new Identificador(cor2.getRed(),cor2.getGreen(),cor2.getBlue())));
    }

    private void desenhaTopo()
    {
        Image imagemVoltar = ImageLoader.getImage("voltar.png");
        if(imagemVoltar == null)
            return;

        ImageView ivVoltar = new ImageView(imagemVoltar);
        ivVoltar.setFitHeight(40);
        ivVoltar.setPreserveRatio(true);
        btnvoltar.setGraphic(ivVoltar);

        // caixa horizontal botão voltar
        HBox hbox = new HBox();

        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(30);
        hbox.setPadding(new Insets(10, 10, 0, 0));

        setTop(hbox);
        hbox.getChildren().add(btnvoltar);
    }

    private void desenhaCentro()
    {
        Image imagemJOGvsJOG = ImageLoader.getImage("JOGvsJOG.png");
        Image imagemCPUvsCPU = ImageLoader.getImage("CPUvsCPU.png");
        Image imagemJOGvsCPU = ImageLoader.getImage("CPUvsJOG.png");

        if(imagemJOGvsJOG == null || imagemCPUvsCPU == null || imagemJOGvsCPU == null)
            return;


        //hbox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        // caixa horizontal opções

        // IMAGENS

        ImageView ivCPUvsCPU = new ImageView(imagemCPUvsCPU);
        ivCPUvsCPU.setFitWidth(100);
        ivCPUvsCPU.setFitHeight(100);
        ivCPUvsCPU.setPreserveRatio(true);

        ImageView ivJOGvsJOG = new ImageView(imagemJOGvsJOG);
        ivJOGvsJOG.setFitWidth(100);
        ivJOGvsJOG.setFitHeight(100);
        ivJOGvsJOG.setPreserveRatio(true);

        ImageView ivJOGvsCPU = new ImageView(imagemJOGvsCPU);
        ivJOGvsCPU.setFitWidth(100);
        ivJOGvsCPU.setFitHeight(100);
        ivJOGvsCPU.setPreserveRatio(true);


        VBox vBoxCPUvsCPU = new VBox();
        vBoxCPUvsCPU.setAlignment(Pos.CENTER);
        vBoxCPUvsCPU.setSpacing(10);
        vBoxCPUvsCPU.setPadding(new Insets(20));
        vBoxCPUvsCPU.getChildren().addAll(ivCPUvsCPU, btnCPUvsCPU);

        VBox vBoxCPUvsJOG = new VBox();
        vBoxCPUvsJOG.setAlignment(Pos.CENTER);
        vBoxCPUvsJOG.setSpacing(10);
        vBoxCPUvsJOG.setPadding(new Insets(20));
        vBoxCPUvsJOG.getChildren().addAll(ivJOGvsCPU, btnCPUvsJOG);

        VBox vBoxJOGvsJOG = new VBox();
        vBoxJOGvsJOG.setAlignment(Pos.CENTER);
        vBoxJOGvsJOG.setSpacing(10);
        vBoxJOGvsJOG.setPadding(new Insets(20));
        vBoxJOGvsJOG.getChildren().addAll(ivJOGvsJOG, btnJOGvsJOG);

        hboxCenterEscolhaJogo.setAlignment(Pos.TOP_CENTER);
        hboxCenterEscolhaJogo.getChildren().addAll(vBoxCPUvsCPU, vBoxCPUvsJOG,vBoxJOGvsJOG);
        hboxCenterEscolhaJogo.setMaxHeight(200);
//        hboxCenter.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setCenter(hboxCenterEscolhaJogo);
    }

    private void adicionarPecas() {
        pecas_ID_Jog1vsCPU = new EscolhaCorPecas(jogo,false);
        pecas_ID_Jog1 = new EscolhaCorPecas(jogo, false);
        pecas_ID_Jog2 = new EscolhaCorPecas(jogo, false);
    }

    private void update()
    {
       if(jogo.getEstado() == Situacao.AGUARDA_INICIO) {
           setCenter(hboxCenterEscolhaJogo);
           vBoxInsereDadosJog1.setVisible(false);
           tfNomeJogVsCPU.setText(null);
           pecas_ID_Jog1vsCPU.reset();
           pecas_ID_Jog1.reset();
           pecas_ID_Jog2.reset();
           vBoxInsereDadosJog2.setVisible(false);
           jogadores = new ArrayList<>();
           tfNomeJog1.setText(null);
           tfNomeJog2.setText(null);
       }
    }

}
