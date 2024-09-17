package com.musicplayer.app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.Playlist.PlaylistsJsonDeserializer;
import com.musicplayer.app.models.Track.Track;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@AllArgsConstructor
public class PlaylistJsonProvider {

    private final PlaylistsJsonDeserializer playlistsJsonDeserializerFactory;

    public List<Playlist> Deserialize() throws IOException {
        return playlistsJsonDeserializerFactory.Deserialize();
    }

    public void replacePlaylist(int playlistId, Playlist playlist) throws IOException {

        List<Playlist> playlists = this.Deserialize();

        int playlistElementIndex = IntStream.range(0, playlists.size())
                        .filter(i -> Objects.equals( playlists.get(i).getId(), playlistId ))
                        .findFirst().orElse(-1);

        playlists.set( playlistElementIndex, playlist );

        new ObjectMapper().writeValue(new File(playlistsJsonDeserializerFactory.getFileName()), playlists);
    }

    public void addPlaylist(Playlist playlist) throws IOException {
        List<Playlist> playlists = this.Deserialize();
        playlists.add( playlist );
        new ObjectMapper().writeValue(new File(playlistsJsonDeserializerFactory.getFileName()), playlists);
    }

    public void removePlaylist( int playlistId ) throws IOException {
        List<Playlist> playlists = this.Deserialize();
        playlists.removeIf(x -> Objects.equals(x.getId(), playlistId));
        new ObjectMapper().writeValue(new File(playlistsJsonDeserializerFactory.getFileName()), playlists);
    }

    public void addTrack( int playlistId, Track track ) throws IOException {
        List<Playlist> playlists = this.Deserialize();

        int playlistElementIndex = IntStream.range(0, playlists.size())
                .filter(i -> Objects.equals( playlists.get(i).getId(), playlistId ))
                .findFirst().orElse(-1);

        playlists.get( playlistElementIndex ).getTracks().add( track );
        new ObjectMapper().writeValue(new File(playlistsJsonDeserializerFactory.getFileName()), playlists);
    }

    public void removeTrack( int playlistId, int trackId ) throws IOException {

        List<Playlist> playlists = this.Deserialize();

        int playlistElementIndex = IntStream.range(0, playlists.size())
                .filter(i -> Objects.equals( playlists.get(i).getId(), playlistId ))
                .findFirst().orElse(-1);

        playlists.get( playlistElementIndex ).getTracks().removeIf( track -> Objects.equals(track.getId(), trackId) );
        new ObjectMapper().writeValue(new File(playlistsJsonDeserializerFactory.getFileName()), playlists);
    }

}
