package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.track_commands.AddNewTracksCommand;
import com.musicplayer.app.commands.track_commands.DeleteTrackCommand;
import com.musicplayer.app.commands.track_commands.SearchTracksCommand;
import com.musicplayer.app.models.CommandParams.DeleteTrackCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.models.Track.TrackCollectionListener;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ContextMenu;
import lombok.Getter;

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
    private final Command addTracksCommand;
    private final Command searchTracksCommand;

    public PlaylistViewModel(VmProvider vmProvider, Property<Playlist> selectedPlaylistProperty) {

        // Providers
        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();

        int playlistId = selectedPlaylistProperty.getValue().getId();

        deleteTrackCommand = new DeleteTrackCommand(new DeleteTrackCmdParam(playlistId, tracks, selectedTrackProperty));
        addTracksCommand = new AddNewTracksCommand(tracks);
        searchTracksCommand = new SearchTracksCommand(filteredTrackList, searchTextProperty);

        playlistNameProperty.setValue(selectedPlaylistProperty.getValue().getName());

        this.selectedPlaylistProperty.bindBidirectional(selectedPlaylistProperty);
        tracks.addAll(selectedPlaylistProperty.getValue().getTracks());
        tracks.addListener(new TrackCollectionListener(playlistJsonProvider, selectedPlaylistProperty).getTracksListener());
    }

}
