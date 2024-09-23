package com.musicplayer.app.viewmodels;

import com.musicplayer.app.commands.playlist_commands.SavePlaylistCommand;
import com.musicplayer.app.models.CommandParams.SavePlaylistCmdParam;
import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.PlaylistSelectionProvider;
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

        PlaylistSelectionProvider playlistSelectionProvider = vmProvider.getPlaylistSelectionProvider();

        Property<Playlist> selectedPlaylistProperty = playlistSelectionProvider.getSelectedPlaylistProperty();
        Property<Boolean> editModeValueProperty = playlistSelectionProvider.getPlaylistIsSelectedProperty();
        
        SavePlaylistCmdParam savePlaylistCmdParam = new SavePlaylistCmdParam(playlistNameProperty, statusTextProperty, selectedPlaylistProperty, editModeValueProperty);
        savePlaylistCommand = new SavePlaylistCommand(vmProvider, savePlaylistCmdParam);

        playlistNameProperty.bindBidirectional(vmProvider.getPlaylistSelectionProvider().getSelectedPlaylistNameProperty() );
    }

}
