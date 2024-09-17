package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.PlaylistsProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;

public class DeletePlaylistCommand extends DelegateCommand {

    private static void deletePlaylist(VmProvider vmProvider, Property<Playlist> selectedPlaylist) {
        PlaylistsProvider playlistsProvider = vmProvider.getPlaylistsProvider();
        playlistsProvider.getPlaylists().remove( selectedPlaylist.getValue()  );
    }

    public DeletePlaylistCommand(VmProvider vmProvider, Property<Playlist> selectedPlaylist) {
        super(() -> new Action() {
            @Override
            protected void action() {
                deletePlaylist(vmProvider, selectedPlaylist);
            }
        });
    }

}
