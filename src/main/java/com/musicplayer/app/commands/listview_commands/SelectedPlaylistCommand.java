package com.musicplayer.app.commands.listview_commands;

import com.musicplayer.app.models.Playlist;
import com.musicplayer.app.views.CreateEditPlaylistView;
import com.musicplayer.app.views.PlaylistView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.Property;
import javafx.scene.Node;

public class SelectedPlaylistCommand extends DelegateCommand {

    private static void selectedPlaylist(Property<Playlist> selectedPlaylistProperty, Property<Node> selectedView) {
        selectedView.setValue(FluentViewLoader.fxmlView(PlaylistView.class).load().getView());
    }

    public SelectedPlaylistCommand(Property<Playlist> selectedPlaylistProperty, Property<Node> selectedView) {
        super(() -> new Action() {
            @Override
            protected void action() throws Exception {
                selectedPlaylist(selectedPlaylistProperty, selectedView);
            }
        });
    }

}
