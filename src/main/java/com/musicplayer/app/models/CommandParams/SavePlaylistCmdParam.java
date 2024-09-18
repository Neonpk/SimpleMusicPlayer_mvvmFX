package com.musicplayer.app.models.CommandParams;

import com.musicplayer.app.models.Playlist.Playlist;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@SuppressWarnings("all")
public class SavePlaylistCmdParam {
    private final StringProperty playListName;
    private final StringProperty statusMessage;
    private final Property<Playlist> selectedPlaylist;

    public boolean isEditMode() {
        return !Objects.equals(selectedPlaylist, null);
    }
}