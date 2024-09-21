package com.musicplayer.app.viewmodels;

import com.musicplayer.app.AppStarter;
import com.musicplayer.app.commands.playlist_commands.CreatePlaylistCommand;
import com.musicplayer.app.commands.playlist_commands.DeletePlaylistCommand;
import com.musicplayer.app.commands.playlist_commands.EditPlaylistCommand;
import com.musicplayer.app.commands.playlist_commands.SelectedPlaylistCommand;
import com.musicplayer.app.commands.media_commands.*;
import com.musicplayer.app.models.MediaListeners;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Playlist.PlaylistCollectionListener;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.MediaProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import com.musicplayer.app.models.Track.Track;
import javafx.util.Duration;
import lombok.Getter;

import java.io.IOException;
import java.util.*;

@Getter
public class MainViewModel implements ViewModel {

    // Properties (fields)
    private final Property<Number> selectedAudioIndexProperty = new SimpleObjectProperty<>(0);

    private final Property<Number> selectedVolumeProperty = new SimpleObjectProperty<>(50.0f);

    private final Property<Number> selectedProgressProperty = new SimpleObjectProperty<>(0.0f);

    private final StringProperty timePositionTextProperty = new SimpleStringProperty("00:00");

    private final StringProperty timeDurationTextProperty = new SimpleStringProperty("00:00");

    private final StringProperty artistTextProperty = new SimpleStringProperty("Неизвестный артист");

    private final StringProperty titleTextProperty = new SimpleStringProperty("Неизвестный заголовок");

    private final StringProperty playButtonTextProperty = new SimpleStringProperty(">");

    private final StringProperty muteButtonTextProperty = new SimpleStringProperty("Выкл звук");

    private final StringProperty repeatButtonTextProperty = new SimpleStringProperty("Повторять");

    private final Property<Boolean> sliderProgressUpdateProperty = new SimpleObjectProperty<>(false);

    private final Property<Boolean> muteStatusProperty = new SimpleObjectProperty<>(false);

    private final Property<Boolean> repeatStatusProperty = new SimpleObjectProperty<>(false);

    private final Property<Image> imageCoverProperty = new SimpleObjectProperty<>(
            new Image( Objects.requireNonNull(AppStarter.class.getResource("images/nocover.jpg")).toString() )
    );

    private final Property<Media> mediaProperty = new SimpleObjectProperty<>();

    private final Property<MediaPlayer> mediaPlayerProperty = new SimpleObjectProperty<>();

    private final Property<ContextMenu> contextMenuProperty = new SimpleObjectProperty<>();

    private final Property<Playlist> selectedPlaylistProperty = new SimpleObjectProperty<>();

    private final Property<Node> selectedView = new SimpleObjectProperty<>();

    // Properties -> Listeners

    private final Property<MapChangeListener<String, Object>> metaDataChangeListener = new SimpleObjectProperty<>();
    private final Property<ChangeListener<Duration>> durationChangeListener = new SimpleObjectProperty<>();
    private final Property<Runnable> onReadyMediaListener = new SimpleObjectProperty<>();
    private final Property<Runnable> onStoppedMediaListener = new SimpleObjectProperty<>();
    private final Property<Runnable> onEndMediaListener = new SimpleObjectProperty<>();

    // Fields

    private final ObservableList<Playlist> playlists;

    private final List<Track> trackListQueue;

    // Commands

    private final Command playPauseCommand = new PlayPauseCommand(mediaPlayerProperty, playButtonTextProperty);
    private final Command volumeControlCommand = new VolumeControlCommand(mediaPlayerProperty, selectedVolumeProperty);
    private final Command muteAudioCommand = new MuteAudioCommand(mediaPlayerProperty, muteStatusProperty, muteButtonTextProperty);
    private final Command seekAudioCommand = new SeekAudioCommand(mediaPlayerProperty, selectedProgressProperty);
    private final Command repeatAudioCommand = new RepeatAudioCommand(mediaPlayerProperty, repeatStatusProperty, repeatButtonTextProperty);

    // Commands initialized in constructor

    private final Command switchNextAudioCommand;
    private final Command switchPrevAudioCommand;
    private final Command selectedPlaylistCommand;
    private final Command playlistCreateCommand;
    private final Command playlistEditCommand;
    private final Command deletePlaylistCommand;

    // Constructor

    public MainViewModel(VmProvider vmProvider) throws IOException {

        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();
        MediaProvider mediaProvider = vmProvider.getMediaProvider();
        NavigationService navigationService = vmProvider.getNavigationService();

        this.metaDataChangeListener.bindBidirectional( vmProvider.getMediaProvider().getMetaDataChangeListener() );
        this.durationChangeListener.bindBidirectional( vmProvider.getMediaProvider().getDurationChangeListener() );
        this.onReadyMediaListener.bindBidirectional( vmProvider.getMediaProvider().getOnReadyMediaListener() );
        this.onStoppedMediaListener.bindBidirectional( vmProvider.getMediaProvider().getOnStoppedMediaListener() );
        this.onEndMediaListener.bindBidirectional( vmProvider.getMediaProvider().getOnEndMediaListener() );

        this.mediaProperty.bindBidirectional(mediaProvider.getMediaProperty());
        this.mediaPlayerProperty.bindBidirectional(mediaProvider.getMediaPlayerProperty());
        this.selectedAudioIndexProperty.bindBidirectional(mediaProvider.getSelectedAudioIndexProperty());

        navigationService.bindBidirectional(selectedView);

        playlists = mediaProvider.getPlaylists();
        trackListQueue = mediaProvider.getTrackListQueue();

        MediaListeners mediaListeners = new MediaListeners(this);

        switchNextAudioCommand = mediaListeners.getSwitchNextAudioCommand();
        switchPrevAudioCommand = mediaListeners.getSwitchPrevAudioCommand();

        selectedPlaylistCommand = new SelectedPlaylistCommand(vmProvider, selectedPlaylistProperty, selectedView);
        playlistCreateCommand = new CreatePlaylistCommand(vmProvider, selectedView);
        playlistEditCommand = new EditPlaylistCommand(vmProvider, selectedPlaylistProperty, selectedView);
        deletePlaylistCommand = new DeletePlaylistCommand(vmProvider, selectedPlaylistProperty);

        playlists.addAll(playlistJsonProvider.Deserialize());
        playlists.addListener(new PlaylistCollectionListener(playlistJsonProvider).getPlaylistListener());
    }

}
