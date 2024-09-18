package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.models.Playlist.Playlist;
import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.PlaylistViewModel;
import com.musicplayer.app.views.PlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.Node;

public class SelectedPlaylistCommand extends DelegateCommand {

    private static void selectedPlaylist(VmProvider vmProvider, Property<Playlist> selectedPlaylistProperty, Property<Node> selectedView) {

        PlaylistViewModel viewModelInstance = new PlaylistViewModel( vmProvider, selectedPlaylistProperty );

        var viewTuple = FluentViewLoader.fxmlView(PlaylistView.class).viewModel(viewModelInstance);

        selectedView.setValue(viewTuple.load().getView());
    }

    public SelectedPlaylistCommand(VmProvider vmProvider, Property<Playlist> selectedPlaylistProperty, Property<Node> selectedView) {
        super(() -> new Action() {
            @Override
            protected void action() {
                selectedPlaylist(vmProvider, selectedPlaylistProperty, selectedView);
            }
        });
    }

}
