package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.create_playlist_commands.SavePlaylistCommand;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class CreateEditPlaylistViewModel implements ViewModel {

    private final StringProperty playlistNameProperty = new SimpleStringProperty();

    private final Command savePlaylistCommand = new SavePlaylistCommand();

    public CreateEditPlaylistViewModel() {

    }

}
