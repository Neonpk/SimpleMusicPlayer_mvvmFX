package com.musicplayer.app.models.Track;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import lombok.Getter;

@Getter
public class TrackContextMenu {

    private final ContextMenu contextMenu = new ContextMenu();
    private final MenuItem deleteTrack = new MenuItem("Удалить трек");

    public TrackContextMenu() {
        contextMenu.getItems().add(deleteTrack);
    }

}
