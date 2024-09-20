package com.musicplayer.app.commands.playlist_commands;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.models.CommandParams.SavePlaylistCmdParam;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.MediaProvider;
import com.musicplayer.app.viewmodels.PlaylistViewModel;
import com.musicplayer.app.views.PlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SavePlaylistCommand extends DelegateCommand {

    private static void saveNewPlaylist(VmProvider vmProvider, SavePlaylistCmdParam savePlaylistCmdParam) {

        // Providers
        MediaProvider playlistsProvider = vmProvider.getMediaProvider();
        NavigationService navService = vmProvider.getNavigationService();

        // Params
        StringProperty playListName = savePlaylistCmdParam.getPlayListName();
        StringProperty statusMessage = savePlaylistCmdParam.getStatusMessage();

        if ( playListName.getValueSafe().trim().isEmpty() ) {
            statusMessage.setValue("Название не было указано.");
            return;
        }

        ObservableList<Playlist> playlists = playlistsProvider.getPlaylists();

        int id = !playlists.isEmpty() ? playlists.getLast().getId() + 1 : 1;
        Playlist playlist = new Playlist( id, playListName.getValue(), new ArrayList<>( ));

        playlists.add( playlist );

        var fxmlView = FluentViewLoader.fxmlView(PlaylistView.class);
        var viewTuple = fxmlView.viewModel( new PlaylistViewModel(vmProvider, new SimpleObjectProperty<>(playlist)) );

        navService.navigate(viewTuple.load().getView());
    }

    private static void saveSelectedPlaylist(VmProvider vmProvider, SavePlaylistCmdParam savePlaylistCmdParam) {

        // Providers
        NavigationService navService = vmProvider.getNavigationService();
        MediaProvider mediaProvider = vmProvider.getMediaProvider();

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

        mediaProvider.getPlaylists().set( id, playlist );

        var fxmlView = FluentViewLoader.fxmlView(PlaylistView.class);
        var viewTuple = fxmlView.viewModel( new PlaylistViewModel(vmProvider, selectedPlaylist) );

        navService.navigate(viewTuple.load().getView());
    }

    public SavePlaylistCommand(VmProvider vmProvider, SavePlaylistCmdParam savePlaylistCmdParam) {
        super(() -> new Action() {
            @Override
            protected void action() {
                if (savePlaylistCmdParam.isEditMode()) {
                    saveSelectedPlaylist(vmProvider, savePlaylistCmdParam);
                } else {
                    saveNewPlaylist(vmProvider, savePlaylistCmdParam);
                }
            }
        });
    }
}
