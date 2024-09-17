package com.musicplayer.app.models.Playlist;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import lombok.Getter;

@Getter
public class PlaylistContextMenu {

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem deleteTrack = new MenuItem("Удалить трек");

    public PlaylistContextMenu() {
        contextMenu.getItems().add(deleteTrack);
    }

}
