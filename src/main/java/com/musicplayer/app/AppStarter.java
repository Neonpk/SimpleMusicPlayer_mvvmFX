package com.musicplayer.app;

import com.musicplayer.app.models.PlaylistJsonDeserializer;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import com.musicplayer.app.viewmodels.MainContainerViewModel;
import com.musicplayer.app.views.MainContainerView;
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

    private MainContainerViewModel mainContainerViewModel;

    @Override
    public void initEasyDi(EasyDI context) throws IOException {

        context.bindInstance(PlaylistJsonProvider.class, new PlaylistJsonProvider(
                new PlaylistJsonDeserializer( "/home/chichard/Desktop/playlist.json" )
        ) );
        context.bindInstance(PlaylistsProvider.class, new PlaylistsProvider(FXCollections.observableArrayList()));
        context.bindInstance(NavigationService.class, new NavigationService( new SimpleObjectProperty<>() ));

        context.bindInstance(MainContainerViewModel.class, new MainContainerViewModel(
                new VmProvider(
                        context.getInstance(PlaylistJsonProvider.class),
                        context.getInstance(PlaylistsProvider.class),
                        context.getInstance(NavigationService.class)
                )
        ));

        // ViewModels

        mainContainerViewModel = context.getInstance(MainContainerViewModel.class);
    }

    @Override
    public void startMvvmfx(Stage stage) throws Exception {

        Image icon = new Image(String.valueOf(getClass().getResource("images/favicon.png")));
        String stylePath = String.valueOf(getClass().getResource( "css/main.css" ));

        stage.setTitle("Simple Music Player");
        stage.getIcons().add(icon);

        Parent root = FluentViewLoader.fxmlView(MainContainerView.class).viewModel(mainContainerViewModel).load().getView();

        Scene scene = new Scene(root, 1400, 720);
        scene.getStylesheets().add( stylePath );

        stage.setScene(scene);
        stage.show();

    }
}