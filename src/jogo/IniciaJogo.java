package jogo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jogo.logica.JogoCareTakerObservable;
import jogo.ui.gui.JanelaPrincipal;
import jogo.ui.gui.resources.ImageLoader;

import java.io.IOException;

import static jogo.ui.gui.Constants.*;

public class IniciaJogo extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        JogoCareTakerObservable model = new JogoCareTakerObservable();
        JanelaPrincipal root = new JanelaPrincipal(model, CANVAS_WIDTH, CANVAS_HEIGHT);

        Scene scene = new Scene(root,CANVAS_WIDTH,CANVAS_HEIGHT);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("4 EM LINHA");

        Image image = ImageLoader.getImage("logo.jpg");

        if(image==null)
            return;

        primaryStage.getIcons().add(image);

        primaryStage.setOnCloseRequest(ev -> Platform.exit());
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}

