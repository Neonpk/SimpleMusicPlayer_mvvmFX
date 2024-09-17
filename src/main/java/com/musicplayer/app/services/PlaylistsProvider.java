package com.musicplayer.app.services;

import com.musicplayer.app.models.Playlist.Playlist;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlaylistsProvider {
    private final ObservableList<Playlist> playlists;
}