package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

import java.io.IOException;
public class DeletePlaylistCommand extends DelegateCommand {

    private static void deletePlaylist(PlaylistJsonProvider jsonProvider,
                                       PlaylistsProvider playlistsProvider,
                                       Property<Playlist> selectedPlaylist) throws IOException {

        jsonProvider.removePlaylist( selectedPlaylist.getValue().getId() );
        playlistsProvider.getPlaylists().remove( selectedPlaylist.getValue().getId() - 1 );
    }

    public DeletePlaylistCommand(PlaylistJsonProvider jsonProvider,
                                 PlaylistsProvider playlistsProvider,
                                 Property<Playlist> selectedPlaylist) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                deletePlaylist(jsonProvider, playlistsProvider, selectedPlaylist);
            }
        });
    }

}
