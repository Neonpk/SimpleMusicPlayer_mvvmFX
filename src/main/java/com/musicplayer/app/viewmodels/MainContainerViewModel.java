package com.musicplayer.app.viewmodels;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.listview_commands.CreatePlaylistCommand;
import com.musicplayer.app.commands.listview_commands.SelectedPlaylistCommand;
import com.musicplayer.app.commands.media_commands.*;
import com.musicplayer.app.models.MediaListeners;
import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.models.PlaylistJsonSerializer;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import lombok.Getter;

import java.io.IOException;
import java.util.*;

public class MainContainerViewModel implements ViewModel {

    // Properties (fields)

    @Getter
    private final Property<Number> selectedAudioIndexProperty = new SimpleObjectProperty<>(0);

    @Getter
    private final Property<Number> selectedVolumeProperty = new SimpleObjectProperty<>(50.0f);

    @Getter
    private final Property<Number> selectedProgressProperty = new SimpleObjectProperty<>(0.0f);

    @Getter
    private final StringProperty timePositionTextProperty = new SimpleStringProperty("00:00");

    @Getter
    private final StringProperty timeDurationTextProperty = new SimpleStringProperty("00:00");

    @Getter
    private final StringProperty artistTextProperty = new SimpleStringProperty("Неизвестный артист");

    @Getter
    private final StringProperty titleTextProperty = new SimpleStringProperty("Неизвестный заголовок");

    @Getter
    private final StringProperty fileNameTextProperty = new SimpleStringProperty("Путь не найден.");

    @Getter
    private final StringProperty playButtonTextProperty = new SimpleStringProperty(">");

    @Getter
    private final StringProperty muteButtonTextProperty = new SimpleStringProperty("Выкл звук");

    @Getter
    private final StringProperty repeatButtonTextProperty = new SimpleStringProperty("Повторять");

    @Getter
    private final Property<Boolean> sliderProgressUpdateProperty = new SimpleObjectProperty<>(false);

    @Getter
    private final Property<Boolean> repeatStatusProperty = new SimpleObjectProperty<>(false);

    @Getter
    private final Property<Image> imageCoverProperty = new SimpleObjectProperty<>(
            new Image( Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString() )
    );

    @Getter
    private final Property<Media> mediaProperty = new SimpleObjectProperty<>();

    @Getter
    private final Property<MediaPlayer> mediaPlayerProperty = new SimpleObjectProperty<>();

    @Getter
    private final Property<ContextMenu> contextMenuProperty = new SimpleObjectProperty<>();

    @Getter
    private final Property<Playlist> selectedPlaylistProperty = new SimpleObjectProperty<>();

    @Getter
    private final Property<Node> selectedView = new SimpleObjectProperty<>();

    // Fields

    @Getter
    private final ObservableList<Playlist> playlist = FXCollections.observableArrayList();

    @Getter
    private final List<String> fileNamesList = new ArrayList<>();

    // Fields => Listeners

    private final MediaListeners mediaListeners = new MediaListeners(this);

    // Commands

    @Getter
    private final Command playPauseCommand = new PlayPauseCommand(mediaPlayerProperty, playButtonTextProperty);
    @Getter
    private final Command volumeControlCommand = new VolumeControlCommand(mediaPlayerProperty, selectedVolumeProperty);
    @Getter
    private final Command muteAudioCommand = new MuteAudioCommand(mediaPlayerProperty, muteButtonTextProperty);
    @Getter
    private final Command seekAudioCommand = new SeekAudioCommand(mediaPlayerProperty, selectedProgressProperty);
    @Getter
    private final Command repeatAudioCommand = new RepeatAudioCommand(mediaPlayerProperty, repeatStatusProperty, repeatButtonTextProperty);

    @Getter
    private final Command switchNextAudioCommand = mediaListeners.getSwitchNextAudioCommand();
    @Getter
    private final Command switchPrevAudioCommand = mediaListeners.getSwitchPrevAudioCommand();

    @Getter
    final Command selectedPlaylistCommand = new SelectedPlaylistCommand(selectedPlaylistProperty, selectedView);

    @Getter
    private final Command playlistCreateCommand = new CreatePlaylistCommand(selectedView);

    // Constructor

    public MainContainerViewModel() throws IOException {

        var cm = new ContextMenu();

        var mi1 = new MenuItem("Изменить название");
        var mi2 = new MenuItem("Удалить плейлист");

        cm.getItems().add(mi1);
        cm.getItems().add(mi2);

        contextMenuProperty.setValue(cm);

        //playlist.addAll(new PlaylistJsonSerializer("/home/chichard/Desktop/playlist.json").Deserialize());

        new InitializeMediaCommand(fileNamesList, mediaProperty, mediaPlayerProperty, mediaListeners).execute();
    }

}
