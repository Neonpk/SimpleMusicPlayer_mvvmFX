package com.musicplayer.app.models.Playlist;

import com.musicplayer.app.services.PlaylistJsonProvider;
import javafx.collections.ListChangeListener;
import lombok.Getter;

import java.io.IOException;

@Getter
public class PlaylistCollectionListener {

    private final ListChangeListener<Playlist> playlistListener;

    public PlaylistCollectionListener(PlaylistJsonProvider playlistJsonProvider) {
        playlistListener = ch -> {

            while(ch.next()) {

                if(ch.wasReplaced()) {
                    try {
                        Playlist replacedPlaylist = ch.getAddedSubList().getFirst();
                        playlistJsonProvider.replacePlaylist(replacedPlaylist.getId(), replacedPlaylist);
                        break;
                    }catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                }

                ch.getRemoved().forEach(playlist -> {
                    try {
                      playlistJsonProvider.removePlaylist(playlist.getId());
                    }catch (IOException exception) {
                      System.out.println(exception.getMessage());
                    }
                });

                ch.getAddedSubList().forEach(playlist -> {
                    try {
                      playlistJsonProvider.addPlaylist(playlist);
                    }catch (IOException exception) {
                      System.out.println(exception.getMessage());
                    }
                });

            }
        };
    }

}
