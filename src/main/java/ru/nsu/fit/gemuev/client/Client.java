package ru.nsu.fit.gemuev.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;


public class Client extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("CiderChat");
        stage.getIcons().add(new Image(Objects.requireNonNull(
                Client.class.getResourceAsStream("/CiderIcon.png"))));

        scene = new Scene(loadFXML("/LoginScene.fxml"), 587, 400);
        new Scene(loadFXML("/MainScene.fxml"), 587, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void setMainScene(){
        setRoot("/MainScene.fxml");
    }

    public static void setLoginScene(){
        setRoot("/LoginScene.fxml");
    }

    static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));
        }
        catch(IOException e){
            throw new IllegalStateException(e);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
