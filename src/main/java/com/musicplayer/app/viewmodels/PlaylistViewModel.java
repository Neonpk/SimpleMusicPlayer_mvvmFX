package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.track_commands.*;
import com.musicplayer.app.models.CommandParams.PlayTrackCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.services.MediaProvider;
import com.musicplayer.app.services.PlaylistSelectionProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ContextMenu;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistViewModel implements ViewModel {

    private final StringProperty playlistNameProperty = new SimpleStringProperty();
    private final Property<Track> selectedTrackProperty = new SimpleObjectProperty<>();
    private final StringProperty searchTextProperty = new SimpleStringProperty();
    private final Property<ContextMenu> contextMenuProperty = new SimpleObjectProperty<>();

    private final Property<ObservableList<Track>> tracksProperty = new SimpleObjectProperty<>();
    private final Property<FilteredList<Track>> filteredListTracksProperty = new SimpleObjectProperty<>();

    private final Command deleteTrackCommand;
    private final Command deleteMissingTracksCommand;
    private final Command playSelectedTrackCommand;
    private final Command addTracksCommand;
    private final Command searchTracksCommand;
    private final Command playPlaylistTracksCommand;

    public PlaylistViewModel(VmProvider vmProvider) {

        // Providers
        PlaylistSelectionProvider playlistSelectionProvider = vmProvider.getPlaylistSelectionProvider();
        MediaProvider mediaProvider = vmProvider.getMediaProvider();

        // Properties

        Property<Playlist> selectedPlaylistProperty = playlistSelectionProvider.getSelectedPlaylistProperty();
        Property<Media> mediaProperty = mediaProvider.getMediaProperty();
        Property<MediaPlayer> mediaPlayerProperty = mediaProvider.getMediaPlayerProperty();
        Property<Number> selectedAudioIndexProperty = mediaProvider.getSelectedAudioIndexProperty();
        List<Track> trackListQueue = mediaProvider.getTrackListQueue();

        // Listeners

        Property<MapChangeListener<String, Object>> metaDataChangeListenerProperty = mediaProvider.getMetaDataChangeListener();
        Property<ChangeListener<Duration>> durationChangeListenerProperty = mediaProvider.getDurationChangeListener();
        Property<Runnable> onReadyMediaListenerProperty = mediaProvider.getOnReadyMediaListener();
        Property<Runnable> onStoppedMediaListener = mediaProvider.getOnStoppedMediaListener();
        Property<Runnable> onEndMediaListenerProperty = mediaProvider.getOnEndMediaListener();

        // Commands

        deleteTrackCommand = new DeleteTrackCommand(tracksProperty, selectedTrackProperty);
        deleteMissingTracksCommand = new DeleteMissingTracksCommand(tracksProperty);
        addTracksCommand = new AddNewTracksCommand(tracksProperty);
        searchTracksCommand = new SearchTracksCommand(filteredListTracksProperty, searchTextProperty);

        playPlaylistTracksCommand = new PlayPlaylistTracksCommand(
                new PlayTrackCmdParam(
                        trackListQueue, null, selectedAudioIndexProperty,
                        selectedPlaylistProperty, mediaProperty, mediaPlayerProperty,
                        metaDataChangeListenerProperty, durationChangeListenerProperty,
                        onReadyMediaListenerProperty, onStoppedMediaListener, onEndMediaListenerProperty
                )
        );

        playSelectedTrackCommand = new PlaySelectedTrackCommand(
                new PlayTrackCmdParam(
                        trackListQueue, selectedTrackProperty, selectedAudioIndexProperty,
                        selectedPlaylistProperty, mediaProperty, mediaPlayerProperty,
                        metaDataChangeListenerProperty, durationChangeListenerProperty,
                        onReadyMediaListenerProperty, onStoppedMediaListener, onEndMediaListenerProperty
                )
        );

        playlistNameProperty.bindBidirectional( playlistSelectionProvider.getSelectedPlaylistNameProperty() );
        tracksProperty.bindBidirectional(playlistSelectionProvider.getObservablePlaylistTracksProperty());
        filteredListTracksProperty.bindBidirectional(playlistSelectionProvider.getFilteredPlaylistTracksProperty());
    }

}
