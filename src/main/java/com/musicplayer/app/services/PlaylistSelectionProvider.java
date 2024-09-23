package com.musicplayer.app.services;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Track.Track;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@SuppressWarnings("all")
public class PlaylistSelectionProvider {
    private Property<Playlist> selectedPlaylistProperty;
    private Property<Boolean> playlistIsSelectedProperty;
    private StringProperty selectedPlaylistNameProperty;
    private Property<ObservableList<Track>> observablePlaylistTracksProperty;
    private Property<FilteredList<Track>> filteredPlaylistTracksProperty;
    private Property<ListChangeListener<Track>> trackCollectionListener;
}
