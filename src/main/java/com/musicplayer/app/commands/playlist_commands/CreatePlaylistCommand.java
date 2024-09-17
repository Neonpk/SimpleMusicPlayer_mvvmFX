package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.services.VmProvider;
import com.musicplayer.app.viewmodels.CreateEditPlaylistViewModel;
import com.musicplayer.app.views.CreateEditPlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.Node;

public class CreatePlaylistCommand extends DelegateCommand {

    private static void createPlaylist(VmProvider vmProvider, Property<Node> selectedView) {
        CreateEditPlaylistViewModel viewModelInstance = new CreateEditPlaylistViewModel( vmProvider );
        var viewTuple = FluentViewLoader.fxmlView(CreateEditPlaylistView.class).viewModel(viewModelInstance);
        selectedView.setValue( viewTuple.load().getView() );
    }

    public CreatePlaylistCommand(VmProvider vmProvider, Property<Node> selectedView) {
        super(() -> new Action() {
            @Override
            protected void action() {
                createPlaylist(vmProvider, selectedView);
            }
        });
    }

}
