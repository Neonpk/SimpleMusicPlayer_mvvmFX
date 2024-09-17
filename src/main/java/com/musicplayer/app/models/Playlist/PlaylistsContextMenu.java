package com.musicplayer.app.models.Playlist;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import lombok.Getter;

@Getter
public class PlaylistsContextMenu {

    private final ContextMenu contextMenu = new ContextMenu();

    private final MenuItem editPlaylistName = new MenuItem("Изменить название");

    private final MenuItem deletePlaylist = new MenuItem("Удалить плейлист");

    public PlaylistsContextMenu() {
        contextMenu.getItems().add(editPlaylistName);
        contextMenu.getItems().add(deletePlaylist);
    }

}
