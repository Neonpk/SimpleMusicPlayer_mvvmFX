package com.musicplayer.app;

import com.musicplayer.app.views.MainContainerView;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppStarter extends Application {

    @Override
    public void start(Stage stage) {

        Image icon = new Image(String.valueOf(getClass().getResource("images/favicon.png")));
        String stylePath = String.valueOf(getClass().getResource( "css/main.css" ));

        stage.setTitle("Simple Music Player");
        stage.getIcons().add(icon);

        Parent root = FluentViewLoader.fxmlView(MainContainerView.class).load().getView();

        Scene scene = new Scene(root, 1400, 720);
        scene.getStylesheets().add( stylePath );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}