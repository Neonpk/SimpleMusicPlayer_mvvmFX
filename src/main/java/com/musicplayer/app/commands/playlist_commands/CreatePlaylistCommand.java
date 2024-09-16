package com.musicplayer.app.commands.playlist_commands;

import com.musicplayer.app.views.CreateEditPlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.Node;

public class CreatePlaylistCommand extends DelegateCommand {

    private static void createPlaylist(Property<Node> selectedView) {
        var viewTuple = FluentViewLoader.fxmlView(CreateEditPlaylistView.class);
        selectedView.setValue( viewTuple.load().getView() );
    }

    public CreatePlaylistCommand(Property<Node> selectedView) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                createPlaylist(selectedView);
            }
        });
    }

}
