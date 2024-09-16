package com.musicplayer.app.models;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class SavePlaylistCmdParam {
    private final StringProperty playListName;
    private final StringProperty statusMessage;
    private final Property<Playlist> selectedPlaylist;

    public boolean isEditMode() {
        return !Objects.equals(selectedPlaylist, null);
    }
}
