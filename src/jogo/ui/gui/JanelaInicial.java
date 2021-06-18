package jogo.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import jogo.logica.JogoCareTakerObservable;
import jogo.ui.gui.resources.ImageLoader;

import java.io.File;
import java.io.IOException;


public class JanelaInicial extends BorderPane
{
    JogoCareTakerObservable jogo;

    Button startButton;
    Button replayButton;
    Button loadGameButton;
    Button exitButton;

    public JanelaInicial(JogoCareTakerObservable jogo, int width, int height)
    {
        this.jogo = jogo;
        setWidth(width);
        setHeight(height);

        criaObjetosGraficos();
        dispoeVista();
        registaHandlers();
    }

    private void criaObjetosGraficos()
    {
        startButton = new Button("Iniciar novo jogo");
        replayButton = new Button("Fazer Replay de jogo");
        loadGameButton = new Button("Carregar jogo gravado");
        exitButton = new Button("Sair");
    }

    private void dispoeVista()
    {

        // caixa a vertical imagem
        HBox hbox = new HBox();

        Image imagem = ImageLoader.getImage("logo.jpg");
        ImageView imgview = new ImageView();
        imgview.setImage(imagem);
        imgview.setFitWidth(300);
        imgview.setFitHeight(300);
        hbox.getChildren().add(imgview);

        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);
        hbox.setPadding(new Insets(10, 0, 0, 0));

        setTop(hbox);

        // caixa vertical
        VBox vbox = new VBox();

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(startButton,replayButton, loadGameButton, exitButton);
        setCenter(vbox);

    }

    private void registaHandlers()
    {
        startButton.setOnAction(ev-> {
            jogo.definirModoJogo();
        });

        loadGameButton.setOnAction(ev->{
            FileChooser fChooser = new FileChooser();
            File f = fChooser.showOpenDialog(null);

            if(f==null)
                return;

            try{
                jogo.carregarJogo(f.getCanonicalPath());
            }catch (IOException | ClassNotFoundException e){
                System.err.println("Save: " + e);
            }
        });

        replayButton.setOnAction(ev-> {
            FileChooser fChooser = new FileChooser();
            File f = fChooser.showOpenDialog(null);

            if(f==null)
                return;

            try{
                if(!jogo.carregarJogoReplay(f.getCanonicalPath()))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("ERRO REPLAY");
                    alert.setHeaderText("O jogo selecionado não é um jogo terminado ");
                    alert.showAndWait();
                }
            }catch (IOException | ClassNotFoundException e){
                System.err.println("Save: " + e);
            }
        });

        exitButton.setOnAction(ev -> Platform.exit());
    }


}
