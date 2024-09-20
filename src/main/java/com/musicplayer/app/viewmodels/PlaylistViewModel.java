package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.track_commands.AddNewTracksCommand;
import com.musicplayer.app.commands.track_commands.DeleteTrackCommand;
import com.musicplayer.app.commands.track_commands.PlayTrackCommand;
import com.musicplayer.app.commands.track_commands.SearchTracksCommand;
import com.musicplayer.app.models.CommandParams.DeleteTrackCmdParam;
import com.musicplayer.app.models.CommandParams.PlayTrackCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.models.Track.TrackCollectionListener;
import com.musicplayer.app.services.MediaProvider;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
    private final Property<Playlist> selectedPlaylistProperty = new SimpleObjectProperty<>();
    private final Property<Track> selectedTrackProperty = new SimpleObjectProperty<>();
    private final StringProperty searchTextProperty = new SimpleStringProperty();
    private final Property<ContextMenu> contextMenuProperty = new SimpleObjectProperty<>();

    private final ObservableList<Track> tracks = FXCollections.observableArrayList();
    private final FilteredList<Track> filteredTrackList = new FilteredList<>(tracks, (_) -> true);

    private final Command deleteTrackCommand;
    private final Command playTrackCommand;
    private final Command addTracksCommand;
    private final Command searchTracksCommand;

    public PlaylistViewModel(VmProvider vmProvider, Property<Playlist> selectedPlaylistProperty) {

        // Providers
        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();
        MediaProvider mediaProvider = vmProvider.getMediaProvider();

        // Properties

        Property<Media> mediaProperty = mediaProvider.getMediaProperty();
        Property<MediaPlayer> mediaPlayerProperty = mediaProvider.getMediaPlayerProperty();
        Property<Number> selectedAudioIndexProperty = mediaProvider.getSelectedAudioIndexProperty();
        List<Track> trackList = mediaProvider.getTrackList();

        // Listeners

        Property<MapChangeListener<String, Object>> metaDataChangeListenerProperty = mediaProvider.getMetaDataChangeListener();
        Property<ChangeListener<Duration>> durationChangeListenerProperty = mediaProvider.getDurationChangeListener();
        Property<Runnable> onReadyMediaListenerProperty = mediaProvider.getOnReadyMediaListener();
        Property<Runnable> onEndMediaListenerProperty = mediaProvider.getOnEndMediaListener();

        int playlistId = selectedPlaylistProperty.getValue().getId();

        // Commands

        deleteTrackCommand = new DeleteTrackCommand(new DeleteTrackCmdParam(playlistId, tracks, selectedTrackProperty));
        addTracksCommand = new AddNewTracksCommand(tracks);
        searchTracksCommand = new SearchTracksCommand(filteredTrackList, searchTextProperty);

        playTrackCommand = new PlayTrackCommand(
                new PlayTrackCmdParam(
                        trackList, selectedTrackProperty, selectedAudioIndexProperty,
                        selectedPlaylistProperty, mediaProperty, mediaPlayerProperty,
                        metaDataChangeListenerProperty, durationChangeListenerProperty,
                        onReadyMediaListenerProperty, onEndMediaListenerProperty
                )
        );

        playlistNameProperty.setValue(selectedPlaylistProperty.getValue().getName());

        this.selectedPlaylistProperty.bindBidirectional(selectedPlaylistProperty);
        tracks.addAll(selectedPlaylistProperty.getValue().getTracks());
        tracks.addListener(new TrackCollectionListener(playlistJsonProvider, selectedPlaylistProperty).getTracksListener());
    }

}
