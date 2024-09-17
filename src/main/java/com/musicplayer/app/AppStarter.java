package com.musicplayer.app;

import com.musicplayer.app.models.Playlist.PlaylistsJsonDeserializer;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.MainViewModel;
import com.musicplayer.app.views.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication;
import eu.lestard.easydi.EasyDI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStarter extends MvvmfxEasyDIApplication {

    private MainViewModel mainViewModel;

    @Override
    public void initEasyDi(EasyDI context) throws IOException {

        context.bindInstance(PlaylistJsonProvider.class, new PlaylistJsonProvider(
                new PlaylistsJsonDeserializer( "/home/chichard/Desktop/playlist.json" )
        ) );
        context.bindInstance(PlaylistsProvider.class, new PlaylistsProvider(FXCollections.observableArrayList()));
        context.bindInstance(NavigationService.class, new NavigationService( new SimpleObjectProperty<>() ));

        context.bindInstance(MainViewModel.class, new MainViewModel(
                new VmProvider(
                        context.getInstance(PlaylistJsonProvider.class),
                        context.getInstance(PlaylistsProvider.class),
                        context.getInstance(NavigationService.class)
                )
        ));

        // ViewModels

        mainViewModel = context.getInstance(MainViewModel.class);
    }

    @Override
    public void startMvvmfx(Stage stage) throws Exception {

        Image icon = new Image(String.valueOf(getClass().getResource("images/favicon.png")));
        String stylePath = String.valueOf(getClass().getResource( "css/main.css" ));

        stage.setTitle("Simple Music Player");
        stage.getIcons().add(icon);

        Parent root = FluentViewLoader.fxmlView(MainView.class).viewModel(mainViewModel).load().getView();

        Scene scene = new Scene(root, 1400, 720);
        scene.getStylesheets().add( stylePath );

        stage.setScene(scene);
        stage.show();

    }
}