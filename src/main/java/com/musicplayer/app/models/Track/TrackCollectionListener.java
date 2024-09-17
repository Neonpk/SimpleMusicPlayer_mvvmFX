package com.musicplayer.app.models.Track;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.PlaylistJsonProvider;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

@Getter
public class TrackCollectionListener {

    private final ListChangeListener<Track> tracksListener;

    public TrackCollectionListener(PlaylistJsonProvider playlistJsonProvider, Property<Playlist> selectedPlaylistProperty) {

        int playlistId = selectedPlaylistProperty.getValue().getId();

        tracksListener = ch -> {
            while (ch.next()) {

                ch.getRemoved().forEach(removedTrack -> {
                    try {
                        playlistJsonProvider.removeTrack(playlistId, removedTrack.getId());
                        selectedPlaylistProperty.getValue().getTracks().removeIf(track -> Objects.equals(track.getId(), removedTrack.getId()));
                    }catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }
                });

                ch.getAddedSubList().forEach(addedTrack -> {
                    try {
                        playlistJsonProvider.addTrack(playlistId, addedTrack);
                        selectedPlaylistProperty.getValue().getTracks().add(addedTrack);
                    }catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }
                });
            }
        };

    }

}
