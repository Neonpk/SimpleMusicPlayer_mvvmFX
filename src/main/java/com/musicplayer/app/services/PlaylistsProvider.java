package com.musicplayer.app.services;

import com.musicplayer.app.models.Playlist;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class PlaylistsProvider {

    private final ObservableList<Playlist> playlists;

    public PlaylistsProvider(ObservableList<Playlist> playlists) {
        this.playlists = playlists;
    }

}
