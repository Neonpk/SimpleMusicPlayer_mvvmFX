package com.musicplayer.app.commands.playlist_commands;
import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import com.musicplayer.app.views.PlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.StringProperty;
import java.io.IOException;
import java.util.ArrayList;

public class SavePlaylistCommand extends DelegateCommand {

    private static void savePlaylist(StringProperty playlistName, PlaylistJsonProvider playlistJsonProvider, PlaylistsProvider playlistsProvider, NavigationService navService) throws IOException {

        int id = playlistsProvider.getPlaylists().size() + 1;
        Playlist playlist = new Playlist( id, playlistName.getValue(), new ArrayList<>( ));

        playlistJsonProvider.addPlaylist( playlist );
        playlistsProvider.getPlaylists().add( playlist );

        navService.navigate(FluentViewLoader.fxmlView(PlaylistView.class).load().getView());
    }

    public SavePlaylistCommand(StringProperty playlistName,
                               PlaylistJsonProvider playlistJsonProvider,
                               PlaylistsProvider playlistsProvider,
                               NavigationService navService) {
        super(() -> new Action() {
            @Override
            protected void action() throws IOException {
                savePlaylist(playlistName, playlistJsonProvider, playlistsProvider, navService);
            }
        });
    }

}
