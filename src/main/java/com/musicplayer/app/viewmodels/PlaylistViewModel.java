package com.musicplayer.app.viewmodels;

import com.musicplayer.app.models.Playlist;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

public class PlaylistViewModel implements ViewModel {

    @Getter
    private final Property<Playlist> selectedPlaylist = new SimpleObjectProperty<>();

    public PlaylistViewModel() {

    }

}
