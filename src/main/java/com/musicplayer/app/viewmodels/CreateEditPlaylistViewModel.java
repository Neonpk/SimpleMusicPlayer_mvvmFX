package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.playlist_commands.SavePlaylistCommand;
import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistJsonProvider;
import com.musicplayer.app.services.PlaylistsProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class CreateEditPlaylistViewModel implements ViewModel {

    private final StringProperty playlistNameProperty = new SimpleStringProperty();

    private final Command savePlaylistCommand;

    public CreateEditPlaylistViewModel(PlaylistJsonProvider jsonProvider,
                                       PlaylistsProvider playlistsProvider,
                                       NavigationService navService) {
        savePlaylistCommand = new SavePlaylistCommand(playlistNameProperty, jsonProvider, playlistsProvider, navService);
    }

}
