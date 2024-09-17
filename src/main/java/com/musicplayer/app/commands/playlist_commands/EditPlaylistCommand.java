package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import com.musicplayer.app.views.CreateEditPlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.Node;

public class EditPlaylistCommand extends DelegateCommand {

    private static void editPlaylist(VmProvider vmProvider, Property<Playlist> selectedPlaylist, Property<Node> selectedView) {
        CreateEditPlaylistViewModel viewModelInstance = new CreateEditPlaylistViewModel( vmProvider, selectedPlaylist );
        var viewTuple = FluentViewLoader.fxmlView(CreateEditPlaylistView.class).viewModel(viewModelInstance);
        selectedView.setValue( viewTuple.load().getView() );
    }

    public EditPlaylistCommand(VmProvider vmProvider, Property<Playlist> selectedPlaylist, Property<Node> selectedView) {
        super(() -> new Action() {
            @Override
            protected void action() {
                editPlaylist(vmProvider, selectedPlaylist, selectedView);
            }
        });
    }
}
