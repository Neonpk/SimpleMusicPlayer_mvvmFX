package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistSelectionProvider;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

public class CreatePlaylistCommand extends DelegateCommand {

    private static void createPlaylist(VmProvider vmProvider) {

        // Providers

        NavigationService navigationService = vmProvider.getNavigationService();
        PlaylistSelectionProvider playlistSelectionProvider = vmProvider.getPlaylistSelectionProvider();

        // Operations

        Property<Boolean> editMode = playlistSelectionProvider.getPlaylistIsSelectedProperty();
        StringProperty selectedPlaylistNameProperty = playlistSelectionProvider.getSelectedPlaylistNameProperty();

        selectedPlaylistNameProperty.setValue("");
        editMode.setValue(false);

        // Navigation

        navigationService.navigate(CreateEditPlaylistViewModel.class);
    }

    public CreatePlaylistCommand(VmProvider vmProvider) {
        super(() -> new Action() {
            @Override
            protected void action() {
                createPlaylist(vmProvider);
            }
        });
    }

}
