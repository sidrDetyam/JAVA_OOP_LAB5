package ru.nsu.fit.gemuev.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fit.gemuev.client.controllers.MainSceneController;

import java.io.*;


public class Client extends Application {

    private static Scene scene;
    public static Model model;
    public static MainSceneController primaryController;


    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Chat client");
        model = new Model();
        scene = new Scene(loadFXML("/LoginScene.fxml"), 587, 400);
        stage.setScene(scene);
        stage.show();
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

