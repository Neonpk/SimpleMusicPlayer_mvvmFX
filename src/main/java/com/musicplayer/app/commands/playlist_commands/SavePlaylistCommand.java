package com.musicplayer.app.commands.playlist_commands;
import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.models.SavePlaylistCmdParam;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import com.musicplayer.app.views.PlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.ArrayList;

public class SavePlaylistCommand extends DelegateCommand {

    private static void saveNewPlaylist(VmProvider vmProvider, SavePlaylistCmdParam savePlaylistCmdParam) throws IOException {

        // Providers
        PlaylistsProvider playlistsProvider = vmProvider.getPlaylistsProvider();
        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();
        NavigationService navService = vmProvider.getNavigationService();

        // Params
        StringProperty playListName = savePlaylistCmdParam.getPlayListName();
        StringProperty statusMessage = savePlaylistCmdParam.getStatusMessage();

        if ( playListName.getValueSafe().trim().isEmpty() ) {
            statusMessage.setValue("Название не было указано.");
            return;
        }

        int id = playlistsProvider.getPlaylists().size() + 1;
        Playlist playlist = new Playlist( id, playListName.getValue(), new ArrayList<>( ));

        playlistJsonProvider.addPlaylist( playlist );
        playlistsProvider.getPlaylists().add( playlist );

        navService.navigate(FluentViewLoader.fxmlView(PlaylistView.class).load().getView());
    }

    private static void saveSelectedPlaylist(VmProvider vmProvider, SavePlaylistCmdParam savePlaylistCmdParam) throws IOException {

        // Providers
        PlaylistsProvider playlistsProvider = vmProvider.getPlaylistsProvider();
        PlaylistJsonProvider playlistJsonProvider = vmProvider.getPlaylistJsonProvider();
        NavigationService navService = vmProvider.getNavigationService();

        // Params
        StringProperty playListName = savePlaylistCmdParam.getPlayListName();
        StringProperty statusMessage = savePlaylistCmdParam.getStatusMessage();
        Property<Playlist> selectedPlaylist = savePlaylistCmdParam.getSelectedPlaylist();

        if ( playListName.getValueSafe().trim().isEmpty() ) {
            statusMessage.setValue("Название не было указано.");
            return;
        }

        int id = selectedPlaylist.getValue().getId() - 1;
        Playlist playlist = selectedPlaylist.getValue();
        playlist.setName(playListName.getValue());

        playlistJsonProvider.replacePlaylist( id, playlist );
        playlistsProvider.getPlaylists().set( id, playlist );

        navService.navigate(FluentViewLoader.fxmlView(PlaylistView.class).load().getView());
    }

    public SavePlaylistCommand(VmProvider vmProvider, SavePlaylistCmdParam savePlaylistCmdParam) {
        super(() -> new Action() {
            @Override
            protected void action() throws IOException {
                if (savePlaylistCmdParam.isEditMode()) {
                    saveSelectedPlaylist(vmProvider, savePlaylistCmdParam);
                }else {
                    saveNewPlaylist(vmProvider, savePlaylistCmdParam);
                }
            }
        });
    }
}
