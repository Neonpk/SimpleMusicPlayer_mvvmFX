package com.musicplayer.app;

import com.musicplayer.app.views.MainContainerView;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStarter extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("test");

        Parent root = FluentViewLoader.fxmlView(MainContainerView.class).load().getView();
        stage.setScene(new Scene(root, 1400, 720));
        stage.show();
    }

    public static void main(String... args) {
        launch(args);
    }
}