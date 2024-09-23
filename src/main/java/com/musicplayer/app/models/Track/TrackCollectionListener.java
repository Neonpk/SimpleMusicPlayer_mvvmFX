package com.musicplayer.app.models.Track;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.PlaylistJsonProvider;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import lombok.Getter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Getter
public class TrackCollectionListener {

    private final ListChangeListener<Track> trackCollectionListener;

    public TrackCollectionListener(PlaylistJsonProvider playlistJsonProvider, Property<Playlist> selectedPlaylistProperty, List<Track> trackListQueue) {

        trackCollectionListener = ch -> {

            int playlistId = selectedPlaylistProperty.getValue().getId();

            while (ch.next()) {

                ch.getRemoved().forEach(removedTrack -> {
                    try {
                        playlistJsonProvider.removeTrack(playlistId, removedTrack.getId());
                        trackListQueue.removeIf(track -> Objects.equals(track.getId(), removedTrack.getId()));
                        selectedPlaylistProperty.getValue().getTracks().removeIf(track -> Objects.equals(track.getId(), removedTrack.getId()));
                    }catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }
                });

                ch.getAddedSubList().forEach(addedTrack -> {
                    try {
                        playlistJsonProvider.addTrack(playlistId, addedTrack);
                        trackListQueue.add(addedTrack);
                        selectedPlaylistProperty.getValue().getTracks().add(addedTrack);
                    }catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }
                });
            }
        };

    }

}
