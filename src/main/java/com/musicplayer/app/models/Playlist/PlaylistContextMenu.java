package com.musicplayer.app.models.Playlist;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import lombok.Getter;

@Getter
public class PlaylistContextMenu {

    private final ContextMenu contextMenu = new ContextMenu();

    private final MenuItem editPlaylistName = new MenuItem("Изменить название");

    private final MenuItem deletePlaylist = new MenuItem("Удалить плейлист");

    public PlaylistContextMenu() {
        contextMenu.getItems().add(editPlaylistName);
        contextMenu.getItems().add(deletePlaylist);
    }

}
