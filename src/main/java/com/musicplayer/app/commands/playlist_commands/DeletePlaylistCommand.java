package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;

import java.io.IOException;
public class DeletePlaylistCommand extends DelegateCommand {

    private static void deletePlaylist(VmProvider vmProvider, Property<Playlist> selectedPlaylist) throws IOException {

        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();
        PlaylistsProvider playlistsProvider = vmProvider.getPlaylistsProvider();

        playlistJsonProvider.removePlaylist( selectedPlaylist.getValue().getId() );
        playlistsProvider.getPlaylists().remove( selectedPlaylist.getValue().getId() - 1 );
    }

    public DeletePlaylistCommand(VmProvider vmProvider, Property<Playlist> selectedPlaylist) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                deletePlaylist(vmProvider, selectedPlaylist);
            }
        });
    }

}
