package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.playlist_commands.SavePlaylistCommand;
import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.models.SavePlaylistCmdParam;
import com.musicplayer.app.services.VmProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class CreateEditPlaylistViewModel implements ViewModel {

    private final StringProperty playlistNameProperty = new SimpleStringProperty();
    private final StringProperty statusTextProperty = new SimpleStringProperty();

    private final Command savePlaylistCommand;

    public CreateEditPlaylistViewModel(VmProvider vmProvider) {

        SavePlaylistCmdParam savePlaylistCmdParam = new SavePlaylistCmdParam(playlistNameProperty, statusTextProperty, null);

        savePlaylistCommand = new SavePlaylistCommand(vmProvider, savePlaylistCmdParam);
    }

    public CreateEditPlaylistViewModel(VmProvider vmProvider, Property<Playlist> selectedPlaylist) {

        SavePlaylistCmdParam savePlaylistCmdParam = new SavePlaylistCmdParam(playlistNameProperty, statusTextProperty, selectedPlaylist);

        savePlaylistCommand = new SavePlaylistCommand(vmProvider, savePlaylistCmdParam);
    }

}
