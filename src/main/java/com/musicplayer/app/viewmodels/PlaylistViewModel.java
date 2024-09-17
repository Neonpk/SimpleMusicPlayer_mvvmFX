package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.track_commands.AddNewTracksCommand;
import com.musicplayer.app.commands.track_commands.DeleteTrackCommand;
import com.musicplayer.app.models.CommandParams.DeleteTrackCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.models.Track.TrackCollectionListener;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import lombok.Getter;

@Getter
public class PlaylistViewModel implements ViewModel {

    private final Property<Playlist> selectedPlaylistProperty = new SimpleObjectProperty<>();
    private final Property<Track> selectedTrackProperty = new SimpleObjectProperty<>();
    private final StringProperty searchTextProperty = new SimpleStringProperty();
    private final Property<ContextMenu> contextMenuProperty = new SimpleObjectProperty<>();

    private final ObservableList<Track> tracks = FXCollections.observableArrayList();

    private final Command deleteTrackCommand;
    private final Command addTracksCommand;

    public PlaylistViewModel(VmProvider vmProvider, Property<Playlist> selectedPlaylistProperty) {

        // Providers
        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();

        int playlistId = selectedPlaylistProperty.getValue().getId();

        deleteTrackCommand = new DeleteTrackCommand(new DeleteTrackCmdParam(playlistId, tracks, selectedTrackProperty));
        addTracksCommand = new AddNewTracksCommand(tracks);

        this.selectedPlaylistProperty.bindBidirectional(selectedPlaylistProperty);
        tracks.addAll(selectedPlaylistProperty.getValue().getTracks());
        tracks.addListener(new TrackCollectionListener(playlistJsonProvider, selectedPlaylistProperty).getTracksListener());
    }

}
