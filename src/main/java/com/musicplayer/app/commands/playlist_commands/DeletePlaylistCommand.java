package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.MediaProvider;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;

public class DeletePlaylistCommand extends DelegateCommand {

    private static void deletePlaylist(VmProvider vmProvider, Property<Playlist> selectedPlaylist) {
        MediaProvider mediaProvider = vmProvider.getMediaProvider();
        mediaProvider.getPlaylists().remove( selectedPlaylist.getValue()  );
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
