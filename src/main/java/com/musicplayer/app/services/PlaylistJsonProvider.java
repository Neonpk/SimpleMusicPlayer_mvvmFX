package com.musicplayer.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.models.PlaylistJsonDeserializer;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class PlaylistJsonProvider {

    private final PlaylistJsonDeserializer playlistJsonDeserializerFactory;

    public List<Playlist> Deserialize() throws IOException {
        return playlistJsonDeserializerFactory.Deserialize();
    }

    public void replacePlaylist(int id, Playlist playlist) throws IOException {
        List<Playlist> playlists = this.Deserialize();
        playlists.set( id, playlist );
        new ObjectMapper().writeValue(new File(playlistJsonDeserializerFactory.getFileName()), playlists);
    }

    public void addPlaylist(Playlist playlist) throws IOException {
        List<Playlist> playlists = this.Deserialize();
        playlists.add( playlist );
        new ObjectMapper().writeValue(new File(playlistJsonDeserializerFactory.getFileName()), playlists);
    }

    public void removePlaylist( int id ) throws IOException {
        List<Playlist> playlists = this.Deserialize();
        playlists.remove( id - 1 );
        new ObjectMapper().writeValue(new File(playlistJsonDeserializerFactory.getFileName()), playlists);
    }

}
