package com.musicplayer.app;

import com.musicplayer.app.models.CacheLoader;
import com.musicplayer.app.models.Playlist.PlaylistsJsonDeserializer;
import com.musicplayer.app.services.*;
import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import com.musicplayer.app.viewmodels.MainViewModel;
import com.musicplayer.app.viewmodels.PlaylistViewModel;
import com.musicplayer.app.views.CreateEditPlaylistView;
import com.musicplayer.app.views.MainView;
import com.musicplayer.app.views.PlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication;
import eu.lestard.easydi.EasyDI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class AppStarter extends MvvmfxEasyDIApplication {

    // Custom Configurations

    private static class ViewContext extends HashMap<Class<? extends ViewModel>, ViewTuple<? extends FxmlView<? extends ViewModel>, ? extends ViewModel> > {}
    private final ViewContext viewContext = new ViewContext();

    // Instances

    private MainViewModel mainViewModel;


    @Override
    public void initEasyDi(EasyDI context) throws IOException {

        Function<Class<? extends ViewModel>, ViewTuple<? extends FxmlView<? extends ViewModel>, ? extends ViewModel>> viewFactoryFunc
                = viewContext::get;

        context.bindInstance(NavigationService.class, new NavigationService(viewFactoryFunc));

        context.bindInstance(PlaylistSelectionProvider.class, new PlaylistSelectionProvider(
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(false),
                new SimpleStringProperty(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>()
        ));

        context.bindInstance(MediaProvider.class, new MediaProvider(
                FXCollections.observableArrayList(),
                new ArrayList<>(),
                new SimpleObjectProperty<>(0),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>(),
                new SimpleObjectProperty<>()
        ));

        context.bindInstance(PlaylistJsonProvider.class, new PlaylistJsonProvider(
                new PlaylistsJsonDeserializer(CacheLoader.loadJsonCache("playlists.json"))
        ) );

        context.bindInstance(VmProvider.class, new VmProvider(
                context.getInstance(PlaylistSelectionProvider.class),
                context.getInstance(PlaylistJsonProvider.class),
                context.getInstance(MediaProvider.class),
                context.getInstance(NavigationService.class)
        ));

        // ViewModels
        context.bindInstance(MainViewModel.class, new MainViewModel(context.getInstance(VmProvider.class)));
        context.bindInstance(CreateEditPlaylistViewModel.class, new CreateEditPlaylistViewModel(context.getInstance(VmProvider.class)));
        context.bindInstance(PlaylistViewModel.class, new PlaylistViewModel(context.getInstance(VmProvider.class)));

        // HashMap view Instances
        viewContext.put(CreateEditPlaylistViewModel.class, FluentViewLoader.fxmlView(CreateEditPlaylistView.class).viewModel(
           context.getInstance(CreateEditPlaylistViewModel.class)
        ).load());

        viewContext.put(PlaylistViewModel.class, FluentViewLoader.fxmlView(PlaylistView.class).viewModel(
           context.getInstance(PlaylistViewModel.class)
        ).load());

        // Instances
        mainViewModel = context.getInstance(MainViewModel.class);
    }

    @Override
    public void startMvvmfx(Stage stage) {

        Image icon = new Image(String.valueOf(getClass().getResource("images/favicon.png")));
        String stylePath = String.valueOf(getClass().getResource( "css/main.css" ));

        stage.setTitle("Simple Music Player");
        stage.getIcons().add(icon);

        Parent root = FluentViewLoader.fxmlView(MainView.class).viewModel(mainViewModel).load().getView();

        Scene scene = new Scene(root, 1400, 720);
        scene.getStylesheets().add( stylePath );

        stage.setMinHeight(720);
        stage.setMinWidth(1400);
        stage.setScene(scene);
        stage.show();

    }
}