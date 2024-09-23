package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.services.NavigationService;
import com.musicplayer.app.services.PlaylistSelectionProvider;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;

public class EditPlaylistCommand extends DelegateCommand {

    private static void editPlaylist(VmProvider vmProvider) {

        // Providers

        NavigationService navigationService = vmProvider.getNavigationService();
        PlaylistSelectionProvider playlistSelectionProvider = vmProvider.getPlaylistSelectionProvider();

        // Operations

        Property<Boolean> editMode = playlistSelectionProvider.getPlaylistIsSelectedProperty();
        editMode.setValue(true);

        // Navigation

        navigationService.navigate(CreateEditPlaylistViewModel.class);
    }

    public EditPlaylistCommand(VmProvider vmProvider) {
        super(() -> new Action() {
            @Override
            protected void action() {
                editPlaylist(vmProvider);
            }
        });
    }
}
