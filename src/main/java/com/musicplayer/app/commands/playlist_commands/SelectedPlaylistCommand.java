package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import com.musicplayer.app.services.*;
import com.musicplayer.app.viewmodels.PlaylistViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;

public class SelectedPlaylistCommand extends DelegateCommand {

    private static void selectedPlaylist(VmProvider vmProvider) {

        // Providers

        NavigationService navigationService = vmProvider.getNavigationService();
        PlaylistSelectionProvider playlistSelectionProvider = vmProvider.getPlaylistSelectionProvider();

        // Listeners
        ListChangeListener<Track> trackCollectionListener = playlistSelectionProvider.getTrackCollectionListener().getValue();

        // Instances

        Property<Playlist> selectedPlaylistProperty = playlistSelectionProvider.getSelectedPlaylistProperty();
        StringProperty selectedPlaylistNameProperty = playlistSelectionProvider.getSelectedPlaylistNameProperty();
        Property<ObservableList<Track>> observablePlaylistTracksProperty =  playlistSelectionProvider.getObservablePlaylistTracksProperty();
        Property<FilteredList<Track>> filteredPlaylistTracksProperty = playlistSelectionProvider.getFilteredPlaylistTracksProperty();

        List<Track> trackList = playlistSelectionProvider.getSelectedPlaylistProperty().getValue().getTracks();

        // Operations

        selectedPlaylistNameProperty.setValue(selectedPlaylistProperty.getValue().getName());

        ObservableList<Track> oldObservablePlaylistTracks;
        if( (oldObservablePlaylistTracks = observablePlaylistTracksProperty.getValue()) != null) {
            oldObservablePlaylistTracks.removeListener(trackCollectionListener);
        }

        ObservableList<Track> newObservablePlaylistTracks = FXCollections.observableArrayList(trackList);
        newObservablePlaylistTracks.addListener(trackCollectionListener);

        observablePlaylistTracksProperty.setValue(newObservablePlaylistTracks);
        filteredPlaylistTracksProperty.setValue(new FilteredList<>(newObservablePlaylistTracks, (_) -> true));

        // Navigate

        navigationService.navigate(PlaylistViewModel.class);
    }

    public SelectedPlaylistCommand(VmProvider vmProvider) {
        super(() -> new Action() {
            @Override
            protected void action() {
                selectedPlaylist(vmProvider);
            }
        });
    }

}
